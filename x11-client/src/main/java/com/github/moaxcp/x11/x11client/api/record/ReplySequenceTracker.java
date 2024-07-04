package com.github.moaxcp.x11.x11client.api.record;

import com.github.moaxcp.x11.protocol.XReply;
import com.github.moaxcp.x11.protocol.XReplyFunction;

import java.util.HashMap;
import java.util.Map;

class ReplySequenceTracker {
  private static final class Key {
    private final int xidBase;
    private final short sequence;

    public Key(int xidBase, short sequence) {
      this.xidBase = xidBase;
      this.sequence = sequence;
    }

    public int getXidBase() {
      return this.xidBase;
    }

    public short getSequence() {
      return this.sequence;
    }

    public boolean equals(final Object o) {
      if (o == this) return true;
      if (!(o instanceof Key)) return false;
      final Key other = (Key) o;
      if (this.getXidBase() != other.getXidBase()) return false;
      if (this.getSequence() != other.getSequence()) return false;
      return true;
    }

    public int hashCode() {
      final int PRIME = 59;
      int result = 1;
      result = result * PRIME + this.getXidBase();
      result = result * PRIME + this.getSequence();
      return result;
    }

    public String toString() {
      return "ReplySequenceTracker.Key(xidBase=" + this.getXidBase() + ", sequence=" + this.getSequence() + ")";
    }
  }

  private final Map<Key, XReplyFunction<XReply>> replyFunctions = new HashMap<>();

  void add(int xidBase, int sequence, XReplyFunction<XReply> function) {
    replyFunctions.put(new Key(xidBase, (short) (sequence & 0x0000FFFF)), function);
  }

  XReplyFunction<XReply> use(int xidBase, short sequence) {
    return replyFunctions.remove(new Key(xidBase, sequence));
  }
}
