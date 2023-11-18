package com.github.moaxcp.x11client.protocol.record;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ExtRange implements XStruct, RecordObject {
  @NonNull
  private Range8 major;

  @NonNull
  private Range16 minor;

  public static ExtRange readExtRange(X11Input in) throws IOException {
    ExtRange.ExtRangeBuilder javaBuilder = ExtRange.builder();
    Range8 major = Range8.readRange8(in);
    Range16 minor = Range16.readRange16(in);
    javaBuilder.major(major);
    javaBuilder.minor(minor);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    major.write(out);
    minor.write(out);
  }

  @Override
  public int getSize() {
    return 6;
  }

  public static class ExtRangeBuilder {
    public int getSize() {
      return 6;
    }
  }
}
