package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class GetFBConfigsReply implements XReply {
  public static final String PLUGIN_NAME = "glx";

  private short sequenceNumber;

  private int numFbConfigs;

  private int numProperties;

  @NonNull
  private IntList propertyList;

  public static GetFBConfigsReply readGetFBConfigsReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetFBConfigsReply.GetFBConfigsReplyBuilder javaBuilder = GetFBConfigsReply.builder();
    int length = in.readCard32();
    int numFbConfigs = in.readCard32();
    int numProperties = in.readCard32();
    byte[] pad6 = in.readPad(16);
    IntList propertyList = in.readCard32((int) (Integer.toUnsignedLong(length)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.numFbConfigs(numFbConfigs);
    javaBuilder.numProperties(numProperties);
    javaBuilder.propertyList(propertyList.toImmutable());
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
    out.writeCard32(numFbConfigs);
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

  public static class GetFBConfigsReplyBuilder {
    public int getSize() {
      return 32 + 4 * propertyList.size();
    }
  }
}
