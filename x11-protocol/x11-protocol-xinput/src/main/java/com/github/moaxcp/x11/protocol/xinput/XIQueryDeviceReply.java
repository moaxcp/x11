package com.github.moaxcp.x11.protocol.xinput;

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

@Value
@Builder
public class XIQueryDeviceReply implements XReply {
  public static final String PLUGIN_NAME = "xinput";

  private short sequenceNumber;

  @NonNull
  private ImmutableList<XIDeviceInfo> infos;

  public static XIQueryDeviceReply readXIQueryDeviceReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    XIQueryDeviceReply.XIQueryDeviceReplyBuilder javaBuilder = XIQueryDeviceReply.builder();
    int length = in.readCard32();
    short numInfos = in.readCard16();
    byte[] pad5 = in.readPad(22);
    MutableList<XIDeviceInfo> infos = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(numInfos));
    for(int i = 0; i < Short.toUnsignedInt(numInfos); i++) {
      infos.add(XIDeviceInfo.readXIDeviceInfo(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.infos(infos.toImmutable());
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
    short numInfos = (short) infos.size();
    out.writeCard16(numInfos);
    out.writePad(22);
    for(XIDeviceInfo t : infos) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(infos);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class XIQueryDeviceReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(infos);
    }
  }
}
