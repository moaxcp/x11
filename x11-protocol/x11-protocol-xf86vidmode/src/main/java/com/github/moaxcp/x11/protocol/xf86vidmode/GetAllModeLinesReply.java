package com.github.moaxcp.x11.protocol.xf86vidmode;

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
public class GetAllModeLinesReply implements XReply {
  public static final String PLUGIN_NAME = "xf86vidmode";

  private short sequenceNumber;

  @NonNull
  private List<ModeInfo> modeinfo;

  public static GetAllModeLinesReply readGetAllModeLinesReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetAllModeLinesReply.GetAllModeLinesReplyBuilder javaBuilder = GetAllModeLinesReply.builder();
    int length = in.readCard32();
    int modecount = in.readCard32();
    byte[] pad5 = in.readPad(20);
    List<ModeInfo> modeinfo = new ArrayList<>((int) (Integer.toUnsignedLong(modecount)));
    for(int i = 0; i < Integer.toUnsignedLong(modecount); i++) {
      modeinfo.add(ModeInfo.readModeInfo(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.modeinfo(modeinfo);
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
    int modecount = modeinfo.size();
    out.writeCard32(modecount);
    out.writePad(20);
    for(ModeInfo t : modeinfo) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(modeinfo);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetAllModeLinesReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(modeinfo);
    }
  }
}
