package com.github.moaxcp.x11client.protocol.xv;

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
public class QueryAdaptorsReply implements XReply, XvObject {
  private short sequenceNumber;

  @NonNull
  private List<AdaptorInfo> info;

  public static QueryAdaptorsReply readQueryAdaptorsReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    QueryAdaptorsReply.QueryAdaptorsReplyBuilder javaBuilder = QueryAdaptorsReply.builder();
    int length = in.readCard32();
    short numAdaptors = in.readCard16();
    byte[] pad5 = in.readPad(22);
    List<AdaptorInfo> info = new ArrayList<>(Short.toUnsignedInt(numAdaptors));
    for(int i = 0; i < Short.toUnsignedInt(numAdaptors); i++) {
      info.add(AdaptorInfo.readAdaptorInfo(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.info(info);
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
    short numAdaptors = (short) info.size();
    out.writeCard16(numAdaptors);
    out.writePad(22);
    for(AdaptorInfo t : info) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(info);
  }

  public static class QueryAdaptorsReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(info);
    }
  }
}
