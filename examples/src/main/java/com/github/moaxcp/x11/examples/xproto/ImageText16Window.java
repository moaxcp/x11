package com.github.moaxcp.x11.examples.xproto;

public class ImageText16Window {
    public static void main(String... args) {
        ExposeWindow main = (client, wid, lineGc, fillGc) -> client.imageText16(wid, lineGc, (short) 0, (short) 10, "Hello World!");
        main.start();
    }
}
