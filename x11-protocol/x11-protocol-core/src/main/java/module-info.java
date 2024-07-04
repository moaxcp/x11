@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.core {
    exports com.github.moaxcp.x11.protocol;

    uses com.github.moaxcp.x11.protocol.XProtocolPlugin;

    requires static lombok;
}