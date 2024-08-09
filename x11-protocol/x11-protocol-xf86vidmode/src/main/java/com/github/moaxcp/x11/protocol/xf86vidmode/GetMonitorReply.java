package com.github.moaxcp.x11.protocol.xf86vidmode;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ImmutableByteList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;

@Value
@Builder
public class GetMonitorReply implements XReply {
  public static final String PLUGIN_NAME = "xf86vidmode";

  private short sequenceNumber;

  @NonNull
  private ImmutableIntList hsync;

  @NonNull
  private ImmutableIntList vsync;

  @NonNull
  private ImmutableByteList vendor;

  @NonNull
  private ImmutableByteList alignmentPad;

  @NonNull
  private ImmutableByteList model;

  public static GetMonitorReply readGetMonitorReply(byte pad1, short sequenceNumber, X11Input in)
      throws IOException {
    GetMonitorReply.GetMonitorReplyBuilder javaBuilder = GetMonitorReply.builder();
    int length = in.readCard32();
    byte vendorLength = in.readCard8();
    byte modelLength = in.readCard8();
    byte numHsync = in.readCard8();
    byte numVsync = in.readCard8();
    byte[] pad8 = in.readPad(20);
    ImmutableIntList hsync = in.readCard32(Byte.toUnsignedInt(numHsync));
    ImmutableIntList vsync = in.readCard32(Byte.toUnsignedInt(numVsync));
    ImmutableByteList vendor = in.readChar(Byte.toUnsignedInt(vendorLength));
    ImmutableByteList alignmentPad = in.readVoid((Byte.toUnsignedInt(vendorLength) + 3) & (~ (3)) - Byte.toUnsignedInt(vendorLength));
    ImmutableByteList model = in.readChar(Byte.toUnsignedInt(modelLength));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.hsync(hsync);
    javaBuilder.vsync(vsync);
    javaBuilder.vendor(vendor);
    javaBuilder.alignmentPad(alignmentPad);
    javaBuilder.model(model);
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
    byte vendorLength = (byte) vendor.size();
    out.writeCard8(vendorLength);
    byte modelLength = (byte) model.size();
    out.writeCard8(modelLength);
    byte numHsync = (byte) hsync.size();
    out.writeCard8(numHsync);
    byte numVsync = (byte) vsync.size();
    out.writeCard8(numVsync);
    out.writePad(20);
    out.writeCard32(hsync);
    out.writeCard32(vsync);
    out.writeChar(vendor);
    out.writeVoid(alignmentPad);
    out.writeChar(model);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * hsync.size() + 4 * vsync.size() + 1 * vendor.size() + 1 * alignmentPad.size() + 1 * model.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetMonitorReplyBuilder {
    public int getSize() {
      return 32 + 4 * hsync.size() + 4 * vsync.size() + 1 * vendor.size() + 1 * alignmentPad.size() + 1 * model.size();
    }
  }
}
