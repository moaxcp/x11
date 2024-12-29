package com.github.moaxcp.x11.protocol.xv;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class Image implements XStruct {
  public static final String PLUGIN_NAME = "xv";

  private int id;

  private short width;

  private short height;

  @NonNull
  private IntList pitches;

  @NonNull
  private IntList offsets;

  @NonNull
  private ByteList data;

  public static Image readImage(X11Input in) throws IOException {
    Image.ImageBuilder javaBuilder = Image.builder();
    int id = in.readCard32();
    short width = in.readCard16();
    short height = in.readCard16();
    int dataSize = in.readCard32();
    int numPlanes = in.readCard32();
    IntList pitches = in.readCard32((int) (Integer.toUnsignedLong(numPlanes)));
    IntList offsets = in.readCard32((int) (Integer.toUnsignedLong(numPlanes)));
    ByteList data = in.readCard8((int) (Integer.toUnsignedLong(dataSize)));
    javaBuilder.id(id);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.pitches(pitches.toImmutable());
    javaBuilder.offsets(offsets.toImmutable());
    javaBuilder.data(data.toImmutable());
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ImageBuilder {
    public int getSize() {
      return 16 + 4 * pitches.size() + 4 * offsets.size() + 1 * data.size();
    }
  }
}
