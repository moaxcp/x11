import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.xf86dri.Xf86driPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.xf86dri {
    exports com.github.moaxcp.x11.protocol.xf86dri;

    requires static lombok;
    requires transitive com.github.moaxcp.x11.protocol.core;
    provides XProtocolPlugin with Xf86driPlugin;
}