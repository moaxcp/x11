import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.randr.RandrPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.randr {
    exports com.github.moaxcp.x11.protocol.randr;

    requires static lombok;
    requires transitive com.github.moaxcp.x11.protocol.core;
    requires com.github.moaxcp.x11.protocol.xproto;
    requires com.github.moaxcp.x11.protocol.render;
    provides XProtocolPlugin with RandrPlugin;
}