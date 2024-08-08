package com.github.moaxcp.x11.examples.xproto;

import com.github.moaxcp.x11.protocol.xproto.CoordMode;
import com.github.moaxcp.x11.protocol.xproto.Point;
import org.eclipse.collections.api.factory.Lists;

import java.util.Random;

import static com.github.moaxcp.x11.examples.xproto.ExposeWindow.HEIGHT;
import static com.github.moaxcp.x11.examples.xproto.ExposeWindow.WIDTH;

public class PolyLineWindow {
    public static void main(String... args) {
        var points = Lists.mutable.<Point>empty();
        var random = new Random();
        for (int i = 0; i < 100; i++) {
            points.add(Point.builder().x((short) random.nextInt(10, WIDTH - 10)).y((short) random.nextInt(10, HEIGHT - 10)).build());
        }
        ExposeWindow main = (client, wid, lineGc, fillGc) -> client.polyLine(wid, lineGc, CoordMode.ORIGIN, points.toImmutable());
        main.start();
    }
}
