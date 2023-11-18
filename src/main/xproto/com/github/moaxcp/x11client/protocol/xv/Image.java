package com.github.moaxcp.x11client.protocol.xv;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Image implements XStruct, XvObject {
  private int id;

  private short width;

  private short height;

  @NonNull
  private List<Integer> pitches;

  @NonNull
  private List<Integer> offsets;

  @NonNull
  private List<Byte> data;

  public static Image readImage(X11Input in) throws IOException {
    Image.ImageBuilder javaBuilder = Image.builder();
    int id = in.readCard32();
    short width = in.readCard16();
    short height = in.readCard16();
    int dataSize = in.readCard32();
    int numPlanes = in.readCard32();
    List<Integer> pitches = in.readCard32((int) (Integer.toUnsignedLong(numPlanes)));
    List<Integer> offsets = in.readCard32((int) (Integer.toUnsignedLong(numPlanes)));
    List<Byte> data = in.readCard8((int) (Integer.toUnsignedLong(dataSize)));
    javaBuilder.id(id);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.pitches(pitches);
    javaBuilder.offsets(offsets);
    javaBuilder.data(data);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(id);
    out.writeCard16(width);
    out.writeCard16(height);
    int dataSize = data.size();
    out.writeCard32(dataSize);
    int numPlanes = offsets.size();
    out.writeCard32(numPlanes);
    out.writeCard32(pitches);
    out.writeCard32(offsets);
    out.writeCard8(data);
  }

  @Override
  public int getSize() {
    return 16 + 4 * pitches.size() + 4 * offsets.size() + 1 * data.size();
  }

  public static class ImageBuilder {
    public int getSize() {
      return 16 + 4 * pitches.size() + 4 * offsets.size() + 1 * data.size();
    }
  }
}
