package com.github.moaxcp.x11.examples.xproto;

public class ImageText8Window {
    public static void main(String... args) {
        ExposeWindow main = (client, wid, lineGc, fillGc) -> client.imageText8(wid, lineGc, (short) 0, (short) 10, "Hello World!");
        main.start();
    }
}
