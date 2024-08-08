import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.screensaver.ScreensaverPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.screensaver {
    exports com.github.moaxcp.x11.protocol.screensaver;

    requires static lombok;
    requires transitive com.github.moaxcp.x11.protocol.core;
    requires com.github.moaxcp.x11.protocol.xproto;
    provides XProtocolPlugin with ScreensaverPlugin;
}