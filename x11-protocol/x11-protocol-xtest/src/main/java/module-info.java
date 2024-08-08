import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.xtest.XtestPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.xtest {
    exports com.github.moaxcp.x11.protocol.xtest;

    requires transitive com.github.moaxcp.x11.protocol.core;
    requires static lombok;
    provides XProtocolPlugin with XtestPlugin;
}