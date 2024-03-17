@SuppressWarnings("module")
module com.github.moaxcp.x11.examples {
    exports com.github.moaxcp.x11.examples;

    requires transitive com.github.moaxcp.x11.client;
    requires transitive com.github.moaxcp.x11.xephyr;
    requires transitive com.github.moaxcp.x11.toolkit;
    requires transitive com.github.moaxcp.x11.keysym;
    requires transitive java.logging;

    requires static lombok;
}