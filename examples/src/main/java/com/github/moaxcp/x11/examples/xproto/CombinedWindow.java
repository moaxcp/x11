package com.github.moaxcp.x11.examples.xproto;

import com.github.moaxcp.x11.protocol.xproto.*;
import org.eclipse.collections.api.factory.Lists;

import java.util.List;

public class CombinedWindow {
    public static void main(String... args) {
        var points = Lists.immutable.of(Point.builder().x((short) 10).y((short) 10).build(),
            Point.builder().x((short) 10).y((short) 20).build(),
            Point.builder().x((short) 20).y((short) 10).build(),
            Point.builder().x((short) 20).y((short) 20).build());

        var polyLine = Lists.immutable.of(Point.builder().x((short) 50).y((short) 10).build(),
            Point.builder().x((short) 5).y((short) 20).build(), // Rest of points are relative
            Point.builder().x((short) 25).y((short) -20).build(),
            Point.builder().x((short) 10).y((short) 10).build());

        var segments = Lists.immutable.of(Segment.builder().x1((short) 100).y1((short) 10).x2((short) 140).y2((short) 30).build(),
            Segment.builder().x1((short) 110).x1((short) 25).x2((short) 130).y2((short) 60).build());

        var rectangles = Lists.immutable.of(Rectangle.builder().x((short) 10).y((short) 50).width((short) 40).height((short) 20).build(),
            Rectangle.builder().x((short) 80).y((short) 50).width((short) 10).height((short) 40).build());

        var arcs = Lists.immutable.of(Arc.builder().x((short) 10).y((short) 100).width((short) 60).height((short) 40).angle1((short) 0).angle2((short) (90 << 6)).build(),
            Arc.builder().x((short) 90).y((short) 100).width((short) 55).height((short) 40).angle1((short) 0).angle2((short) (270 << 6)).build());

        ExposeWindow main = (client, wid, lineGc, fillGc) -> {
            client.polyPoint(wid, fillGc, CoordMode.ORIGIN, points);
            client.polyLine(wid, lineGc, CoordMode.PREVIOUS, polyLine);
            client.polySegment(wid, lineGc, segments);
            client.polyRectangle(wid, lineGc, rectangles);
            client.polyArc(wid, lineGc, arcs);
        };
        main.start();
    }
}
