package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SetPanning implements TwoWayRequest<SetPanningReply>, RandrObject {
  public static final byte OPCODE = 29;

  private int crtc;

  private int timestamp;

  private short left;

  private short top;

  private short width;

  private short height;

  private short trackLeft;

  private short trackTop;

  private short trackWidth;

  private short trackHeight;

  private short borderLeft;

  private short borderTop;

  private short borderRight;

  private short borderBottom;

  public XReplyFunction<SetPanningReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> SetPanningReply.readSetPanningReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetPanning readSetPanning(X11Input in) throws IOException {
    SetPanning.SetPanningBuilder javaBuilder = SetPanning.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int crtc = in.readCard32();
    int timestamp = in.readCard32();
    short left = in.readCard16();
    short top = in.readCard16();
    short width = in.readCard16();
    short height = in.readCard16();
    short trackLeft = in.readCard16();
    short trackTop = in.readCard16();
    short trackWidth = in.readCard16();
    short trackHeight = in.readCard16();
    short borderLeft = in.readInt16();
    short borderTop = in.readInt16();
    short borderRight = in.readInt16();
    short borderBottom = in.readInt16();
    javaBuilder.crtc(crtc);
    javaBuilder.timestamp(timestamp);
    javaBuilder.left(left);
    javaBuilder.top(top);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.trackLeft(trackLeft);
    javaBuilder.trackTop(trackTop);
    javaBuilder.trackWidth(trackWidth);
    javaBuilder.trackHeight(trackHeight);
    javaBuilder.borderLeft(borderLeft);
    javaBuilder.borderTop(borderTop);
    javaBuilder.borderRight(borderRight);
    javaBuilder.borderBottom(borderBottom);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(crtc);
    out.writeCard32(timestamp);
    out.writeCard16(left);
    out.writeCard16(top);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(trackLeft);
    out.writeCard16(trackTop);
    out.writeCard16(trackWidth);
    out.writeCard16(trackHeight);
    out.writeInt16(borderLeft);
    out.writeInt16(borderTop);
    out.writeInt16(borderRight);
    out.writeInt16(borderBottom);
  }

  @Override
  public int getSize() {
    return 36;
  }

  public static class SetPanningBuilder {
    public int getSize() {
      return 36;
    }
  }
}
