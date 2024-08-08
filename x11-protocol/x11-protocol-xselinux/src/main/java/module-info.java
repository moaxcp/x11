import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.xselinux.XselinuxPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.xselinux {
    exports com.github.moaxcp.x11.protocol.xselinux;

    requires transitive com.github.moaxcp.x11.protocol.core;
    requires static lombok;
    provides XProtocolPlugin with XselinuxPlugin;
}