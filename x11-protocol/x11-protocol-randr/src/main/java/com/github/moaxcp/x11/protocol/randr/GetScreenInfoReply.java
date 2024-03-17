package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetScreenInfoReply implements XReply {
  public static final String PLUGIN_NAME = "randr";

  private byte rotations;

  private short sequenceNumber;

  private int root;

  private int timestamp;

  private int configTimestamp;

  private short sizeID;

  private short rotation;

  private short rate;

  private short nInfo;

  @NonNull
  private List<ScreenSize> sizes;

  @NonNull
  private List<RefreshRates> rates;

  public static GetScreenInfoReply readGetScreenInfoReply(byte rotations, short sequenceNumber,
      X11Input in) throws IOException {
    GetScreenInfoReply.GetScreenInfoReplyBuilder javaBuilder = GetScreenInfoReply.builder();
    int length = in.readCard32();
    int root = in.readCard32();
    int timestamp = in.readCard32();
    int configTimestamp = in.readCard32();
    short nSizes = in.readCard16();
    short sizeID = in.readCard16();
    short rotation = in.readCard16();
    short rate = in.readCard16();
    short nInfo = in.readCard16();
    byte[] pad12 = in.readPad(2);
    List<ScreenSize> sizes = new ArrayList<>(Short.toUnsignedInt(nSizes));
    for(int i = 0; i < Short.toUnsignedInt(nSizes); i++) {
      sizes.add(ScreenSize.readScreenSize(in));
    }
    List<RefreshRates> rates = new ArrayList<>(Short.toUnsignedInt(nInfo) - Short.toUnsignedInt(nSizes));
    for(int i = 0; i < Short.toUnsignedInt(nInfo) - Short.toUnsignedInt(nSizes); i++) {
      rates.add(RefreshRates.readRefreshRates(in));
    }
    javaBuilder.rotations(rotations);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.root(root);
    javaBuilder.timestamp(timestamp);
    javaBuilder.configTimestamp(configTimestamp);
    javaBuilder.sizeID(sizeID);
    javaBuilder.rotation(rotation);
    javaBuilder.rate(rate);
    javaBuilder.nInfo(nInfo);
    javaBuilder.sizes(sizes);
    javaBuilder.rates(rates);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(rotations);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(root);
    out.writeCard32(timestamp);
    out.writeCard32(configTimestamp);
    short nSizes = (short) sizes.size();
    out.writeCard16(nSizes);
    out.writeCard16(sizeID);
    out.writeCard16(rotation);
    out.writeCard16(rate);
    out.writeCard16(nInfo);
    out.writePad(2);
    for(ScreenSize t : sizes) {
      t.write(out);
    }
    for(RefreshRates t : rates) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  public boolean isRotationsEnabled(@NonNull Rotation... maskEnums) {
    for(Rotation m : maskEnums) {
      if(!m.isEnabled(rotations)) {
        return false;
      }
    }
    return true;
  }

  public boolean isRotationEnabled(@NonNull Rotation... maskEnums) {
    for(Rotation m : maskEnums) {
      if(!m.isEnabled(rotation)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(sizes) + XObject.sizeOf(rates);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetScreenInfoReplyBuilder {
    public boolean isRotationsEnabled(@NonNull Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        if(!m.isEnabled(rotations)) {
          return false;
        }
      }
      return true;
    }

    public GetScreenInfoReply.GetScreenInfoReplyBuilder rotationsEnable(Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        rotations((byte) m.enableFor(rotations));
      }
      return this;
    }

    public GetScreenInfoReply.GetScreenInfoReplyBuilder rotationsDisable(Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        rotations((byte) m.disableFor(rotations));
      }
      return this;
    }

    public boolean isRotationEnabled(@NonNull Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        if(!m.isEnabled(rotation)) {
          return false;
        }
      }
      return true;
    }

    public GetScreenInfoReply.GetScreenInfoReplyBuilder rotationEnable(Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        rotation((short) m.enableFor(rotation));
      }
      return this;
    }

    public GetScreenInfoReply.GetScreenInfoReplyBuilder rotationDisable(Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        rotation((short) m.disableFor(rotation));
      }
      return this;
    }

    public int getSize() {
      return 32 + XObject.sizeOf(sizes) + XObject.sizeOf(rates);
    }
  }
}
