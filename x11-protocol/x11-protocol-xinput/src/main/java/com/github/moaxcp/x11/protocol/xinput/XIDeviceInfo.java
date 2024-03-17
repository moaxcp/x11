package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class XIDeviceInfo implements XStruct {
  public static final String PLUGIN_NAME = "xinput";

  private short deviceid;

  private short type;

  private short attachment;

  private boolean enabled;

  @NonNull
  private List<Byte> name;

  @NonNull
  private List<DeviceClass> classes;

  public static XIDeviceInfo readXIDeviceInfo(X11Input in) throws IOException {
    XIDeviceInfo.XIDeviceInfoBuilder javaBuilder = XIDeviceInfo.builder();
    short deviceid = in.readCard16();
    short type = in.readCard16();
    short attachment = in.readCard16();
    short numClasses = in.readCard16();
    short nameLen = in.readCard16();
    boolean enabled = in.readBool();
    byte[] pad6 = in.readPad(1);
    List<Byte> name = in.readChar(Short.toUnsignedInt(nameLen));
    in.readPadAlign(Short.toUnsignedInt(nameLen));
    List<DeviceClass> classes = new ArrayList<>(Short.toUnsignedInt(numClasses));
    for(int i = 0; i < Short.toUnsignedInt(numClasses); i++) {
      classes.add(DeviceClass.readDeviceClass(in));
    }
    javaBuilder.deviceid(deviceid);
    javaBuilder.type(type);
    javaBuilder.attachment(attachment);
    javaBuilder.enabled(enabled);
    javaBuilder.name(name);
    javaBuilder.classes(classes);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(deviceid);
    out.writeCard16(type);
    out.writeCard16(attachment);
    short numClasses = (short) classes.size();
    out.writeCard16(numClasses);
    short nameLen = (short) name.size();
    out.writeCard16(nameLen);
    out.writeBool(enabled);
    out.writePad(1);
    out.writeChar(name);
    out.writePadAlign(Short.toUnsignedInt(nameLen));
    for(DeviceClass t : classes) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 12 + 1 * name.size() + XObject.getSizeForPadAlign(4, 1 * name.size()) + XObject.sizeOf(classes);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class XIDeviceInfoBuilder {
    public XIDeviceInfo.XIDeviceInfoBuilder type(DeviceType type) {
      this.type = (short) type.getValue();
      return this;
    }

    public XIDeviceInfo.XIDeviceInfoBuilder type(short type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 12 + 1 * name.size() + XObject.getSizeForPadAlign(4, 1 * name.size()) + XObject.sizeOf(classes);
    }
  }
}
