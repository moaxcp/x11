package com.github.moaxcp.x11.x11client;

import com.github.moaxcp.x11.protocol.KeySym;
import com.github.moaxcp.x11.protocol.XEvent;
import com.github.moaxcp.x11.protocol.record.*;
import com.github.moaxcp.x11.protocol.xproto.KeyPressEvent;
import com.github.moaxcp.x11.protocol.xproto.MotionNotifyEvent;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.Collections;

import static com.github.moaxcp.x11.protocol.Utilities.toX11Input;

@Log
public class Record {
    /**
     * Tests with only standard device events and directly parses the data
     * @throws IOException
     */
    public static void main(String... args) throws IOException {
        try (X11Client control = X11Client.connect()) {
            int rc = control.nextResourceId();
            Range8 empty = Range8.builder().build();
            ExtRange emptyExtRange = ExtRange.builder()
                    .major(empty)
                    .minor(Range16.builder().build())
                    .build();
            Range range = Range.builder()
                    .deviceEvents(Range8.builder().first(KeyPressEvent.NUMBER).last(MotionNotifyEvent.NUMBER).build())
                    .coreRequests(empty)
                    .coreReplies(empty)
                    .extRequests(emptyExtRange)
                    .extReplies(emptyExtRange)
                    .deliveredEvents(empty)
                    .clientStarted(false)
                    .errors(empty)
                    .clientDied(false)
                    .build();

            CreateContext createContext = CreateContext.builder()
                    .context(rc)
                    .clientSpecs(Collections.singletonList(Cs.ALL_CLIENTS.getValue()))
                    .elementHeader((byte) 0)
                    .ranges(Collections.singletonList(range))
                    .build();

            control.send(createContext);

            control.sync();

            EnableContext enableContext = EnableContext
                    .builder()
                    .context(rc)
                    .build();

            try (X11Client data = X11Client.connect()) {
                EnableContextReply enableContextReply = data.send(enableContext);

                log.info(String.format("enableContextReply: %s", enableContextReply));

                while(true) {
                    EnableContextReply reply = data.getNextReply(enableContext.getReplyFunction());
                    log.info(String.format("Next reply: %s", reply));
                    XEvent replyData = data.readEvent(toX11Input(reply.getData()));
                    log.info(String.format("reply data: %s", replyData));

                    if (replyData instanceof KeyPressEvent) {
                        KeyPressEvent e = (KeyPressEvent) replyData;
                        com.github.moaxcp.x11.protocol.KeySym keysym = data.keyCodeToKeySym(e);
                        if(keysym == KeySym.XK_Escape) {
                            break;
                        }
                    }
                }
            }
        }
    }
}
