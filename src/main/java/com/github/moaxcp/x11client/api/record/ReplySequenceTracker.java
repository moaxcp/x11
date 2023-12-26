package com.github.moaxcp.x11client.api.record;

import com.github.moaxcp.x11client.protocol.XReply;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.util.HashMap;
import java.util.Map;
import lombok.Value;

class ReplySequenceTracker {
  @Value
  private static class Key {
    int xidBase;
    short sequence;
  }

  private final Map<Key, XReplyFunction<XReply>> replyFunctions = new HashMap<>();

  void add(int xidBase, int sequence, XReplyFunction<XReply> function) {
    replyFunctions.put(new Key(xidBase, (short) (sequence & 0x0000FFFF)), function);
  }

  XReplyFunction<XReply> use(int xidBase, short sequence) {
    return replyFunctions.remove(new Key(xidBase, sequence));
  }
}
