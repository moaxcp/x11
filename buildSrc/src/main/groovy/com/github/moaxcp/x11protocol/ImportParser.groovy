package com.github.moaxcp.x11protocol

import groovy.util.slurpersupport.GPathResult

/**
 * Parses all imports recursively merging all results in one ImportResult
 */
class ImportParser {
    File file
    String basePackage
    ImportResult importResult = new ImportResult()
    private boolean parsed

    ImportResult parse() {
        if(parsed) {
            throw new IllegalStateException("already parsed")
        }
        parsed = true

        GPathResult xml = new XmlSlurper().parse(file)
        List<File> files = xml.import.collect {
            new File(file.parent + File.separator + (String) it.text() + '.xml')
        }

        List<File> validFiles = files
            .findAll {!importResult.filesImported.contains(it)}

        importResult.filesImported += files

        validFiles.each {
            GPathResult importXml = new XmlSlurper().parse(it)
            Map<String, String> types = ProtocolParser.parseTypeDefs(importXml)
            importResult.definedTypes.putAll(types)

            String packageName = basePackage + '.' + (String) importXml.@header

            Map<String, String> structs = importXml.struct*.@name.flatten().collectEntries {
                [((String) it): Conventions.getX11ToJavaType((String) it)]
            }

            importResult.x11ToJavaTypes.put(packageName, structs)

            ImportResult newResult = new ImportParser(basePackage: basePackage, file:it, importResult: importResult).parse()
            importResult.addResult(newResult)
        }

        return importResult
    }
}
