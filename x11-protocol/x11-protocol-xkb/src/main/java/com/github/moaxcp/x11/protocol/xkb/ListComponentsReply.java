package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Value
@Builder
public class ListComponentsReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";

  private byte deviceID;

  private short sequenceNumber;

  private short extra;

  @NonNull
  private ImmutableList<Listing> keymaps;

  @NonNull
  private ImmutableList<Listing> keycodes;

  @NonNull
  private ImmutableList<Listing> types;

  @NonNull
  private ImmutableList<Listing> compatMaps;

  @NonNull
  private ImmutableList<Listing> symbols;

  @NonNull
  private ImmutableList<Listing> geometries;

  public static ListComponentsReply readListComponentsReply(byte deviceID, short sequenceNumber,
      X11Input in) throws IOException {
    ListComponentsReply.ListComponentsReplyBuilder javaBuilder = ListComponentsReply.builder();
    int length = in.readCard32();
    short nKeymaps = in.readCard16();
    short nKeycodes = in.readCard16();
    short nTypes = in.readCard16();
    short nCompatMaps = in.readCard16();
    short nSymbols = in.readCard16();
    short nGeometries = in.readCard16();
    short extra = in.readCard16();
    byte[] pad11 = in.readPad(10);
    MutableList<Listing> keymaps = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(nKeymaps));
    for(int i = 0; i < Short.toUnsignedInt(nKeymaps); i++) {
      keymaps.add(Listing.readListing(in));
    }
    MutableList<Listing> keycodes = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(nKeycodes));
    for(int i = 0; i < Short.toUnsignedInt(nKeycodes); i++) {
      keycodes.add(Listing.readListing(in));
    }
    MutableList<Listing> types = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(nTypes));
    for(int i = 0; i < Short.toUnsignedInt(nTypes); i++) {
      types.add(Listing.readListing(in));
    }
    MutableList<Listing> compatMaps = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(nCompatMaps));
    for(int i = 0; i < Short.toUnsignedInt(nCompatMaps); i++) {
      compatMaps.add(Listing.readListing(in));
    }
    MutableList<Listing> symbols = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(nSymbols));
    for(int i = 0; i < Short.toUnsignedInt(nSymbols); i++) {
      symbols.add(Listing.readListing(in));
    }
    MutableList<Listing> geometries = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(nGeometries));
    for(int i = 0; i < Short.toUnsignedInt(nGeometries); i++) {
      geometries.add(Listing.readListing(in));
    }
    javaBuilder.deviceID(deviceID);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.extra(extra);
    javaBuilder.keymaps(keymaps.toImmutable());
    javaBuilder.keycodes(keycodes.toImmutable());
    javaBuilder.types(types.toImmutable());
    javaBuilder.compatMaps(compatMaps.toImmutable());
    javaBuilder.symbols(symbols.toImmutable());
    javaBuilder.geometries(geometries.toImmutable());
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(deviceID);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    short nKeymaps = (short) keymaps.size();
    out.writeCard16(nKeymaps);
    short nKeycodes = (short) keycodes.size();
    out.writeCard16(nKeycodes);
    short nTypes = (short) types.size();
    out.writeCard16(nTypes);
    short nCompatMaps = (short) compatMaps.size();
    out.writeCard16(nCompatMaps);
    short nSymbols = (short) symbols.size();
    out.writeCard16(nSymbols);
    short nGeometries = (short) geometries.size();
    out.writeCard16(nGeometries);
    out.writeCard16(extra);
    out.writePad(10);
    for(Listing t : keymaps) {
      t.write(out);
    }
    for(Listing t : keycodes) {
      t.write(out);
    }
    for(Listing t : types) {
      t.write(out);
    }
    for(Listing t : compatMaps) {
      t.write(out);
    }
    for(Listing t : symbols) {
      t.write(out);
    }
    for(Listing t : geometries) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(keymaps) + XObject.sizeOf(keycodes) + XObject.sizeOf(types) + XObject.sizeOf(compatMaps) + XObject.sizeOf(symbols) + XObject.sizeOf(geometries);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ListComponentsReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(keymaps) + XObject.sizeOf(keycodes) + XObject.sizeOf(types) + XObject.sizeOf(compatMaps) + XObject.sizeOf(symbols) + XObject.sizeOf(geometries);
    }
  }
}
