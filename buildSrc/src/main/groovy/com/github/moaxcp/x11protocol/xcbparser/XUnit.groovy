package com.github.moaxcp.x11protocol.xcbparser
/**
 * A single unit within the x protocol. XUnit is converted to a JavaUnit which defines how to read/write in java.
 */
interface XUnit {
    XCaseInfo getCaseInfo()
    void setCaseInfo(XCaseInfo caseInfo)
    XBitcaseInfo getBitcaseInfo()
    void setBitcaseInfo(XBitcaseInfo bitcaseInfo)
    List<JavaUnit> getJavaUnit(JavaType javaType)
}