package com.github.moaxcp.x11.examples.record;

import com.github.moaxcp.x11.keysym.KeySym;
import com.github.moaxcp.x11.protocol.record.*;
import com.github.moaxcp.x11.protocol.xproto.GeGenericEvent;
import com.github.moaxcp.x11.protocol.xproto.KeyPressEvent;
import com.github.moaxcp.x11.x11client.X11Client;
import com.github.moaxcp.x11.x11client.api.record.RecordData;
import com.github.moaxcp.x11.x11client.api.record.RecordReply;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.primitive.IntLists;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

public class RecordApiExample {

    private static final Logger log = Logger.getLogger(RecordApiExample.class.getName());

    /**
     * Tests all xproto events including generic events. Also tests errors.
     *
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
                    .deliveredEvents(Range8.builder().first((byte) 2).last((byte) 22).build())
                    .clientStarted(true)
                    .errors(Range8.builder()
                            .first((byte) 1)
                            .last((byte) 17)
                            .build())
                    .clientDied(true)
                    .build();

            CreateContext createContext = CreateContext.builder()
                    .context(rc)
                    .clientSpecs(IntLists.immutable.of(Cs.ALL_CLIENTS.getValue()))
                    .ranges(Lists.immutable.of(range))
                    .build();

            control.send(createContext);

            control.sync();

            try (X11Client data = X11Client.connect()) {
                EnableContext enableContext = EnableContext
                        .builder()
                        .context(rc)
                        .build();
                data.send(enableContext);
                while (true) {
                    RecordReply recordReply = data.readNextRecord();
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
