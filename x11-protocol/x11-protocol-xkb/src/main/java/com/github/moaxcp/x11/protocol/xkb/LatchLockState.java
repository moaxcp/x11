package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class LatchLockState implements OneWayRequest {
  public static final String PLUGIN_NAME = "xkb";

  public static final byte OPCODE = 5;

  private short deviceSpec;

  private byte affectModLocks;

  private byte modLocks;

  private boolean lockGroup;

  private byte groupLock;

  private byte affectModLatches;

  private boolean latchGroup;

  private short groupLatch;

  public byte getOpCode() {
    return OPCODE;
  }

  public static LatchLockState readLatchLockState(X11Input in) throws IOException {
    LatchLockState.LatchLockStateBuilder javaBuilder = LatchLockState.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    byte affectModLocks = in.readCard8();
    byte modLocks = in.readCard8();
    boolean lockGroup = in.readBool();
    byte groupLock = in.readCard8();
    byte affectModLatches = in.readCard8();
    byte[] pad9 = in.readPad(1);
    byte[] pad10 = in.readPad(1);
    boolean latchGroup = in.readBool();
    short groupLatch = in.readCard16();
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.affectModLocks(affectModLocks);
    javaBuilder.modLocks(modLocks);
    javaBuilder.lockGroup(lockGroup);
    javaBuilder.groupLock(groupLock);
    javaBuilder.affectModLatches(affectModLatches);
    javaBuilder.latchGroup(latchGroup);
    javaBuilder.groupLatch(groupLatch);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard8(affectModLocks);
    out.writeCard8(modLocks);
    out.writeBool(lockGroup);
    out.writeCard8(groupLock);
    out.writeCard8(affectModLatches);
    out.writePad(1);
    out.writePad(1);
    out.writeBool(latchGroup);
    out.writeCard16(groupLatch);
  }

  public boolean isAffectModLocksEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(affectModLocks)) {
        return false;
      }
    }
    return true;
  }

  public boolean isModLocksEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(modLocks)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectModLatchesEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(affectModLatches)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class LatchLockStateBuilder {
    public boolean isAffectModLocksEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(affectModLocks)) {
          return false;
        }
      }
      return true;
    }

    public LatchLockState.LatchLockStateBuilder affectModLocksEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        affectModLocks((byte) m.enableFor(affectModLocks));
      }
      return this;
    }

    public LatchLockState.LatchLockStateBuilder affectModLocksDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        affectModLocks((byte) m.disableFor(affectModLocks));
      }
      return this;
    }

    public boolean isModLocksEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(modLocks)) {
          return false;
        }
      }
      return true;
    }

    public LatchLockState.LatchLockStateBuilder modLocksEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        modLocks((byte) m.enableFor(modLocks));
      }
      return this;
    }

    public LatchLockState.LatchLockStateBuilder modLocksDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        modLocks((byte) m.disableFor(modLocks));
      }
      return this;
    }

    public LatchLockState.LatchLockStateBuilder groupLock(Group groupLock) {
      this.groupLock = (byte) groupLock.getValue();
      return this;
    }

    public LatchLockState.LatchLockStateBuilder groupLock(byte groupLock) {
      this.groupLock = groupLock;
      return this;
    }

    public boolean isAffectModLatchesEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(affectModLatches)) {
          return false;
        }
      }
      return true;
    }

    public LatchLockState.LatchLockStateBuilder affectModLatchesEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        affectModLatches((byte) m.enableFor(affectModLatches));
      }
      return this;
    }

    public LatchLockState.LatchLockStateBuilder affectModLatchesDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        affectModLatches((byte) m.disableFor(affectModLatches));
      }
      return this;
    }

    public int getSize() {
      return 16;
    }
  }
}
