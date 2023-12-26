package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.api.record.RecordData;
import com.github.moaxcp.x11client.api.record.RecordReply;
import com.github.moaxcp.x11client.protocol.KeySym;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.record.*;
import com.github.moaxcp.x11client.protocol.xproto.*;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11client.protocol.Utilities.toX11Input;

@Log
public class RecordIT {
    /**
     * Tests with only standard device events and directly parses the data
     * @throws IOException
     */
    @Test
    void test() throws IOException {
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
                        KeySym keysym = data.keyCodeToKeySym(e);
                        if(keysym == KeySym.XK_Escape) {
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Tests all xproto events including generic events. Also tests errors.
     * @throws IOException
     */
    @Test
    void testApi() throws IOException {
        try (X11Client control = X11Client.connect()) {
            int rc = control.nextResourceId();
            Range8 empty = Range8.builder().build();
            ExtRange emptyExtRange = ExtRange.builder()
                .major(empty)
                .minor(Range16.builder().build())
                .build();
            Range range = Range.builder()
                .deviceEvents(Range8.builder().first(KeyPressEvent.NUMBER).last(GeGenericEvent.NUMBER).build())
                .coreRequests(empty)
                //requests partially work except there are errors PutImage and a majorOpcode of -1
                //.coreRequests(Range8.builder().first(CreateWindow.OPCODE).last(NoOperation.OPCODE).build())
                .coreReplies(empty)
                //replies do not work. There is an issue with matching a sequence number with the sequence number of the reply
                //.coreReplies(Range8.builder().first(CreateWindow.OPCODE).last(NoOperation.OPCODE).build())
                .extRequests(emptyExtRange)
                .extReplies(emptyExtRange)
                .deliveredEvents(empty)
                //doesn't accept any value?
                //.deliveredEvents(Range8.builder().first((byte) 0).last((byte) 1).build())
                .clientStarted(true)
                .errors(Range8.builder()
                    .first((byte) 1)
                    .last((byte) 17)
                    .build())
                .clientDied(true)
                .build();

            CreateContext createContext = CreateContext.builder()
                .context(rc)
                .clientSpecs(Collections.singletonList(Cs.ALL_CLIENTS.getValue()))
                .ranges(Collections.singletonList(range))
                .build();

            control.send(createContext);

            control.sync();

            try (X11Client data = X11Client.connect()) {
                EnableContext enableContext = EnableContext
                    .builder()
                    .context(rc)
                    .build();
                data.send(enableContext);
                while(true) {
                    RecordReply recordReply = data.record().readNextRecord();
                    log.info(String.format("%s", recordReply));
                    Optional<KeySym> first = recordReply.getData().stream()
                        .map(RecordData::getXObject)
                        .filter(obj -> obj instanceof KeyPressEvent)
                        .map(KeyPressEvent.class::cast)
                        .map(data::keyCodeToKeySym)
                        .filter(keySym -> keySym == KeySym.XK_Escape)
                        .findFirst();
                    if (first.isPresent()) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * Attempts to read requests and replies but fails
     * @throws IOException
     */
    @Test
    void testApiWithClientSequence() throws IOException {
        try (X11Client control = X11Client.connect()) {
            int rc = control.nextResourceId();
            Range8 empty = Range8.builder().build();
            ExtRange emptyExtRange = ExtRange.builder()
                .major(empty)
                .minor(Range16.builder().build())
                .build();
            Range range = Range.builder()
                .deviceEvents(Range8.builder().first(KeyPressEvent.NUMBER).last(GeGenericEvent.NUMBER).build())
                //.coreRequests(empty)
                //requests partially work except there are errors PutImage and a majorOpcode of -1
                .coreRequests(Range8.builder().first(CreateWindow.OPCODE).last(NoOperation.OPCODE).build())
                .coreReplies(empty)
                //replies do not work. There is an issue with matching a sequence number with the sequence number of the reply
                //.coreReplies(Range8.builder().first(CreateWindow.OPCODE).last(NoOperation.OPCODE).build())
                .extRequests(emptyExtRange)
                .extReplies(emptyExtRange)
                .deliveredEvents(empty)
                //doesn't accept any value?
                //.deliveredEvents(Range8.builder().first((byte) 0).last((byte) 1).build())
                .clientStarted(true)
                .errors(Range8.builder()
                    .first((byte) 1)
                    .last((byte) 17)
                    .build())
                .clientDied(true)
                .build();

            CreateContext createContext = CreateContext.builder()
                .context(rc)
                .clientSpecs(Collections.singletonList(Cs.ALL_CLIENTS.getValue()))
                .elementHeaderEnable(HType.FROM_CLIENT_SEQUENCE)
                .ranges(Collections.singletonList(range))
                .build();

            control.send(createContext);

            control.sync();

            try (X11Client data = X11Client.connect()) {
                EnableContext enableContext = EnableContext
                    .builder()
                    .context(rc)
                    .build();
                data.send(enableContext);
                while(true) {
                    RecordReply recordReply = data.record().readNextRecord();
                    log.info(String.format("%s", recordReply));
                    Optional<KeySym> first = recordReply.getData().stream()
                        .map(RecordData::getXObject)
                        .filter(obj -> obj instanceof KeyPressEvent)
                        .map(KeyPressEvent.class::cast)
                        .map(data::keyCodeToKeySym)
                        .filter(keySym -> keySym == KeySym.XK_Escape)
                        .findFirst();
                    if (first.isPresent()) {
                        break;
                    }
                }
            }
        }
    }
}
