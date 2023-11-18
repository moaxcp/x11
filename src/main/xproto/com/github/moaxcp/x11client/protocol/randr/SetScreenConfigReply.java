package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import com.github.moaxcp.x11client.protocol.render.SubPixel;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetScreenConfigReply implements XReply, RandrObject {
  private byte status;

  private short sequenceNumber;

  private int newTimestamp;

  private int configTimestamp;

  private int root;

  private short subpixelOrder;

  public static SetScreenConfigReply readSetScreenConfigReply(byte status, short sequenceNumber,
      X11Input in) throws IOException {
    SetScreenConfigReply.SetScreenConfigReplyBuilder javaBuilder = SetScreenConfigReply.builder();
    int length = in.readCard32();
    int newTimestamp = in.readCard32();
    int configTimestamp = in.readCard32();
    int root = in.readCard32();
    short subpixelOrder = in.readCard16();
    byte[] pad8 = in.readPad(10);
    javaBuilder.status(status);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.newTimestamp(newTimestamp);
    javaBuilder.configTimestamp(configTimestamp);
    javaBuilder.root(root);
    javaBuilder.subpixelOrder(subpixelOrder);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(status);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(newTimestamp);
    out.writeCard32(configTimestamp);
    out.writeCard32(root);
    out.writeCard16(subpixelOrder);
    out.writePad(10);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class SetScreenConfigReplyBuilder {
    public SetScreenConfigReply.SetScreenConfigReplyBuilder status(SetConfig status) {
      this.status = (byte) status.getValue();
      return this;
    }

    public SetScreenConfigReply.SetScreenConfigReplyBuilder status(byte status) {
      this.status = status;
      return this;
    }

    public SetScreenConfigReply.SetScreenConfigReplyBuilder subpixelOrder(SubPixel subpixelOrder) {
      this.subpixelOrder = (short) subpixelOrder.getValue();
      return this;
    }

    public SetScreenConfigReply.SetScreenConfigReplyBuilder subpixelOrder(short subpixelOrder) {
      this.subpixelOrder = subpixelOrder;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
