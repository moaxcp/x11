package com.github.moaxcp.x11.protocol.xvmc;

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
public class CreateSubpictureReply implements XReply {
  public static final String PLUGIN_NAME = "xvmc";

  private short sequenceNumber;

  private short widthActual;

  private short heightActual;

  private short numPaletteEntries;

  private short entryBytes;

  @NonNull
  private ImmutableByteList componentOrder;

  @NonNull
  private ImmutableIntList privData;

  public static CreateSubpictureReply readCreateSubpictureReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    CreateSubpictureReply.CreateSubpictureReplyBuilder javaBuilder = CreateSubpictureReply.builder();
    int length = in.readCard32();
    short widthActual = in.readCard16();
    short heightActual = in.readCard16();
    short numPaletteEntries = in.readCard16();
    short entryBytes = in.readCard16();
    ImmutableByteList componentOrder = in.readCard8(4);
    byte[] pad9 = in.readPad(12);
    ImmutableIntList privData = in.readCard32((int) (Integer.toUnsignedLong(length)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.widthActual(widthActual);
    javaBuilder.heightActual(heightActual);
    javaBuilder.numPaletteEntries(numPaletteEntries);
    javaBuilder.entryBytes(entryBytes);
    javaBuilder.componentOrder(componentOrder);
    javaBuilder.privData(privData);
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
    int length = privData.size();
    out.writeCard32(getLength());
    out.writeCard16(widthActual);
    out.writeCard16(heightActual);
    out.writeCard16(numPaletteEntries);
    out.writeCard16(entryBytes);
    out.writeCard8(componentOrder);
    out.writePad(12);
    out.writeCard32(privData);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 28 + 1 * componentOrder.size() + 4 * privData.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateSubpictureReplyBuilder {
    public int getSize() {
      return 28 + 1 * componentOrder.size() + 4 * privData.size();
    }
  }
}
