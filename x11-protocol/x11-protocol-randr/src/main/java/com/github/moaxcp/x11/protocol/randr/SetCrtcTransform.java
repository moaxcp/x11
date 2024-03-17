package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.render.Transform;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetCrtcTransform implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";

  public static final byte OPCODE = 26;

  private int crtc;

  @NonNull
  private Transform transform;

  @NonNull
  private List<Byte> filterName;

  @NonNull
  private List<Integer> filterParams;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetCrtcTransform readSetCrtcTransform(X11Input in) throws IOException {
    SetCrtcTransform.SetCrtcTransformBuilder javaBuilder = SetCrtcTransform.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int crtc = in.readCard32();
    javaStart += 4;
    Transform transform = Transform.readTransform(in);
    javaStart += transform.getSize();
    short filterLen = in.readCard16();
    javaStart += 2;
    byte[] pad6 = in.readPad(2);
    javaStart += 2;
    List<Byte> filterName = in.readChar(Short.toUnsignedInt(filterLen));
    javaStart += 1 * filterName.size();
    in.readPadAlign(Short.toUnsignedInt(filterLen));
    javaStart += XObject.getSizeForPadAlign(4, 1 * filterName.size());
    List<Integer> filterParams = in.readInt32(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.crtc(crtc);
    javaBuilder.transform(transform);
    javaBuilder.filterName(filterName);
    javaBuilder.filterParams(filterParams);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(crtc);
    transform.write(out);
    short filterLen = (short) filterName.size();
    out.writeCard16(filterLen);
    out.writePad(2);
    out.writeChar(filterName);
    out.writePadAlign(Short.toUnsignedInt(filterLen));
    out.writeInt32(filterParams);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 48 + 1 * filterName.size() + XObject.getSizeForPadAlign(4, 1 * filterName.size()) + 4 * filterParams.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetCrtcTransformBuilder {
    public int getSize() {
      return 48 + 1 * filterName.size() + XObject.getSizeForPadAlign(4, 1 * filterName.size()) + 4 * filterParams.size();
    }
  }
}
