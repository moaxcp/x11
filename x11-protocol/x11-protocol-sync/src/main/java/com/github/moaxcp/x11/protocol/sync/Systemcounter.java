package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class Systemcounter implements XStruct {
  public static final String PLUGIN_NAME = "sync";

  private int counter;

  @NonNull
  private Int64 resolution;

  @NonNull
  private ByteList name;

  public static Systemcounter readSystemcounter(X11Input in) throws IOException {
    Systemcounter.SystemcounterBuilder javaBuilder = Systemcounter.builder();
    int counter = in.readCard32();
    Int64 resolution = Int64.readInt64(in);
    short nameLen = in.readCard16();
    ByteList name = in.readChar(Short.toUnsignedInt(nameLen));
    in.readPadAlign(Short.toUnsignedInt(nameLen));
    javaBuilder.counter(counter);
    javaBuilder.resolution(resolution);
    javaBuilder.name(name.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(counter);
    resolution.write(out);
    short nameLen = (short) name.size();
    out.writeCard16(nameLen);
    out.writeChar(name);
    out.writePadAlign(Short.toUnsignedInt(nameLen));
  }

  @Override
  public int getSize() {
    return 14 + 1 * name.size() + XObject.getSizeForPadAlign(4, 1 * name.size());
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SystemcounterBuilder {
    public int getSize() {
      return 14 + 1 * name.size() + XObject.getSizeForPadAlign(4, 1 * name.size());
    }
  }
}
