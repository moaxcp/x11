package com.github.moaxcp.x11client.protocol.xf86dri;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetDrawableInfoReply implements XReply, Xf86driObject {
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
  private List<DrmClipRect> clipRects;

  @NonNull
  private List<DrmClipRect> backClipRects;

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
    List<DrmClipRect> clipRects = new ArrayList<>((int) (Integer.toUnsignedLong(numClipRects)));
    for(int i = 0; i < Integer.toUnsignedLong(numClipRects); i++) {
      clipRects.add(DrmClipRect.readDrmClipRect(in));
    }
    List<DrmClipRect> backClipRects = new ArrayList<>((int) (Integer.toUnsignedLong(numBackClipRects)));
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
    javaBuilder.clipRects(clipRects);
    javaBuilder.backClipRects(backClipRects);
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

  public static class GetDrawableInfoReplyBuilder {
    public int getSize() {
      return 36 + XObject.sizeOf(clipRects) + XObject.sizeOf(backClipRects);
    }
  }
}
