import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.sync.SyncPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.sync {
    exports com.github.moaxcp.x11.protocol.sync;

    requires static lombok;
    requires com.github.moaxcp.x11.protocol.core;
    provides XProtocolPlugin with SyncPlugin;
}