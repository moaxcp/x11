import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.glx.GlxPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.glx {
    exports com.github.moaxcp.x11.protocol.glx;

    requires static lombok;
    requires transitive com.github.moaxcp.x11.protocol.core;
    provides XProtocolPlugin with GlxPlugin;
}