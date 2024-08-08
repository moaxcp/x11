@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.core {
    exports com.github.moaxcp.x11.protocol;

    uses com.github.moaxcp.x11.protocol.XProtocolPlugin;

    requires transitive org.eclipse.collections.api;
    requires transitive org.eclipse.collections.impl;
    requires static lombok;
}