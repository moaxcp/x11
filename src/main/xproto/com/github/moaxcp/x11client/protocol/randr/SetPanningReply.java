package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetPanningReply implements XReply, RandrObject {
  private byte status;

  private short sequenceNumber;

  private int timestamp;

  public static SetPanningReply readSetPanningReply(byte status, short sequenceNumber, X11Input in)
      throws IOException {
    SetPanningReply.SetPanningReplyBuilder javaBuilder = SetPanningReply.builder();
    int length = in.readCard32();
    int timestamp = in.readCard32();
    in.readPad(20);
    javaBuilder.status(status);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timestamp(timestamp);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(status);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(timestamp);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public static class SetPanningReplyBuilder {
    public SetPanningReply.SetPanningReplyBuilder status(SetConfig status) {
      this.status = (byte) status.getValue();
      return this;
    }

    public SetPanningReply.SetPanningReplyBuilder status(byte status) {
      this.status = status;
      return this;
    }

    public int getSize() {
      return 12;
    }
  }
}
