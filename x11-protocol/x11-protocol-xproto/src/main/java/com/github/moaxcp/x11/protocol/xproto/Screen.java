package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Value
@Builder
public class Screen implements XStruct {
  public static final String PLUGIN_NAME = "xproto";

  private int root;

  private int defaultColormap;

  private int whitePixel;

  private int blackPixel;

  private int currentInputMasks;

  private short widthInPixels;

  private short heightInPixels;

  private short widthInMillimeters;

  private short heightInMillimeters;

  private short minInstalledMaps;

  private short maxInstalledMaps;

  private int rootVisual;

  private byte backingStores;

  private boolean saveUnders;

  private byte rootDepth;

  @NonNull
  private ImmutableList<Depth> allowedDepths;

  public static Screen readScreen(X11Input in) throws IOException {
    Screen.ScreenBuilder javaBuilder = Screen.builder();
    int root = in.readCard32();
    int defaultColormap = in.readCard32();
    int whitePixel = in.readCard32();
    int blackPixel = in.readCard32();
    int currentInputMasks = in.readCard32();
    short widthInPixels = in.readCard16();
    short heightInPixels = in.readCard16();
    short widthInMillimeters = in.readCard16();
    short heightInMillimeters = in.readCard16();
    short minInstalledMaps = in.readCard16();
    short maxInstalledMaps = in.readCard16();
    int rootVisual = in.readCard32();
    byte backingStores = in.readByte();
    boolean saveUnders = in.readBool();
    byte rootDepth = in.readCard8();
    byte allowedDepthsLen = in.readCard8();
    MutableList<Depth> allowedDepths = Lists.mutable.withInitialCapacity(Byte.toUnsignedInt(allowedDepthsLen));
    for(int i = 0; i < Byte.toUnsignedInt(allowedDepthsLen); i++) {
      allowedDepths.add(Depth.readDepth(in));
    }
    javaBuilder.root(root);
    javaBuilder.defaultColormap(defaultColormap);
    javaBuilder.whitePixel(whitePixel);
    javaBuilder.blackPixel(blackPixel);
    javaBuilder.currentInputMasks(currentInputMasks);
    javaBuilder.widthInPixels(widthInPixels);
    javaBuilder.heightInPixels(heightInPixels);
    javaBuilder.widthInMillimeters(widthInMillimeters);
    javaBuilder.heightInMillimeters(heightInMillimeters);
    javaBuilder.minInstalledMaps(minInstalledMaps);
    javaBuilder.maxInstalledMaps(maxInstalledMaps);
    javaBuilder.rootVisual(rootVisual);
    javaBuilder.backingStores(backingStores);
    javaBuilder.saveUnders(saveUnders);
    javaBuilder.rootDepth(rootDepth);
    javaBuilder.allowedDepths(allowedDepths.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(root);
    out.writeCard32(defaultColormap);
    out.writeCard32(whitePixel);
    out.writeCard32(blackPixel);
    out.writeCard32(currentInputMasks);
    out.writeCard16(widthInPixels);
    out.writeCard16(heightInPixels);
    out.writeCard16(widthInMillimeters);
    out.writeCard16(heightInMillimeters);
    out.writeCard16(minInstalledMaps);
    out.writeCard16(maxInstalledMaps);
    out.writeCard32(rootVisual);
    out.writeByte(backingStores);
    out.writeBool(saveUnders);
    out.writeCard8(rootDepth);
    byte allowedDepthsLen = (byte) allowedDepths.size();
    out.writeCard8(allowedDepthsLen);
    for(Depth t : allowedDepths) {
      t.write(out);
    }
  }

  public boolean isCurrentInputMasksEnabled(@NonNull EventMask... maskEnums) {
    for(EventMask m : maskEnums) {
      if(!m.isEnabled(currentInputMasks)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 40 + XObject.sizeOf(allowedDepths);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ScreenBuilder {
    public boolean isCurrentInputMasksEnabled(@NonNull EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        if(!m.isEnabled(currentInputMasks)) {
          return false;
        }
      }
      return true;
    }

    public Screen.ScreenBuilder currentInputMasksEnable(EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        currentInputMasks((int) m.enableFor(currentInputMasks));
      }
      return this;
    }

    public Screen.ScreenBuilder currentInputMasksDisable(EventMask... maskEnums) {
      for(EventMask m : maskEnums) {
        currentInputMasks((int) m.disableFor(currentInputMasks));
      }
      return this;
    }

    public Screen.ScreenBuilder backingStores(BackingStore backingStores) {
      this.backingStores = (byte) backingStores.getValue();
      return this;
    }

    public Screen.ScreenBuilder backingStores(byte backingStores) {
      this.backingStores = backingStores;
      return this;
    }

    public int getSize() {
      return 40 + XObject.sizeOf(allowedDepths);
    }
  }
}
