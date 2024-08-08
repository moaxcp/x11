import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.xf86vidmode.Xf86vidmodePlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.xf86vidmode {
    exports com.github.moaxcp.x11.protocol.xf86vidmode;

    requires static lombok;
    requires transitive com.github.moaxcp.x11.protocol.core;
    provides XProtocolPlugin with Xf86vidmodePlugin;
}