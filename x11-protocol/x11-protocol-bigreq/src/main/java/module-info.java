import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.bigreq.BigreqPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.bigreq {
    exports com.github.moaxcp.x11.protocol.bigreq;

    requires static lombok;
    requires com.github.moaxcp.x11.protocol.core;
    provides XProtocolPlugin with BigreqPlugin;
}