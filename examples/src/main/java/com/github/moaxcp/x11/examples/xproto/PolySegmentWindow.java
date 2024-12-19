package com.github.moaxcp.x11.examples.xproto;

import com.github.moaxcp.x11.protocol.xproto.Segment;

import java.util.ArrayList;
import java.util.Random;

import static com.github.moaxcp.x11.examples.xproto.ExposeWindow.HEIGHT;
import static com.github.moaxcp.x11.examples.xproto.ExposeWindow.WIDTH;

public class PolySegmentWindow {
    public static void main(String... args) {
        var segments = new ArrayList<Segment>();
        var random = new Random();
        for (int i = 0; i < 100; i++) {
            segments.add(Segment.builder()
              .x1((short) random.nextInt(10, WIDTH - 10))
              .y1((short) random.nextInt(10, HEIGHT - 10))
              .x2((short) random.nextInt(10, WIDTH - 10))
              .y2((short) random.nextInt(10, HEIGHT - 10))
              .build());
        }
        ExposeWindow main = (client, wid, lineGc, fillGc) -> client.polySegment(wid, lineGc, segments);
        main.start();
    }
}
