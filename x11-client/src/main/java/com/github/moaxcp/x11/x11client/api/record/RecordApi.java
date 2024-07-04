package com.github.moaxcp.x11.x11client.api.record;

import com.github.moaxcp.x11.protocol.record.EnableContextReply;
import com.github.moaxcp.x11.x11client.X11Client;

public class RecordApi {
  private final X11Client client;
  private final ReplySequenceTracker tracker = new ReplySequenceTracker();

  public RecordApi(X11Client client) {
    this.client = client;
  }

  public RecordReply readNextRecord() {
    EnableContextReply reply = client.getNextReply(EnableContextReply::readEnableContextReply);
    RecordDataParser parser = new RecordDataParser(client, reply, tracker);
    return parser.parse();
  }
}
