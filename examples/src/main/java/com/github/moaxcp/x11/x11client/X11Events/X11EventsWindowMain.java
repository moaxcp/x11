package com.github.moaxcp.x11.x11client.X11Events;

import com.github.moaxcp.x11.xephyr.XephyrRunner;

import java.io.IOException;

public class X11EventsWindowMain {
    public static void main(String... args) throws IOException, InterruptedException {
        XephyrRunner runner = XephyrRunner.builder()
                .ac(true)
                .br(true)
                .noreset(true)
                .screen("1200x1000")
                .softCursor(true)
                .build();
        runner.start();
        new X11EventsWindow(runner.getDisplay()).start();
        runner.stop();
    }
}
