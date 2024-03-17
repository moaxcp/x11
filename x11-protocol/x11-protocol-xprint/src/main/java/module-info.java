import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.xprint.XprintPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.xprint {
    exports com.github.moaxcp.x11.protocol.xprint;

    requires static lombok;
    requires com.github.moaxcp.x11.protocol.core;
    provides XProtocolPlugin with XprintPlugin;
}