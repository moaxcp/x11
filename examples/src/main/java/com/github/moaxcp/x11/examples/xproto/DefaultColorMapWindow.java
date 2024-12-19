package com.github.moaxcp.x11.examples.xproto;

public class DefaultColorMapWindow {
    public static void main(String... args) {
        ExposeWindow main = (client, wid, lineGc, fillGc) -> client.imageText8(wid, lineGc, (short) 0, (short) 10, String.valueOf(client.getSetup().getRoots().get(0).getDefaultColormap()));
        main.start();
    }
}
