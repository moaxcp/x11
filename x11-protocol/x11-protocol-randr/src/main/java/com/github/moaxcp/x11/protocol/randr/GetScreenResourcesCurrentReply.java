package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ByteList;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class GetScreenResourcesCurrentReply implements XReply {
  public static final String PLUGIN_NAME = "randr";

  private short sequenceNumber;

  private int timestamp;

  private int configTimestamp;

  @NonNull
  private IntList crtcs;

  @NonNull
  private IntList outputs;

  @NonNull
  private ImmutableList<ModeInfo> modes;

  @NonNull
  private ByteList names;

  public static GetScreenResourcesCurrentReply readGetScreenResourcesCurrentReply(byte pad1,
      short sequenceNumber, X11Input in) throws IOException {
    GetScreenResourcesCurrentReply.GetScreenResourcesCurrentReplyBuilder javaBuilder = GetScreenResourcesCurrentReply.builder();
    int length = in.readCard32();
    int timestamp = in.readCard32();
    int configTimestamp = in.readCard32();
    short numCrtcs = in.readCard16();
    short numOutputs = in.readCard16();
    short numModes = in.readCard16();
    short namesLen = in.readCard16();
    byte[] pad10 = in.readPad(8);
    IntList crtcs = in.readCard32(Short.toUnsignedInt(numCrtcs));
    IntList outputs = in.readCard32(Short.toUnsignedInt(numOutputs));
    MutableList<ModeInfo> modes = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(numModes));
    for(int i = 0; i < Short.toUnsignedInt(numModes); i++) {
      modes.add(ModeInfo.readModeInfo(in));
    }
    ByteList names = in.readByte(Short.toUnsignedInt(namesLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timestamp(timestamp);
    javaBuilder.configTimestamp(configTimestamp);
    javaBuilder.crtcs(crtcs.toImmutable());
    javaBuilder.outputs(outputs.toImmutable());
    javaBuilder.modes(modes.toImmutable());
    javaBuilder.names(names.toImmutable());
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(timestamp);
    out.writeCard32(configTimestamp);
    short numCrtcs = (short) crtcs.size();
    out.writeCard16(numCrtcs);
    short numOutputs = (short) outputs.size();
    out.writeCard16(numOutputs);
    short numModes = (short) modes.size();
    out.writeCard16(numModes);
    short namesLen = (short) names.size();
    out.writeCard16(namesLen);
    out.writePad(8);
    out.writeCard32(crtcs);
    out.writeCard32(outputs);
    for(ModeInfo t : modes) {
      t.write(out);
    }
    out.writeByte(names);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * crtcs.size() + 4 * outputs.size() + XObject.sizeOf(modes) + 1 * names.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetScreenResourcesCurrentReplyBuilder {
    public int getSize() {
      return 32 + 4 * crtcs.size() + 4 * outputs.size() + XObject.sizeOf(modes) + 1 * names.size();
    }
  }
}
