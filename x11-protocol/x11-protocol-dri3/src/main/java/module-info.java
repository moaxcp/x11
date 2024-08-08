import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.dri3.Dri3Plugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.dri3 {
    exports com.github.moaxcp.x11.protocol.dri3;

    requires static lombok;
    requires transitive com.github.moaxcp.x11.protocol.core;
    provides XProtocolPlugin with Dri3Plugin;
}