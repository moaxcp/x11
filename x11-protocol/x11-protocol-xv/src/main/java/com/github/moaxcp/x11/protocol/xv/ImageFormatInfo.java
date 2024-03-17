package com.github.moaxcp.x11.protocol.xv;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import com.github.moaxcp.x11.protocol.xproto.ImageOrder;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ImageFormatInfo implements XStruct {
  public static final String PLUGIN_NAME = "xv";

  private int id;

  private byte type;

  private byte byteOrder;

  @NonNull
  private List<Byte> guid;

  private byte bpp;

  private byte numPlanes;

  private byte depth;

  private int redMask;

  private int greenMask;

  private int blueMask;

  private byte format;

  private int ySampleBits;

  private int uSampleBits;

  private int vSampleBits;

  private int vhorzYPeriod;

  private int vhorzUPeriod;

  private int vhorzVPeriod;

  private int vvertYPeriod;

  private int vvertUPeriod;

  private int vvertVPeriod;

  @NonNull
  private List<Byte> vcompOrder;

  private byte vscanlineOrder;

  public static ImageFormatInfo readImageFormatInfo(X11Input in) throws IOException {
    ImageFormatInfo.ImageFormatInfoBuilder javaBuilder = ImageFormatInfo.builder();
    int id = in.readCard32();
    byte type = in.readCard8();
    byte byteOrder = in.readCard8();
    byte[] pad3 = in.readPad(2);
    List<Byte> guid = in.readCard8(16);
    byte bpp = in.readCard8();
    byte numPlanes = in.readCard8();
    byte[] pad7 = in.readPad(2);
    byte depth = in.readCard8();
    byte[] pad9 = in.readPad(3);
    int redMask = in.readCard32();
    int greenMask = in.readCard32();
    int blueMask = in.readCard32();
    byte format = in.readCard8();
    byte[] pad14 = in.readPad(3);
    int ySampleBits = in.readCard32();
    int uSampleBits = in.readCard32();
    int vSampleBits = in.readCard32();
    int vhorzYPeriod = in.readCard32();
    int vhorzUPeriod = in.readCard32();
    int vhorzVPeriod = in.readCard32();
    int vvertYPeriod = in.readCard32();
    int vvertUPeriod = in.readCard32();
    int vvertVPeriod = in.readCard32();
    List<Byte> vcompOrder = in.readCard8(32);
    byte vscanlineOrder = in.readCard8();
    byte[] pad26 = in.readPad(11);
    javaBuilder.id(id);
    javaBuilder.type(type);
    javaBuilder.byteOrder(byteOrder);
    javaBuilder.guid(guid);
    javaBuilder.bpp(bpp);
    javaBuilder.numPlanes(numPlanes);
    javaBuilder.depth(depth);
    javaBuilder.redMask(redMask);
    javaBuilder.greenMask(greenMask);
    javaBuilder.blueMask(blueMask);
    javaBuilder.format(format);
    javaBuilder.ySampleBits(ySampleBits);
    javaBuilder.uSampleBits(uSampleBits);
    javaBuilder.vSampleBits(vSampleBits);
    javaBuilder.vhorzYPeriod(vhorzYPeriod);
    javaBuilder.vhorzUPeriod(vhorzUPeriod);
    javaBuilder.vhorzVPeriod(vhorzVPeriod);
    javaBuilder.vvertYPeriod(vvertYPeriod);
    javaBuilder.vvertUPeriod(vvertUPeriod);
    javaBuilder.vvertVPeriod(vvertVPeriod);
    javaBuilder.vcompOrder(vcompOrder);
    javaBuilder.vscanlineOrder(vscanlineOrder);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(id);
    out.writeCard8(type);
    out.writeCard8(byteOrder);
    out.writePad(2);
    out.writeCard8(guid);
    out.writeCard8(bpp);
    out.writeCard8(numPlanes);
    out.writePad(2);
    out.writeCard8(depth);
    out.writePad(3);
    out.writeCard32(redMask);
    out.writeCard32(greenMask);
    out.writeCard32(blueMask);
    out.writeCard8(format);
    out.writePad(3);
    out.writeCard32(ySampleBits);
    out.writeCard32(uSampleBits);
    out.writeCard32(vSampleBits);
    out.writeCard32(vhorzYPeriod);
    out.writeCard32(vhorzUPeriod);
    out.writeCard32(vhorzVPeriod);
    out.writeCard32(vvertYPeriod);
    out.writeCard32(vvertUPeriod);
    out.writeCard32(vvertVPeriod);
    out.writeCard8(vcompOrder);
    out.writeCard8(vscanlineOrder);
    out.writePad(11);
  }

  @Override
  public int getSize() {
    return 80 + 1 * guid.size() + 1 * vcompOrder.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ImageFormatInfoBuilder {
    public ImageFormatInfo.ImageFormatInfoBuilder type(ImageFormatInfoType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public ImageFormatInfo.ImageFormatInfoBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public ImageFormatInfo.ImageFormatInfoBuilder byteOrder(ImageOrder byteOrder) {
      this.byteOrder = (byte) byteOrder.getValue();
      return this;
    }

    public ImageFormatInfo.ImageFormatInfoBuilder byteOrder(byte byteOrder) {
      this.byteOrder = byteOrder;
      return this;
    }

    public ImageFormatInfo.ImageFormatInfoBuilder format(ImageFormatInfoFormat format) {
      this.format = (byte) format.getValue();
      return this;
    }

    public ImageFormatInfo.ImageFormatInfoBuilder format(byte format) {
      this.format = format;
      return this;
    }

    public ImageFormatInfo.ImageFormatInfoBuilder vscanlineOrder(ScanlineOrder vscanlineOrder) {
      this.vscanlineOrder = (byte) vscanlineOrder.getValue();
      return this;
    }

    public ImageFormatInfo.ImageFormatInfoBuilder vscanlineOrder(byte vscanlineOrder) {
      this.vscanlineOrder = vscanlineOrder;
      return this;
    }

    public int getSize() {
      return 80 + 1 * guid.size() + 1 * vcompOrder.size();
    }
  }
}
