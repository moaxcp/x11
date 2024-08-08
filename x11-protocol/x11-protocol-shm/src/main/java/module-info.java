import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.shm.ShmPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.shm {
    exports com.github.moaxcp.x11.protocol.shm;

    requires static lombok;
    requires transitive com.github.moaxcp.x11.protocol.core;
    provides XProtocolPlugin with ShmPlugin;
}