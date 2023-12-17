package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.X11OutputStream;
import com.github.moaxcp.x11client.protocol.record.*;
import java.io.ByteArrayOutputStream;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;

@Log
public class RecordIT {
    @Test
    void test() {
        try (X11Client client = X11Client.connect()) {
            int rc = client.nextResourceId();
            Range8 empty = Range8.builder().build();
            ExtRange emptyExtRange = ExtRange.builder()
                    .major(empty)
                    .minor(Range16.builder().build())
                    .build();
            Range range = Range.builder()
                    .deviceEvents(Range8.builder().first((byte) 2).last((byte) 6).build())
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

            try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); X11OutputStream out = new X11OutputStream(baos)) {
                createContext.write((byte) -110, out);
                log.info(String.format("createContext bytes size = %d", baos.toByteArray().length));
            }

            log.info(String.format("createContext: %s, size: %d, length: %d", createContext, createContext.getSize(), createContext.getLength()));

            client.send(createContext);

            client.sync();

            EnableContext enableContext = EnableContext
                    .builder()
                    .context(rc)
                    .build();
            EnableContextReply enableContextReply = client.send(enableContext);
            if (enableContextReply.getCategory() == 0) {
                for (Byte datum : enableContextReply.getData()) {
                    System.out.println(datum);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
