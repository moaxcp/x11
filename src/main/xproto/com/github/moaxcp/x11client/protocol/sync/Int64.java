package com.github.moaxcp.x11client.protocol.sync;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Int64 implements XStruct, SyncObject {
  private int hi;

  private int lo;

  public static Int64 readInt64(X11Input in) throws IOException {
    Int64.Int64Builder javaBuilder = Int64.builder();
    int hi = in.readInt32();
    int lo = in.readCard32();
    javaBuilder.hi(hi);
    javaBuilder.lo(lo);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt32(hi);
    out.writeCard32(lo);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public static class Int64Builder {
    public int getSize() {
      return 8;
    }
  }
}
