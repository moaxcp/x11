package com.github.moaxcp.x11.examples.xproto;

import com.github.moaxcp.x11.protocol.xproto.Rectangle;
import org.eclipse.collections.api.factory.Lists;

import java.util.Random;

import static com.github.moaxcp.x11.examples.xproto.ExposeWindow.HEIGHT;
import static com.github.moaxcp.x11.examples.xproto.ExposeWindow.WIDTH;

public class PolyRectangleWindow {
    public static void main(String... args) {
        var rectangles = Lists.mutable.<Rectangle>empty();
        var random = new Random();
        for (int i = 0; i < 100; i++) {
            var x = (short) random.nextInt(10, WIDTH - 10);
            var y = (short) random.nextInt(10, HEIGHT - 10);
            rectangles.add(Rectangle.builder()
              .x(x)
              .y(y)
              .width((short) random.nextInt(0, WIDTH - 10 - x))
              .height((short) random.nextInt(0, HEIGHT - 10 - y))
              .build());
        }
        ExposeWindow main = (client, wid, lineGc, fillGc) -> client.polyRectangle(wid, lineGc, rectangles.toImmutable());
        main.start();
    }
}
