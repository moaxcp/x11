package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CreateMode implements TwoWayRequest<CreateModeReply>, RandrObject {
  public static final byte OPCODE = 16;

  private int window;

  @NonNull
  private ModeInfo modeInfo;

  @NonNull
  private List<Byte> name;

  public XReplyFunction<CreateModeReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> CreateModeReply.readCreateModeReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateMode readCreateMode(X11Input in) throws IOException {
    CreateMode.CreateModeBuilder javaBuilder = CreateMode.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int window = in.readCard32();
    javaStart += 4;
    ModeInfo modeInfo = ModeInfo.readModeInfo(in);
    javaStart += modeInfo.getSize();
    List<Byte> name = in.readChar(javaStart - length);
    javaBuilder.window(window);
    javaBuilder.modeInfo(modeInfo);
    javaBuilder.name(name);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    modeInfo.write(out);
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 40 + 1 * name.size();
  }

  public static class CreateModeBuilder {
    public int getSize() {
      return 40 + 1 * name.size();
    }
  }
}
