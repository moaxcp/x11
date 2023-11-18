package com.github.moaxcp.x11client.protocol.xvmc;

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
public class ListSurfaceTypesReply implements XReply, XvmcObject {
  private short sequenceNumber;

  @NonNull
  private List<SurfaceInfo> surfaces;

  public static ListSurfaceTypesReply readListSurfaceTypesReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    ListSurfaceTypesReply.ListSurfaceTypesReplyBuilder javaBuilder = ListSurfaceTypesReply.builder();
    int length = in.readCard32();
    int num = in.readCard32();
    byte[] pad5 = in.readPad(20);
    List<SurfaceInfo> surfaces = new ArrayList<>((int) (Integer.toUnsignedLong(num)));
    for(int i = 0; i < Integer.toUnsignedLong(num); i++) {
      surfaces.add(SurfaceInfo.readSurfaceInfo(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.surfaces(surfaces);
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
    int num = surfaces.size();
    out.writeCard32(num);
    out.writePad(20);
    for(SurfaceInfo t : surfaces) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(surfaces);
  }

  public static class ListSurfaceTypesReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(surfaces);
    }
  }
}
