import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.present.PresentPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.present {
    exports com.github.moaxcp.x11.protocol.present;

    requires static lombok;
    requires transitive com.github.moaxcp.x11.protocol.core;
    requires com.github.moaxcp.x11.protocol.xproto;
    provides XProtocolPlugin with PresentPlugin;
}