package com.github.moaxcp.x11client.protocol.xtest;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FakeInput implements OneWayRequest, XtestObject {
  public static final byte OPCODE = 2;

  private byte type;

  private byte detail;

  private int time;

  private int root;

  private short rootX;

  private short rootY;

  private byte deviceid;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FakeInput readFakeInput(X11Input in) throws IOException {
    FakeInput.FakeInputBuilder javaBuilder = FakeInput.builder();
    byte type = in.readByte();
    short length = in.readCard16();
    byte detail = in.readByte();
    byte[] pad4 = in.readPad(2);
    int time = in.readCard32();
    int root = in.readCard32();
    byte[] pad7 = in.readPad(8);
    short rootX = in.readInt16();
    short rootY = in.readInt16();
    byte[] pad10 = in.readPad(7);
    byte deviceid = in.readCard8();
    javaBuilder.type(type);
    javaBuilder.detail(detail);
    javaBuilder.time(time);
    javaBuilder.root(root);
    javaBuilder.rootX(rootX);
    javaBuilder.rootY(rootY);
    javaBuilder.deviceid(deviceid);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writeByte(type);
    out.writeCard16((short) getLength());
    out.writeByte(detail);
    out.writePad(2);
    out.writeCard32(time);
    out.writeCard32(root);
    out.writePad(8);
    out.writeInt16(rootX);
    out.writeInt16(rootY);
    out.writePad(7);
    out.writeCard8(deviceid);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 35;
  }

  public static class FakeInputBuilder {
    public int getSize() {
      return 35;
    }
  }
}
