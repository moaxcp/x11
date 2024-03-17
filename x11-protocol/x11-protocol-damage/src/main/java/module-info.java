import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.damage.DamagePlugin;

@SuppressWarnings("module")
module com.github.moaxcp.x11.protocol.damage {
    exports com.github.moaxcp.x11.protocol.damage;

    requires static lombok;
    requires transitive com.github.moaxcp.x11.protocol.core;
    requires transitive com.github.moaxcp.x11.protocol.xproto;
    provides XProtocolPlugin with DamagePlugin;
}