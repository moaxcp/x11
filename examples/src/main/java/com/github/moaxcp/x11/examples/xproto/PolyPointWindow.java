package com.github.moaxcp.x11.examples.xproto;

import com.github.moaxcp.x11.protocol.xproto.CoordMode;
import com.github.moaxcp.x11.protocol.xproto.Point;

import java.util.ArrayList;
import java.util.Random;

public class PolyPointWindow {
    public static void main(String... args) {
        var points = new ArrayList<Point>();
        var random = new Random();
        for (int i = 0; i < 10000; i++) {
            points.add(Point.builder().x((short) random.nextInt(10, 590)).y((short) random.nextInt(10, 590)).build());
        }
        ExposeWindow main = (client, wid, lineGc, fillGc) -> client.polyPoint(wid, lineGc, CoordMode.ORIGIN, points);
        main.start();
    }
}
