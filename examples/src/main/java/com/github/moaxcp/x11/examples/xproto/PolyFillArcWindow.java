package com.github.moaxcp.x11.examples.xproto;

import com.github.moaxcp.x11.protocol.xproto.Arc;
import org.eclipse.collections.api.factory.Lists;

import java.util.Random;

import static com.github.moaxcp.x11.examples.xproto.ExposeWindow.HEIGHT;
import static com.github.moaxcp.x11.examples.xproto.ExposeWindow.WIDTH;

public class PolyFillArcWindow {
    public static void main(String... args) {
        var arcs = Lists.mutable.<Arc>empty();
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
        ExposeWindow main = (client, wid, lineGc, fillGc) -> {
            client.polyFillArc(wid, fillGc, arcs.toImmutable());
            client.polyArc(wid, lineGc, arcs.toImmutable());
        };
        main.start();
    }
}
