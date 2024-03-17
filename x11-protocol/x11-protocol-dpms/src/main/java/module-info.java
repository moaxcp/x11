import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.dpms.DpmsPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.dpms {
    exports com.github.moaxcp.x11.protocol.dpms;

    requires static lombok;
    requires com.github.moaxcp.x11.protocol.core;
    provides XProtocolPlugin with DpmsPlugin;
}