import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.xproto.XprotoPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.xproto {
    exports com.github.moaxcp.x11.protocol.xproto;

    requires com.github.moaxcp.x11.protocol.core;
    requires static lombok;
    provides XProtocolPlugin with XprotoPlugin;
}