import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.render.RenderPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.render {
    exports com.github.moaxcp.x11.protocol.render;

    requires static lombok;
    requires transitive com.github.moaxcp.x11.protocol.core;
    requires com.github.moaxcp.x11.protocol.xproto;
    provides XProtocolPlugin with RenderPlugin;
}