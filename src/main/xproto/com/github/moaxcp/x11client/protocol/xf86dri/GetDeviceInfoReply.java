package com.github.moaxcp.x11client.protocol.xf86dri;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetDeviceInfoReply implements XReply, Xf86driObject {
  private short sequenceNumber;

  private int framebufferHandleLow;

  private int framebufferHandleHigh;

  private int framebufferOriginOffset;

  private int framebufferSize;

  private int framebufferStride;

  @NonNull
  private List<Integer> devicePrivate;

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
    List<Integer> devicePrivate = in.readCard32((int) (Integer.toUnsignedLong(devicePrivateSize)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.framebufferHandleLow(framebufferHandleLow);
    javaBuilder.framebufferHandleHigh(framebufferHandleHigh);
    javaBuilder.framebufferOriginOffset(framebufferOriginOffset);
    javaBuilder.framebufferSize(framebufferSize);
    javaBuilder.framebufferStride(framebufferStride);
    javaBuilder.devicePrivate(devicePrivate);
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

  public static class GetDeviceInfoReplyBuilder {
    public int getSize() {
      return 32 + 4 * devicePrivate.size();
    }
  }
}
