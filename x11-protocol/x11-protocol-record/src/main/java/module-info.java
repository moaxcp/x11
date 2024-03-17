import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.record.RecordPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.record {
    exports com.github.moaxcp.x11.protocol.record;

    requires static lombok;
    requires com.github.moaxcp.x11.protocol.core;
    provides XProtocolPlugin with RecordPlugin;
}