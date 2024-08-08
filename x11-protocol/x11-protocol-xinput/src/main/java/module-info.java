import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.xinput.XinputPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.xinput {
    exports com.github.moaxcp.x11.protocol.xinput;

    requires static lombok;
    requires transitive com.github.moaxcp.x11.protocol.core;
    requires com.github.moaxcp.x11.protocol.xproto;
    provides XProtocolPlugin with XinputPlugin;
}