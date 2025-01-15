@SuppressWarnings("module")
module com.github.moaxcp.x11.client {
    exports com.github.moaxcp.x11.x11client;
    exports com.github.moaxcp.x11.x11client.api.record;
    exports com.github.moaxcp.x11.x11client.api.image;

    requires transitive com.github.moaxcp.x11.keysym;
    requires transitive com.github.moaxcp.x11.protocol.core;
    requires transitive com.github.moaxcp.x11.protocol.bigreq;
    requires transitive com.github.moaxcp.x11.protocol.record;
    requires transitive com.github.moaxcp.x11.protocol.xproto;
    requires transitive com.github.moaxcp.x11.protocol.xcmisc;
    requires transitive org.newsclub.net.unix;
    requires transitive com.kohlschutter.junixsocket.nativecommon;
}