import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.shape.ShapePlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.shape {
    exports com.github.moaxcp.x11.protocol.shape;

    requires static lombok;
    requires transitive com.github.moaxcp.x11.protocol.core;
    requires com.github.moaxcp.x11.protocol.xproto;
    provides XProtocolPlugin with ShapePlugin;
}