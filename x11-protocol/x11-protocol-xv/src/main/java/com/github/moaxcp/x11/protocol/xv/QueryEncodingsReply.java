package com.github.moaxcp.x11.protocol.xv;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class QueryEncodingsReply implements XReply {
  public static final String PLUGIN_NAME = "xv";

  private short sequenceNumber;

  @NonNull
  private List<EncodingInfo> info;

  public static QueryEncodingsReply readQueryEncodingsReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    QueryEncodingsReply.QueryEncodingsReplyBuilder javaBuilder = QueryEncodingsReply.builder();
    int length = in.readCard32();
    short numEncodings = in.readCard16();
    byte[] pad5 = in.readPad(22);
    List<EncodingInfo> info = new ArrayList<>(Short.toUnsignedInt(numEncodings));
    for(int i = 0; i < Short.toUnsignedInt(numEncodings); i++) {
      info.add(EncodingInfo.readEncodingInfo(in));
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
    short numEncodings = (short) info.size();
    out.writeCard16(numEncodings);
    out.writePad(22);
    for(EncodingInfo t : info) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(info);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryEncodingsReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(info);
    }
  }
}
