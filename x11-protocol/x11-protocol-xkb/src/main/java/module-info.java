import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.xkb.XkbPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.xkb {
    exports com.github.moaxcp.x11.protocol.xkb;

    requires static lombok;
    requires com.github.moaxcp.x11.protocol.core;
    requires com.github.moaxcp.x11.protocol.xproto;
    provides XProtocolPlugin with XkbPlugin;
}