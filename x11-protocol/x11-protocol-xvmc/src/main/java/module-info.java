import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.xvmc.XvmcPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.xvmc {
    exports com.github.moaxcp.x11.protocol.xvmc;

    requires com.github.moaxcp.x11.protocol.core;
    requires com.github.moaxcp.x11.protocol.xv;
    requires static lombok;
    provides XProtocolPlugin with XvmcPlugin;
}