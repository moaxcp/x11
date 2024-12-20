package com.github.moaxcp.x11.examples.xproto;

import com.github.moaxcp.x11.protocol.xproto.CoordMode;
import com.github.moaxcp.x11.protocol.xproto.Point;
import com.github.moaxcp.x11.protocol.xproto.PolyShape;

import java.util.ArrayList;
import java.util.Random;

import static com.github.moaxcp.x11.examples.xproto.ExposeWindow.HEIGHT;
import static com.github.moaxcp.x11.examples.xproto.ExposeWindow.WIDTH;

public class FillPolyWindow {
    public static void main(String... args) {
        var points = new ArrayList<Point>();
        var random = new Random();
        points.add(Point.builder().x((short) random.nextInt(10, WIDTH / 2)).y((short) random.nextInt(10, HEIGHT / 2)).build());
        points.add(Point.builder().x((short) random.nextInt(WIDTH / 2,  WIDTH - 10)).y((short) random.nextInt(10, HEIGHT / 2)).build());
        points.add(Point.builder().x((short) random.nextInt(WIDTH / 2, WIDTH - 10)).y((short) random.nextInt(HEIGHT / 2, HEIGHT -10)).build());
        points.add(Point.builder().x((short) random.nextInt(10, WIDTH / 2)).y((short) random.nextInt(HEIGHT / 2, HEIGHT - 10)).build());
        points.add(points.get(0));
        ExposeWindow main = (client, wid, lineGc, fillGc) -> {
            client.fillPoly(wid, fillGc, PolyShape.CONVEX, CoordMode.ORIGIN, points);
            client.polyLine(wid, lineGc, CoordMode.ORIGIN, points);
        };
        main.start();
    }
}
