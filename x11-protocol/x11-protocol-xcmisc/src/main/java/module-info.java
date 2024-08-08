import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.xc_misc.XcMiscplugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.xcmisc {
    exports com.github.moaxcp.x11.protocol.xc_misc;

    requires static lombok;
    requires transitive com.github.moaxcp.x11.protocol.core;
    provides XProtocolPlugin with XcMiscplugin;
}