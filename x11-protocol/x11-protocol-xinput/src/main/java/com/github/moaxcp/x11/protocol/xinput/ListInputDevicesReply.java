package com.github.moaxcp.x11.protocol.xinput;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import com.github.moaxcp.x11.protocol.xproto.Str;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Value
@Builder
public class ListInputDevicesReply implements XReply {
  public static final String PLUGIN_NAME = "xinput";

  private byte xiReplyType;

  private short sequenceNumber;

  @NonNull
  private ImmutableList<DeviceInfo> devices;

  @NonNull
  private ImmutableList<InputInfo> infos;

  @NonNull
  private ImmutableList<Str> names;

  public static ListInputDevicesReply readListInputDevicesReply(byte xiReplyType,
      short sequenceNumber, X11Input in) throws IOException {
    ListInputDevicesReply.ListInputDevicesReplyBuilder javaBuilder = ListInputDevicesReply.builder();
    int length = in.readCard32();
    byte devicesLen = in.readCard8();
    byte[] pad5 = in.readPad(23);
    MutableList<DeviceInfo> devices = Lists.mutable.withInitialCapacity(Byte.toUnsignedInt(devicesLen));
    for(int i = 0; i < Byte.toUnsignedInt(devicesLen); i++) {
      devices.add(DeviceInfo.readDeviceInfo(in));
    }
    MutableList<InputInfo> infos = Lists.mutable.withInitialCapacity(devices.stream().mapToInt(o -> Byte.toUnsignedInt(o.getNumClassInfo())).sum());
    for(int i = 0; i < devices.stream().mapToInt(o -> Byte.toUnsignedInt(o.getNumClassInfo())).sum(); i++) {
      infos.add(InputInfo.readInputInfo(in));
    }
    MutableList<Str> names = Lists.mutable.withInitialCapacity(Byte.toUnsignedInt(devicesLen));
    for(int i = 0; i < Byte.toUnsignedInt(devicesLen); i++) {
      names.add(Str.readStr(in));
    }
    in.readPadAlign(Byte.toUnsignedInt(devicesLen));
    javaBuilder.xiReplyType(xiReplyType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.devices(devices.toImmutable());
    javaBuilder.infos(infos.toImmutable());
    javaBuilder.names(names.toImmutable());
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(xiReplyType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    byte devicesLen = (byte) names.size();
    out.writeCard8(devicesLen);
    out.writePad(23);
    for(DeviceInfo t : devices) {
      t.write(out);
    }
    for(InputInfo t : infos) {
      t.write(out);
    }
    for(Str t : names) {
      t.write(out);
    }
    out.writePadAlign(Byte.toUnsignedInt(devicesLen));
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(devices) + XObject.sizeOf(infos) + XObject.sizeOf(names) + XObject.getSizeForPadAlign(4, XObject.sizeOf(names));
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ListInputDevicesReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(devices) + XObject.sizeOf(infos) + XObject.sizeOf(names) + XObject.getSizeForPadAlign(4, XObject.sizeOf(names));
    }
  }
}
