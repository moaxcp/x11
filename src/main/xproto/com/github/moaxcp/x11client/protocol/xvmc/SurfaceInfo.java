package com.github.moaxcp.x11client.protocol.xvmc;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SurfaceInfo implements XStruct, XvmcObject {
  private int id;

  private short chromaFormat;

  private short pad0;

  private short maxWidth;

  private short maxHeight;

  private short subpictureMaxWidth;

  private short subpictureMaxHeight;

  private int mcType;

  private int flags;

  public static SurfaceInfo readSurfaceInfo(X11Input in) throws IOException {
    SurfaceInfo.SurfaceInfoBuilder javaBuilder = SurfaceInfo.builder();
    int id = in.readCard32();
    short chromaFormat = in.readCard16();
    short pad0 = in.readCard16();
    short maxWidth = in.readCard16();
    short maxHeight = in.readCard16();
    short subpictureMaxWidth = in.readCard16();
    short subpictureMaxHeight = in.readCard16();
    int mcType = in.readCard32();
    int flags = in.readCard32();
    javaBuilder.id(id);
    javaBuilder.chromaFormat(chromaFormat);
    javaBuilder.pad0(pad0);
    javaBuilder.maxWidth(maxWidth);
    javaBuilder.maxHeight(maxHeight);
    javaBuilder.subpictureMaxWidth(subpictureMaxWidth);
    javaBuilder.subpictureMaxHeight(subpictureMaxHeight);
    javaBuilder.mcType(mcType);
    javaBuilder.flags(flags);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(id);
    out.writeCard16(chromaFormat);
    out.writeCard16(pad0);
    out.writeCard16(maxWidth);
    out.writeCard16(maxHeight);
    out.writeCard16(subpictureMaxWidth);
    out.writeCard16(subpictureMaxHeight);
    out.writeCard32(mcType);
    out.writeCard32(flags);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public static class SurfaceInfoBuilder {
    public int getSize() {
      return 24;
    }
  }
}
