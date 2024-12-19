@SuppressWarnings("module")
module com.github.moaxcp.x11.examples {
    exports com.github.moaxcp.x11.examples;
  exports com.github.moaxcp.x11.examples.record;

  requires transitive com.github.moaxcp.x11.client;
    requires transitive com.github.moaxcp.x11.xephyr;
    requires transitive com.github.moaxcp.x11.toolkit;
    requires transitive com.github.moaxcp.x11.keysym;
    requires transitive java.logging;
  requires com.github.moaxcp.x11.protocol.xfixes;
  requires java.desktop;
  requires com.github.moaxcp.x11.protocol.present;
}