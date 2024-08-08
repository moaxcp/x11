import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.xinerama.XineramaPlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.xinerama {
    exports com.github.moaxcp.x11.protocol.xinerama;

    requires static lombok;
    requires transitive com.github.moaxcp.x11.protocol.core;
    provides XProtocolPlugin with XineramaPlugin;
}