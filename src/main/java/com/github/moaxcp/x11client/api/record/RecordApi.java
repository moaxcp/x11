package com.github.moaxcp.x11client.api.record;

import com.github.moaxcp.x11client.X11Client;
import com.github.moaxcp.x11client.protocol.record.EnableContextReply;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RecordApi {
  private final X11Client client;
  private final ReplySequenceTracker tracker = new ReplySequenceTracker();

  public RecordReply readNextRecord() {
    EnableContextReply reply = client.getNextReply(EnableContextReply::readEnableContextReply);
    RecordDataParser parser = new RecordDataParser(client, reply, tracker);
    return parser.parse();
  }
}
