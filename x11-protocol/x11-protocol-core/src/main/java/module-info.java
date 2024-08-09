@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.core {
    exports com.github.moaxcp.x11.protocol;

    uses com.github.moaxcp.x11.protocol.XProtocolPlugin;

    requires org.eclipse.collections.api;
    requires org.eclipse.collections.impl;
    requires static lombok;
}