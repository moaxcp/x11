package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CreateGC implements OneWayRequest, XprotoObject {
  public static final byte OPCODE = 55;

  private int cid;

  private int drawable;

  private int valueMask;

  private int function;

  private int planeMask;

  private int foreground;

  private int background;

  private int lineWidth;

  private int lineStyle;

  private int capStyle;

  private int joinStyle;

  private int fillStyle;

  private int fillRule;

  private int tile;

  private int stipple;

  private int tileStippleXOrigin;

  private int tileStippleYOrigin;

  private int font;

  private int subwindowMode;

  private int graphicsExposures;

  private int clipXOrigin;

  private int clipYOrigin;

  private int clipMask;

  private int dashOffset;

  private int dashes;

  private int arcMode;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateGC readCreateGC(X11Input in) throws IOException {
    CreateGC.CreateGCBuilder javaBuilder = CreateGC.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int cid = in.readCard32();
    int drawable = in.readCard32();
    int valueMask = in.readCard32();
    int function = 0;
    int planeMask = 0;
    int foreground = 0;
    int background = 0;
    int lineWidth = 0;
    int lineStyle = 0;
    int capStyle = 0;
    int joinStyle = 0;
    int fillStyle = 0;
    int fillRule = 0;
    int tile = 0;
    int stipple = 0;
    int tileStippleXOrigin = 0;
    int tileStippleYOrigin = 0;
    int font = 0;
    int subwindowMode = 0;
    int graphicsExposures = 0;
    int clipXOrigin = 0;
    int clipYOrigin = 0;
    int clipMask = 0;
    int dashOffset = 0;
    int dashes = 0;
    int arcMode = 0;
    javaBuilder.cid(cid);
    javaBuilder.drawable(drawable);
    javaBuilder.valueMask(valueMask);
    if(Gc.FUNCTION.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      function = in.readCard32();
      javaBuilder.function(function);
    }
    if(Gc.PLANE_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      planeMask = in.readCard32();
      javaBuilder.planeMask(planeMask);
    }
    if(Gc.FOREGROUND.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      foreground = in.readCard32();
      javaBuilder.foreground(foreground);
    }
    if(Gc.BACKGROUND.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      background = in.readCard32();
      javaBuilder.background(background);
    }
    if(Gc.LINE_WIDTH.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      lineWidth = in.readCard32();
      javaBuilder.lineWidth(lineWidth);
    }
    if(Gc.LINE_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      lineStyle = in.readCard32();
      javaBuilder.lineStyle(lineStyle);
    }
    if(Gc.CAP_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      capStyle = in.readCard32();
      javaBuilder.capStyle(capStyle);
    }
    if(Gc.JOIN_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      joinStyle = in.readCard32();
      javaBuilder.joinStyle(joinStyle);
    }
    if(Gc.FILL_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      fillStyle = in.readCard32();
      javaBuilder.fillStyle(fillStyle);
    }
    if(Gc.FILL_RULE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      fillRule = in.readCard32();
      javaBuilder.fillRule(fillRule);
    }
    if(Gc.TILE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      tile = in.readCard32();
      javaBuilder.tile(tile);
    }
    if(Gc.STIPPLE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      stipple = in.readCard32();
      javaBuilder.stipple(stipple);
    }
    if(Gc.TILE_STIPPLE_ORIGIN_X.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      tileStippleXOrigin = in.readInt32();
      javaBuilder.tileStippleXOrigin(tileStippleXOrigin);
    }
    if(Gc.TILE_STIPPLE_ORIGIN_Y.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      tileStippleYOrigin = in.readInt32();
      javaBuilder.tileStippleYOrigin(tileStippleYOrigin);
    }
    if(Gc.FONT.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      font = in.readCard32();
      javaBuilder.font(font);
    }
    if(Gc.SUBWINDOW_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      subwindowMode = in.readCard32();
      javaBuilder.subwindowMode(subwindowMode);
    }
    if(Gc.GRAPHICS_EXPOSURES.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      graphicsExposures = in.readCard32();
      javaBuilder.graphicsExposures(graphicsExposures);
    }
    if(Gc.CLIP_ORIGIN_X.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      clipXOrigin = in.readInt32();
      javaBuilder.clipXOrigin(clipXOrigin);
    }
    if(Gc.CLIP_ORIGIN_Y.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      clipYOrigin = in.readInt32();
      javaBuilder.clipYOrigin(clipYOrigin);
    }
    if(Gc.CLIP_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      clipMask = in.readCard32();
      javaBuilder.clipMask(clipMask);
    }
    if(Gc.DASH_OFFSET.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      dashOffset = in.readCard32();
      javaBuilder.dashOffset(dashOffset);
    }
    if(Gc.DASH_LIST.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      dashes = in.readCard32();
      javaBuilder.dashes(dashes);
    }
    if(Gc.ARC_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      arcMode = in.readCard32();
      javaBuilder.arcMode(arcMode);
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cid);
    out.writeCard32(drawable);
    out.writeCard32(valueMask);
    if(Gc.FUNCTION.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(function);
    }
    if(Gc.PLANE_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(planeMask);
    }
    if(Gc.FOREGROUND.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(foreground);
    }
    if(Gc.BACKGROUND.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(background);
    }
    if(Gc.LINE_WIDTH.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(lineWidth);
    }
    if(Gc.LINE_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(lineStyle);
    }
    if(Gc.CAP_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(capStyle);
    }
    if(Gc.JOIN_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(joinStyle);
    }
    if(Gc.FILL_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(fillStyle);
    }
    if(Gc.FILL_RULE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(fillRule);
    }
    if(Gc.TILE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(tile);
    }
    if(Gc.STIPPLE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(stipple);
    }
    if(Gc.TILE_STIPPLE_ORIGIN_X.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(tileStippleXOrigin);
    }
    if(Gc.TILE_STIPPLE_ORIGIN_Y.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(tileStippleYOrigin);
    }
    if(Gc.FONT.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(font);
    }
    if(Gc.SUBWINDOW_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(subwindowMode);
    }
    if(Gc.GRAPHICS_EXPOSURES.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(graphicsExposures);
    }
    if(Gc.CLIP_ORIGIN_X.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(clipXOrigin);
    }
    if(Gc.CLIP_ORIGIN_Y.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(clipYOrigin);
    }
    if(Gc.CLIP_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(clipMask);
    }
    if(Gc.DASH_OFFSET.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(dashOffset);
    }
    if(Gc.DASH_LIST.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(dashes);
    }
    if(Gc.ARC_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(arcMode);
    }
    out.writePadAlign(getSize());
  }

  public boolean isValueMaskEnabled(@NonNull Gc... maskEnums) {
    for(Gc m : maskEnums) {
      if(!m.isEnabled(valueMask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 16 + (Gc.FUNCTION.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.PLANE_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.FOREGROUND.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.BACKGROUND.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.LINE_WIDTH.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.LINE_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.CAP_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.JOIN_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.FILL_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.FILL_RULE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.TILE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.STIPPLE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.TILE_STIPPLE_ORIGIN_X.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.TILE_STIPPLE_ORIGIN_Y.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.FONT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.SUBWINDOW_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.GRAPHICS_EXPOSURES.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.CLIP_ORIGIN_X.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.CLIP_ORIGIN_Y.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.CLIP_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.DASH_OFFSET.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.DASH_LIST.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.ARC_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
  }

  public static class CreateGCBuilder {
    private CreateGC.CreateGCBuilder valueMask(int valueMask) {
      this.valueMask = valueMask;
      return this;
    }

    public boolean isValueMaskEnabled(@NonNull Gc... maskEnums) {
      for(Gc m : maskEnums) {
        if(!m.isEnabled(valueMask)) {
          return false;
        }
      }
      return true;
    }

    private CreateGC.CreateGCBuilder valueMaskEnable(Gc... maskEnums) {
      for(Gc m : maskEnums) {
        valueMask((int) m.enableFor(valueMask));
      }
      return this;
    }

    private CreateGC.CreateGCBuilder valueMaskDisable(Gc... maskEnums) {
      for(Gc m : maskEnums) {
        valueMask((int) m.disableFor(valueMask));
      }
      return this;
    }

    public CreateGC.CreateGCBuilder function(int function) {
      this.function = function;
      valueMaskEnable(Gc.FUNCTION);
      return this;
    }

    public CreateGC.CreateGCBuilder function(Gx function) {
      this.function = (int) function.getValue();
      valueMaskEnable(Gc.FUNCTION);
      return this;
    }

    public CreateGC.CreateGCBuilder planeMask(int planeMask) {
      this.planeMask = planeMask;
      valueMaskEnable(Gc.PLANE_MASK);
      return this;
    }

    public CreateGC.CreateGCBuilder foreground(int foreground) {
      this.foreground = foreground;
      valueMaskEnable(Gc.FOREGROUND);
      return this;
    }

    public CreateGC.CreateGCBuilder background(int background) {
      this.background = background;
      valueMaskEnable(Gc.BACKGROUND);
      return this;
    }

    public CreateGC.CreateGCBuilder lineWidth(int lineWidth) {
      this.lineWidth = lineWidth;
      valueMaskEnable(Gc.LINE_WIDTH);
      return this;
    }

    public CreateGC.CreateGCBuilder lineStyle(int lineStyle) {
      this.lineStyle = lineStyle;
      valueMaskEnable(Gc.LINE_STYLE);
      return this;
    }

    public CreateGC.CreateGCBuilder lineStyle(LineStyle lineStyle) {
      this.lineStyle = (int) lineStyle.getValue();
      valueMaskEnable(Gc.LINE_STYLE);
      return this;
    }

    public CreateGC.CreateGCBuilder capStyle(int capStyle) {
      this.capStyle = capStyle;
      valueMaskEnable(Gc.CAP_STYLE);
      return this;
    }

    public CreateGC.CreateGCBuilder capStyle(CapStyle capStyle) {
      this.capStyle = (int) capStyle.getValue();
      valueMaskEnable(Gc.CAP_STYLE);
      return this;
    }

    public CreateGC.CreateGCBuilder joinStyle(int joinStyle) {
      this.joinStyle = joinStyle;
      valueMaskEnable(Gc.JOIN_STYLE);
      return this;
    }

    public CreateGC.CreateGCBuilder joinStyle(JoinStyle joinStyle) {
      this.joinStyle = (int) joinStyle.getValue();
      valueMaskEnable(Gc.JOIN_STYLE);
      return this;
    }

    public CreateGC.CreateGCBuilder fillStyle(int fillStyle) {
      this.fillStyle = fillStyle;
      valueMaskEnable(Gc.FILL_STYLE);
      return this;
    }

    public CreateGC.CreateGCBuilder fillStyle(FillStyle fillStyle) {
      this.fillStyle = (int) fillStyle.getValue();
      valueMaskEnable(Gc.FILL_STYLE);
      return this;
    }

    public CreateGC.CreateGCBuilder fillRule(int fillRule) {
      this.fillRule = fillRule;
      valueMaskEnable(Gc.FILL_RULE);
      return this;
    }

    public CreateGC.CreateGCBuilder fillRule(FillRule fillRule) {
      this.fillRule = (int) fillRule.getValue();
      valueMaskEnable(Gc.FILL_RULE);
      return this;
    }

    public CreateGC.CreateGCBuilder tile(int tile) {
      this.tile = tile;
      valueMaskEnable(Gc.TILE);
      return this;
    }

    public CreateGC.CreateGCBuilder stipple(int stipple) {
      this.stipple = stipple;
      valueMaskEnable(Gc.STIPPLE);
      return this;
    }

    public CreateGC.CreateGCBuilder tileStippleXOrigin(int tileStippleXOrigin) {
      this.tileStippleXOrigin = tileStippleXOrigin;
      valueMaskEnable(Gc.TILE_STIPPLE_ORIGIN_X);
      return this;
    }

    public CreateGC.CreateGCBuilder tileStippleYOrigin(int tileStippleYOrigin) {
      this.tileStippleYOrigin = tileStippleYOrigin;
      valueMaskEnable(Gc.TILE_STIPPLE_ORIGIN_Y);
      return this;
    }

    public CreateGC.CreateGCBuilder font(int font) {
      this.font = font;
      valueMaskEnable(Gc.FONT);
      return this;
    }

    public CreateGC.CreateGCBuilder subwindowMode(int subwindowMode) {
      this.subwindowMode = subwindowMode;
      valueMaskEnable(Gc.SUBWINDOW_MODE);
      return this;
    }

    public CreateGC.CreateGCBuilder subwindowMode(SubwindowMode subwindowMode) {
      this.subwindowMode = (int) subwindowMode.getValue();
      valueMaskEnable(Gc.SUBWINDOW_MODE);
      return this;
    }

    public CreateGC.CreateGCBuilder graphicsExposures(int graphicsExposures) {
      this.graphicsExposures = graphicsExposures;
      valueMaskEnable(Gc.GRAPHICS_EXPOSURES);
      return this;
    }

    public CreateGC.CreateGCBuilder clipXOrigin(int clipXOrigin) {
      this.clipXOrigin = clipXOrigin;
      valueMaskEnable(Gc.CLIP_ORIGIN_X);
      return this;
    }

    public CreateGC.CreateGCBuilder clipYOrigin(int clipYOrigin) {
      this.clipYOrigin = clipYOrigin;
      valueMaskEnable(Gc.CLIP_ORIGIN_Y);
      return this;
    }

    public CreateGC.CreateGCBuilder clipMask(int clipMask) {
      this.clipMask = clipMask;
      valueMaskEnable(Gc.CLIP_MASK);
      return this;
    }

    public CreateGC.CreateGCBuilder dashOffset(int dashOffset) {
      this.dashOffset = dashOffset;
      valueMaskEnable(Gc.DASH_OFFSET);
      return this;
    }

    public CreateGC.CreateGCBuilder dashes(int dashes) {
      this.dashes = dashes;
      valueMaskEnable(Gc.DASH_LIST);
      return this;
    }

    public CreateGC.CreateGCBuilder arcMode(int arcMode) {
      this.arcMode = arcMode;
      valueMaskEnable(Gc.ARC_MODE);
      return this;
    }

    public CreateGC.CreateGCBuilder arcMode(ArcMode arcMode) {
      this.arcMode = (int) arcMode.getValue();
      valueMaskEnable(Gc.ARC_MODE);
      return this;
    }

    public int getSize() {
      return 16 + (Gc.FUNCTION.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.PLANE_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.FOREGROUND.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.BACKGROUND.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.LINE_WIDTH.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.LINE_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.CAP_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.JOIN_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.FILL_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.FILL_RULE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.TILE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.STIPPLE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.TILE_STIPPLE_ORIGIN_X.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.TILE_STIPPLE_ORIGIN_Y.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.FONT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.SUBWINDOW_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.GRAPHICS_EXPOSURES.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.CLIP_ORIGIN_X.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.CLIP_ORIGIN_Y.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.CLIP_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.DASH_OFFSET.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.DASH_LIST.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.ARC_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
    }
  }
}
