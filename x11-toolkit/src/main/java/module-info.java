@SuppressWarnings("module")
module com.github.moaxcp.x11.toolkit {
    exports com.github.moaxcp.x11.toolkit;

    requires transitive com.github.moaxcp.x11.client;
    requires static lombok;
}