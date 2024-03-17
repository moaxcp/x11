package com.github.moaxcp.x11.protocol.xinerama;

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
public class QueryScreensReply implements XReply {
  public static final String PLUGIN_NAME = "xinerama";

  private short sequenceNumber;

  @NonNull
  private List<ScreenInfo> screenInfo;

  public static QueryScreensReply readQueryScreensReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    QueryScreensReply.QueryScreensReplyBuilder javaBuilder = QueryScreensReply.builder();
    int length = in.readCard32();
    int number = in.readCard32();
    byte[] pad5 = in.readPad(20);
    List<ScreenInfo> screenInfo = new ArrayList<>((int) (Integer.toUnsignedLong(number)));
    for(int i = 0; i < Integer.toUnsignedLong(number); i++) {
      screenInfo.add(ScreenInfo.readScreenInfo(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.screenInfo(screenInfo);
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
    int number = screenInfo.size();
    out.writeCard32(number);
    out.writePad(20);
    for(ScreenInfo t : screenInfo) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(screenInfo);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryScreensReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(screenInfo);
    }
  }
}
