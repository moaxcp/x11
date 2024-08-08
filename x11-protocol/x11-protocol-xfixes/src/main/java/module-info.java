import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.xfixes.XfixesPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.xfixes {
    exports com.github.moaxcp.x11.protocol.xfixes;

    requires static lombok;
    requires transitive com.github.moaxcp.x11.protocol.core;
    requires com.github.moaxcp.x11.protocol.xproto;
    requires com.github.moaxcp.x11.protocol.shape;
    provides XProtocolPlugin with XfixesPlugin;
}