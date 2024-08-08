import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.xproto.XprotoPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.xproto {
    exports com.github.moaxcp.x11.protocol.xproto;

    requires transitive com.github.moaxcp.x11.protocol.core;
    requires static lombok;
    requires org.eclipse.collections.api;
    provides XProtocolPlugin with XprotoPlugin;
}