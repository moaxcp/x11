import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.dri2.Dri2Plugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.dri2 {
    exports com.github.moaxcp.x11.protocol.dri2;

    requires static lombok;
    requires com.github.moaxcp.x11.protocol.core;
    provides XProtocolPlugin with Dri2Plugin;
}