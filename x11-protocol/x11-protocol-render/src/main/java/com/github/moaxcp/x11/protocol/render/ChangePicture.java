package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.xproto.SubwindowMode;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ChangePicture implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";

  public static final byte OPCODE = 5;

  private int picture;

  private int valueMask;

  private int repeat;

  private int alphamap;

  private int alphaxorigin;

  private int alphayorigin;

  private int clipxorigin;

  private int clipyorigin;

  private int clipmask;

  private int graphicsexposure;

  private int subwindowmode;

  private int polyedge;

  private int polymode;

  private int dither;

  private int componentalpha;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangePicture readChangePicture(X11Input in) throws IOException {
    ChangePicture.ChangePictureBuilder javaBuilder = ChangePicture.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int picture = in.readCard32();
    int valueMask = in.readCard32();
    int repeat = 0;
    int alphamap = 0;
    int alphaxorigin = 0;
    int alphayorigin = 0;
    int clipxorigin = 0;
    int clipyorigin = 0;
    int clipmask = 0;
    int graphicsexposure = 0;
    int subwindowmode = 0;
    int polyedge = 0;
    int polymode = 0;
    int dither = 0;
    int componentalpha = 0;
    javaBuilder.picture(picture);
    javaBuilder.valueMask(valueMask);
    if(Cp.REPEAT.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      repeat = in.readCard32();
      javaBuilder.repeat(repeat);
    }
    if(Cp.ALPHA_MAP.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      alphamap = in.readCard32();
      javaBuilder.alphamap(alphamap);
    }
    if(Cp.ALPHA_X_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      alphaxorigin = in.readInt32();
      javaBuilder.alphaxorigin(alphaxorigin);
    }
    if(Cp.ALPHA_Y_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      alphayorigin = in.readInt32();
      javaBuilder.alphayorigin(alphayorigin);
    }
    if(Cp.CLIP_X_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      clipxorigin = in.readInt32();
      javaBuilder.clipxorigin(clipxorigin);
    }
    if(Cp.CLIP_Y_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      clipyorigin = in.readInt32();
      javaBuilder.clipyorigin(clipyorigin);
    }
    if(Cp.CLIP_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      clipmask = in.readCard32();
      javaBuilder.clipmask(clipmask);
    }
    if(Cp.GRAPHICS_EXPOSURE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      graphicsexposure = in.readCard32();
      javaBuilder.graphicsexposure(graphicsexposure);
    }
    if(Cp.SUBWINDOW_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      subwindowmode = in.readCard32();
      javaBuilder.subwindowmode(subwindowmode);
    }
    if(Cp.POLY_EDGE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      polyedge = in.readCard32();
      javaBuilder.polyedge(polyedge);
    }
    if(Cp.POLY_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      polymode = in.readCard32();
      javaBuilder.polymode(polymode);
    }
    if(Cp.DITHER.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      dither = in.readCard32();
      javaBuilder.dither(dither);
    }
    if(Cp.COMPONENT_ALPHA.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      componentalpha = in.readCard32();
      javaBuilder.componentalpha(componentalpha);
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(picture);
    out.writeCard32(valueMask);
    if(Cp.REPEAT.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(repeat);
    }
    if(Cp.ALPHA_MAP.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(alphamap);
    }
    if(Cp.ALPHA_X_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(alphaxorigin);
    }
    if(Cp.ALPHA_Y_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(alphayorigin);
    }
    if(Cp.CLIP_X_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(clipxorigin);
    }
    if(Cp.CLIP_Y_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(clipyorigin);
    }
    if(Cp.CLIP_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(clipmask);
    }
    if(Cp.GRAPHICS_EXPOSURE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(graphicsexposure);
    }
    if(Cp.SUBWINDOW_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(subwindowmode);
    }
    if(Cp.POLY_EDGE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(polyedge);
    }
    if(Cp.POLY_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(polymode);
    }
    if(Cp.DITHER.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(dither);
    }
    if(Cp.COMPONENT_ALPHA.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(componentalpha);
    }
    out.writePadAlign(getSize());
  }

  public boolean isValueMaskEnabled(@NonNull Cp... maskEnums) {
    for(Cp m : maskEnums) {
      if(!m.isEnabled(valueMask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 12 + (Cp.REPEAT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.ALPHA_MAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.ALPHA_X_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.ALPHA_Y_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.CLIP_X_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.CLIP_Y_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.CLIP_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.GRAPHICS_EXPOSURE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.SUBWINDOW_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.POLY_EDGE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.POLY_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.DITHER.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.COMPONENT_ALPHA.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ChangePictureBuilder {
    private ChangePicture.ChangePictureBuilder valueMask(int valueMask) {
      this.valueMask = valueMask;
      return this;
    }

    public boolean isValueMaskEnabled(@NonNull Cp... maskEnums) {
      for(Cp m : maskEnums) {
        if(!m.isEnabled(valueMask)) {
          return false;
        }
      }
      return true;
    }

    private ChangePicture.ChangePictureBuilder valueMaskEnable(Cp... maskEnums) {
      for(Cp m : maskEnums) {
        valueMask((int) m.enableFor(valueMask));
      }
      return this;
    }

    private ChangePicture.ChangePictureBuilder valueMaskDisable(Cp... maskEnums) {
      for(Cp m : maskEnums) {
        valueMask((int) m.disableFor(valueMask));
      }
      return this;
    }

    public ChangePicture.ChangePictureBuilder repeat(int repeat) {
      this.repeat = repeat;
      valueMaskEnable(Cp.REPEAT);
      return this;
    }

    public ChangePicture.ChangePictureBuilder repeat(Repeat repeat) {
      this.repeat = (int) repeat.getValue();
      valueMaskEnable(Cp.REPEAT);
      return this;
    }

    public ChangePicture.ChangePictureBuilder alphamap(int alphamap) {
      this.alphamap = alphamap;
      valueMaskEnable(Cp.ALPHA_MAP);
      return this;
    }

    public ChangePicture.ChangePictureBuilder alphaxorigin(int alphaxorigin) {
      this.alphaxorigin = alphaxorigin;
      valueMaskEnable(Cp.ALPHA_X_ORIGIN);
      return this;
    }

    public ChangePicture.ChangePictureBuilder alphayorigin(int alphayorigin) {
      this.alphayorigin = alphayorigin;
      valueMaskEnable(Cp.ALPHA_Y_ORIGIN);
      return this;
    }

    public ChangePicture.ChangePictureBuilder clipxorigin(int clipxorigin) {
      this.clipxorigin = clipxorigin;
      valueMaskEnable(Cp.CLIP_X_ORIGIN);
      return this;
    }

    public ChangePicture.ChangePictureBuilder clipyorigin(int clipyorigin) {
      this.clipyorigin = clipyorigin;
      valueMaskEnable(Cp.CLIP_Y_ORIGIN);
      return this;
    }

    public ChangePicture.ChangePictureBuilder clipmask(int clipmask) {
      this.clipmask = clipmask;
      valueMaskEnable(Cp.CLIP_MASK);
      return this;
    }

    public ChangePicture.ChangePictureBuilder graphicsexposure(int graphicsexposure) {
      this.graphicsexposure = graphicsexposure;
      valueMaskEnable(Cp.GRAPHICS_EXPOSURE);
      return this;
    }

    public ChangePicture.ChangePictureBuilder subwindowmode(int subwindowmode) {
      this.subwindowmode = subwindowmode;
      valueMaskEnable(Cp.SUBWINDOW_MODE);
      return this;
    }

    public ChangePicture.ChangePictureBuilder subwindowmode(SubwindowMode subwindowmode) {
      this.subwindowmode = (int) subwindowmode.getValue();
      valueMaskEnable(Cp.SUBWINDOW_MODE);
      return this;
    }

    public ChangePicture.ChangePictureBuilder polyedge(int polyedge) {
      this.polyedge = polyedge;
      valueMaskEnable(Cp.POLY_EDGE);
      return this;
    }

    public ChangePicture.ChangePictureBuilder polyedge(PolyEdge polyedge) {
      this.polyedge = (int) polyedge.getValue();
      valueMaskEnable(Cp.POLY_EDGE);
      return this;
    }

    public ChangePicture.ChangePictureBuilder polymode(int polymode) {
      this.polymode = polymode;
      valueMaskEnable(Cp.POLY_MODE);
      return this;
    }

    public ChangePicture.ChangePictureBuilder polymode(PolyMode polymode) {
      this.polymode = (int) polymode.getValue();
      valueMaskEnable(Cp.POLY_MODE);
      return this;
    }

    public ChangePicture.ChangePictureBuilder dither(int dither) {
      this.dither = dither;
      valueMaskEnable(Cp.DITHER);
      return this;
    }

    public ChangePicture.ChangePictureBuilder componentalpha(int componentalpha) {
      this.componentalpha = componentalpha;
      valueMaskEnable(Cp.COMPONENT_ALPHA);
      return this;
    }

    public int getSize() {
      return 12 + (Cp.REPEAT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.ALPHA_MAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.ALPHA_X_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.ALPHA_Y_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.CLIP_X_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.CLIP_Y_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.CLIP_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.GRAPHICS_EXPOSURE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.SUBWINDOW_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.POLY_EDGE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.POLY_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.DITHER.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.COMPONENT_ALPHA.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
    }
  }
}
