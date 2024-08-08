import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.res.ResPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.res {
    exports com.github.moaxcp.x11.protocol.res;

    requires static lombok;
    requires transitive com.github.moaxcp.x11.protocol.core;
    provides XProtocolPlugin with ResPlugin;
}