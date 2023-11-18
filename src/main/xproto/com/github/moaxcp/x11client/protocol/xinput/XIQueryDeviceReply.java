package com.github.moaxcp.x11client.protocol.xinput;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class XIQueryDeviceReply implements XReply, XinputObject {
  private short sequenceNumber;

  @NonNull
  private List<XIDeviceInfo> infos;

  public static XIQueryDeviceReply readXIQueryDeviceReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    XIQueryDeviceReply.XIQueryDeviceReplyBuilder javaBuilder = XIQueryDeviceReply.builder();
    int length = in.readCard32();
    short numInfos = in.readCard16();
    byte[] pad5 = in.readPad(22);
    List<XIDeviceInfo> infos = new ArrayList<>(Short.toUnsignedInt(numInfos));
    for(int i = 0; i < Short.toUnsignedInt(numInfos); i++) {
      infos.add(XIDeviceInfo.readXIDeviceInfo(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.infos(infos);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
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

  public static class XIQueryDeviceReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(infos);
    }
  }
}
