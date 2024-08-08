import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.xv.XvPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.xv {
    exports com.github.moaxcp.x11.protocol.xv;

    requires transitive com.github.moaxcp.x11.protocol.core;
    requires com.github.moaxcp.x11.protocol.xproto;
    requires static lombok;
    provides XProtocolPlugin with XvPlugin;
}