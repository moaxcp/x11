package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class RenderLarge implements OneWayRequest {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = 2;

  private int contextTag;

  private short requestNum;

  private short requestTotal;

  @NonNull
  private List<Byte> data;

  public byte getOpCode() {
    return OPCODE;
  }

  public static RenderLarge readRenderLarge(X11Input in) throws IOException {
    RenderLarge.RenderLargeBuilder javaBuilder = RenderLarge.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int contextTag = in.readCard32();
    short requestNum = in.readCard16();
    short requestTotal = in.readCard16();
    int dataLen = in.readCard32();
    List<Byte> data = in.readByte((int) (Integer.toUnsignedLong(dataLen)));
    javaBuilder.contextTag(contextTag);
    javaBuilder.requestNum(requestNum);
    javaBuilder.requestTotal(requestTotal);
    javaBuilder.data(data);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    out.writeCard16(requestNum);
    out.writeCard16(requestTotal);
    int dataLen = data.size();
    out.writeCard32(dataLen);
    out.writeByte(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + 1 * data.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class RenderLargeBuilder {
    public int getSize() {
      return 16 + 1 * data.size();
    }
  }
}
