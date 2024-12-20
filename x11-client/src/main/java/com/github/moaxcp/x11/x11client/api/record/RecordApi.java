package com.github.moaxcp.x11.x11client.api.record;

import com.github.moaxcp.x11.protocol.record.EnableContextReply;
import com.github.moaxcp.x11.x11client.api.XApi;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface RecordApi extends XApi {
  Map<RecordApi, ReplySequenceTracker> trackers = new ConcurrentHashMap<>();

  default RecordReply readNextRecord() {
    var tracker = trackers.computeIfAbsent(this, key -> new ReplySequenceTracker());
    EnableContextReply reply = getNextReply(EnableContextReply::readEnableContextReply);
    RecordDataParser parser = new RecordDataParser(this, reply, tracker);
    return parser.parse();
  }
}
