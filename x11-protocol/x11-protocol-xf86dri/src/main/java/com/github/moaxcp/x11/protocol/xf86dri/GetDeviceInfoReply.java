package com.github.moaxcp.x11.protocol.xf86dri;

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
public class GetDeviceInfoReply implements XReply {
  public static final String PLUGIN_NAME = "xf86dri";

  private short sequenceNumber;

  private int framebufferHandleLow;

  private int framebufferHandleHigh;

  private int framebufferOriginOffset;

  private int framebufferSize;

  private int framebufferStride;

  @NonNull
  private IntList devicePrivate;

  public static GetDeviceInfoReply readGetDeviceInfoReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetDeviceInfoReply.GetDeviceInfoReplyBuilder javaBuilder = GetDeviceInfoReply.builder();
    int length = in.readCard32();
    int framebufferHandleLow = in.readCard32();
    int framebufferHandleHigh = in.readCard32();
    int framebufferOriginOffset = in.readCard32();
    int framebufferSize = in.readCard32();
    int framebufferStride = in.readCard32();
    int devicePrivateSize = in.readCard32();
    IntList devicePrivate = in.readCard32((int) (Integer.toUnsignedLong(devicePrivateSize)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.framebufferHandleLow(framebufferHandleLow);
    javaBuilder.framebufferHandleHigh(framebufferHandleHigh);
    javaBuilder.framebufferOriginOffset(framebufferOriginOffset);
    javaBuilder.framebufferSize(framebufferSize);
    javaBuilder.framebufferStride(framebufferStride);
    javaBuilder.devicePrivate(devicePrivate.toImmutable());
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
    out.writeCard32(framebufferHandleLow);
    out.writeCard32(framebufferHandleHigh);
    out.writeCard32(framebufferOriginOffset);
    out.writeCard32(framebufferSize);
    out.writeCard32(framebufferStride);
    int devicePrivateSize = devicePrivate.size();
    out.writeCard32(devicePrivateSize);
    out.writeCard32(devicePrivate);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * devicePrivate.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetDeviceInfoReplyBuilder {
    public int getSize() {
      return 32 + 4 * devicePrivate.size();
    }
  }
}
