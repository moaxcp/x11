package com.github.moaxcp.x11protocol.xcbparser

interface XTypeUnit {
    JavaType getJavaType()
    boolean hasSubTypes()
    /**
     * Returns the subtype for this unit. The subType is the case name in the switch.
     * @param subType
     * @return
     */
    JavaType getSubType(String subType)
    List<JavaType> getSubTypes()
    List<String> getSubTypeNames()
    JavaProperty getJavaProperty(JavaType javaType, XUnitField field)
    JavaListProperty getJavaProperty(JavaType javaType, XUnitListField field)
}