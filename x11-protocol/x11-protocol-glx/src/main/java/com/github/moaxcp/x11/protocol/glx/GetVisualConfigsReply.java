package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetVisualConfigsReply implements XReply {
  public static final String PLUGIN_NAME = "glx";

  private short sequenceNumber;

  private int numVisuals;

  private int numProperties;

  @NonNull
  private List<Integer> propertyList;

  public static GetVisualConfigsReply readGetVisualConfigsReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetVisualConfigsReply.GetVisualConfigsReplyBuilder javaBuilder = GetVisualConfigsReply.builder();
    int length = in.readCard32();
    int numVisuals = in.readCard32();
    int numProperties = in.readCard32();
    byte[] pad6 = in.readPad(16);
    List<Integer> propertyList = in.readCard32((int) (Integer.toUnsignedLong(length)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.numVisuals(numVisuals);
    javaBuilder.numProperties(numProperties);
    javaBuilder.propertyList(propertyList);
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
    int length = propertyList.size();
    out.writeCard32(getLength());
    out.writeCard32(numVisuals);
    out.writeCard32(numProperties);
    out.writePad(16);
    out.writeCard32(propertyList);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * propertyList.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetVisualConfigsReplyBuilder {
    public int getSize() {
      return 32 + 4 * propertyList.size();
    }
  }
}
