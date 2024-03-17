import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.composite.CompositePlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.composite {
    exports com.github.moaxcp.x11.protocol.composite;

    requires static lombok;
    requires com.github.moaxcp.x11.protocol.core;
    provides XProtocolPlugin with CompositePlugin;
}