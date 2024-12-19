package com.github.moaxcp.x11.examples.xproto;

import com.github.moaxcp.x11.protocol.xproto.Arc;

import java.util.ArrayList;
import java.util.Random;

import static com.github.moaxcp.x11.examples.xproto.ExposeWindow.HEIGHT;
import static com.github.moaxcp.x11.examples.xproto.ExposeWindow.WIDTH;

public class PolyArcWindow {
    public static void main(String... args) {
        var arcs = new ArrayList<Arc>();
        var random = new Random();
        for (int i = 0; i < 100; i++) {
            var x = (short) random.nextInt(10, WIDTH - 10);
            var y = (short) random.nextInt(10, HEIGHT - 10);
            arcs.add(Arc.builder()
              .x(x)
              .y(y)
              .width((short) random.nextInt(0, WIDTH - 10 - x))
              .height((short) random.nextInt(0, HEIGHT - 10 - y))
              .angle1((short) (0 * 64))
              .angle2((short) (190 * 64))
              .build());
        }
        ExposeWindow main = (client, wid, lineGc, fillGc) -> client.polyArc(wid, lineGc, arcs);
        main.start();
    }
}
