import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.xevie.XeviePlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.xeive {
    exports com.github.moaxcp.x11.protocol.xevie;

    requires static lombok;
    requires com.github.moaxcp.x11.protocol.core;
    provides XProtocolPlugin with XeviePlugin;
}