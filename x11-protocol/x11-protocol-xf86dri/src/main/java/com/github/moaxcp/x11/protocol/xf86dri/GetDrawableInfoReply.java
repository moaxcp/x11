package com.github.moaxcp.x11.protocol.xf86dri;

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
public class GetDrawableInfoReply implements XReply {
  public static final String PLUGIN_NAME = "xf86dri";

  private short sequenceNumber;

  private int drawableTableIndex;

  private int drawableTableStamp;

  private short drawableOriginX;

  private short drawableOriginY;

  private short drawableSizeW;

  private short drawableSizeH;

  private short backX;

  private short backY;

  @NonNull
  private ImmutableList<DrmClipRect> clipRects;

  @NonNull
  private ImmutableList<DrmClipRect> backClipRects;

  public static GetDrawableInfoReply readGetDrawableInfoReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetDrawableInfoReply.GetDrawableInfoReplyBuilder javaBuilder = GetDrawableInfoReply.builder();
    int length = in.readCard32();
    int drawableTableIndex = in.readCard32();
    int drawableTableStamp = in.readCard32();
    short drawableOriginX = in.readInt16();
    short drawableOriginY = in.readInt16();
    short drawableSizeW = in.readInt16();
    short drawableSizeH = in.readInt16();
    int numClipRects = in.readCard32();
    short backX = in.readInt16();
    short backY = in.readInt16();
    int numBackClipRects = in.readCard32();
    MutableList<DrmClipRect> clipRects = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numClipRects)));
    for(int i = 0; i < Integer.toUnsignedLong(numClipRects); i++) {
      clipRects.add(DrmClipRect.readDrmClipRect(in));
    }
    MutableList<DrmClipRect> backClipRects = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numBackClipRects)));
    for(int i = 0; i < Integer.toUnsignedLong(numBackClipRects); i++) {
      backClipRects.add(DrmClipRect.readDrmClipRect(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.drawableTableIndex(drawableTableIndex);
    javaBuilder.drawableTableStamp(drawableTableStamp);
    javaBuilder.drawableOriginX(drawableOriginX);
    javaBuilder.drawableOriginY(drawableOriginY);
    javaBuilder.drawableSizeW(drawableSizeW);
    javaBuilder.drawableSizeH(drawableSizeH);
    javaBuilder.backX(backX);
    javaBuilder.backY(backY);
    javaBuilder.clipRects(clipRects.toImmutable());
    javaBuilder.backClipRects(backClipRects.toImmutable());
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
    out.writeCard32(drawableTableIndex);
    out.writeCard32(drawableTableStamp);
    out.writeInt16(drawableOriginX);
    out.writeInt16(drawableOriginY);
    out.writeInt16(drawableSizeW);
    out.writeInt16(drawableSizeH);
    int numClipRects = clipRects.size();
    out.writeCard32(numClipRects);
    out.writeInt16(backX);
    out.writeInt16(backY);
    int numBackClipRects = backClipRects.size();
    out.writeCard32(numBackClipRects);
    for(DrmClipRect t : clipRects) {
      t.write(out);
    }
    for(DrmClipRect t : backClipRects) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 36 + XObject.sizeOf(clipRects) + XObject.sizeOf(backClipRects);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetDrawableInfoReplyBuilder {
    public int getSize() {
      return 36 + XObject.sizeOf(clipRects) + XObject.sizeOf(backClipRects);
    }
  }
}
