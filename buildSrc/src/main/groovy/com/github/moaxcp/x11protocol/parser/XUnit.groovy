package com.github.moaxcp.x11protocol.parser
/**
 * A single unit within the x protocol. XUnit is converted to a JavaUnit which defines how to read/write in java and
 * add the unit to a class in the case of JavaPropertyUnit.
 */
interface XUnit {
    JavaUnit getJavaUnit()
}