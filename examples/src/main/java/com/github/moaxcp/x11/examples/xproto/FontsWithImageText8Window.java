package com.github.moaxcp.x11.examples.xproto;

import com.github.moaxcp.x11.protocol.xproto.ChangeGC;

public class FontsWithImageText8Window {
    public static void main(String... args) {
        ExposeWindow main = (client, wid, lineGc, fillGc) -> {
            var fid = client.openFont("9x15bold");
            var gc = client.createGC(0, wid);
            client.send(ChangeGC.builder()
                .gc(gc)
                .font(fid)
                .build());
            client.imageText8(wid, gc, (short) 0, (short) 10, "Hello World!");
            client.closeFont(fid);
            client.freeGC(gc);
        };
        main.start();
    }
}
