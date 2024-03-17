import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.ge.GePlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.ge {
    exports com.github.moaxcp.x11.protocol.ge;

    requires static lombok;
    requires com.github.moaxcp.x11.protocol.core;
    provides XProtocolPlugin with GePlugin;
}