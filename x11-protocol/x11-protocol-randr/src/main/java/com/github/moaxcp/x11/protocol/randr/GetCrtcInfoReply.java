package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetCrtcInfoReply implements XReply {
  public static final String PLUGIN_NAME = "randr";

  private byte status;

  private short sequenceNumber;

  private int timestamp;

  private short x;

  private short y;

  private short width;

  private short height;

  private int mode;

  private short rotation;

  private short rotations;

  @NonNull
  private List<Integer> outputs;

  @NonNull
  private List<Integer> possible;

  public static GetCrtcInfoReply readGetCrtcInfoReply(byte status, short sequenceNumber,
      X11Input in) throws IOException {
    GetCrtcInfoReply.GetCrtcInfoReplyBuilder javaBuilder = GetCrtcInfoReply.builder();
    int length = in.readCard32();
    int timestamp = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    int mode = in.readCard32();
    short rotation = in.readCard16();
    short rotations = in.readCard16();
    short numOutputs = in.readCard16();
    short numPossibleOutputs = in.readCard16();
    List<Integer> outputs = in.readCard32(Short.toUnsignedInt(numOutputs));
    List<Integer> possible = in.readCard32(Short.toUnsignedInt(numPossibleOutputs));
    javaBuilder.status(status);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timestamp(timestamp);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.mode(mode);
    javaBuilder.rotation(rotation);
    javaBuilder.rotations(rotations);
    javaBuilder.outputs(outputs);
    javaBuilder.possible(possible);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(status);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(timestamp);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard32(mode);
    out.writeCard16(rotation);
    out.writeCard16(rotations);
    short numOutputs = (short) outputs.size();
    out.writeCard16(numOutputs);
    short numPossibleOutputs = (short) possible.size();
    out.writeCard16(numPossibleOutputs);
    out.writeCard32(outputs);
    out.writeCard32(possible);
    out.writePadAlign(getSize());
  }

  public boolean isRotationEnabled(@NonNull Rotation... maskEnums) {
    for(Rotation m : maskEnums) {
      if(!m.isEnabled(rotation)) {
        return false;
      }
    }
    return true;
  }

  public boolean isRotationsEnabled(@NonNull Rotation... maskEnums) {
    for(Rotation m : maskEnums) {
      if(!m.isEnabled(rotations)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32 + 4 * outputs.size() + 4 * possible.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetCrtcInfoReplyBuilder {
    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder status(SetConfig status) {
      this.status = (byte) status.getValue();
      return this;
    }

    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder status(byte status) {
      this.status = status;
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

    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder rotationEnable(Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        rotation((short) m.enableFor(rotation));
      }
      return this;
    }

    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder rotationDisable(Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        rotation((short) m.disableFor(rotation));
      }
      return this;
    }

    public boolean isRotationsEnabled(@NonNull Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        if(!m.isEnabled(rotations)) {
          return false;
        }
      }
      return true;
    }

    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder rotationsEnable(Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        rotations((short) m.enableFor(rotations));
      }
      return this;
    }

    public GetCrtcInfoReply.GetCrtcInfoReplyBuilder rotationsDisable(Rotation... maskEnums) {
      for(Rotation m : maskEnums) {
        rotations((short) m.disableFor(rotations));
      }
      return this;
    }

    public int getSize() {
      return 32 + 4 * outputs.size() + 4 * possible.size();
    }
  }
}
