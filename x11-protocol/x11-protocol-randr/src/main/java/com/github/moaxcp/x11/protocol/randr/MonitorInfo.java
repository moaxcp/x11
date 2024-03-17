package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class MonitorInfo implements XStruct {
  public static final String PLUGIN_NAME = "randr";

  private int name;

  private boolean primary;

  private boolean automatic;

  private short x;

  private short y;

  private short width;

  private short height;

  private int widthInMillimeters;

  private int heightInMillimeters;

  @NonNull
  private List<Integer> outputs;

  public static MonitorInfo readMonitorInfo(X11Input in) throws IOException {
    MonitorInfo.MonitorInfoBuilder javaBuilder = MonitorInfo.builder();
    int name = in.readCard32();
    boolean primary = in.readBool();
    boolean automatic = in.readBool();
    short nOutput = in.readCard16();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    int widthInMillimeters = in.readCard32();
    int heightInMillimeters = in.readCard32();
    List<Integer> outputs = in.readCard32(Short.toUnsignedInt(nOutput));
    javaBuilder.name(name);
    javaBuilder.primary(primary);
    javaBuilder.automatic(automatic);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.widthInMillimeters(widthInMillimeters);
    javaBuilder.heightInMillimeters(heightInMillimeters);
    javaBuilder.outputs(outputs);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(name);
    out.writeBool(primary);
    out.writeBool(automatic);
    short nOutput = (short) outputs.size();
    out.writeCard16(nOutput);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard32(widthInMillimeters);
    out.writeCard32(heightInMillimeters);
    out.writeCard32(outputs);
  }

  @Override
  public int getSize() {
    return 24 + 4 * outputs.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class MonitorInfoBuilder {
    public int getSize() {
      return 24 + 4 * outputs.size();
    }
  }
}
