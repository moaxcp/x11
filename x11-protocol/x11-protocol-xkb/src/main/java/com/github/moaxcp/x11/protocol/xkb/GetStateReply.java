package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import com.github.moaxcp.x11.protocol.xproto.KeyButMask;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetStateReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";

  private byte deviceID;

  private short sequenceNumber;

  private byte mods;

  private byte baseMods;

  private byte latchedMods;

  private byte lockedMods;

  private byte group;

  private byte lockedGroup;

  private short baseGroup;

  private short latchedGroup;

  private byte compatState;

  private byte grabMods;

  private byte compatGrabMods;

  private byte lookupMods;

  private byte compatLookupMods;

  private short ptrBtnState;

  public static GetStateReply readGetStateReply(byte deviceID, short sequenceNumber, X11Input in)
      throws IOException {
    GetStateReply.GetStateReplyBuilder javaBuilder = GetStateReply.builder();
    int length = in.readCard32();
    byte mods = in.readCard8();
    byte baseMods = in.readCard8();
    byte latchedMods = in.readCard8();
    byte lockedMods = in.readCard8();
    byte group = in.readCard8();
    byte lockedGroup = in.readCard8();
    short baseGroup = in.readInt16();
    short latchedGroup = in.readInt16();
    byte compatState = in.readCard8();
    byte grabMods = in.readCard8();
    byte compatGrabMods = in.readCard8();
    byte lookupMods = in.readCard8();
    byte compatLookupMods = in.readCard8();
    byte[] pad17 = in.readPad(1);
    short ptrBtnState = in.readCard16();
    byte[] pad19 = in.readPad(6);
    javaBuilder.deviceID(deviceID);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.mods(mods);
    javaBuilder.baseMods(baseMods);
    javaBuilder.latchedMods(latchedMods);
    javaBuilder.lockedMods(lockedMods);
    javaBuilder.group(group);
    javaBuilder.lockedGroup(lockedGroup);
    javaBuilder.baseGroup(baseGroup);
    javaBuilder.latchedGroup(latchedGroup);
    javaBuilder.compatState(compatState);
    javaBuilder.grabMods(grabMods);
    javaBuilder.compatGrabMods(compatGrabMods);
    javaBuilder.lookupMods(lookupMods);
    javaBuilder.compatLookupMods(compatLookupMods);
    javaBuilder.ptrBtnState(ptrBtnState);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(deviceID);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard8(mods);
    out.writeCard8(baseMods);
    out.writeCard8(latchedMods);
    out.writeCard8(lockedMods);
    out.writeCard8(group);
    out.writeCard8(lockedGroup);
    out.writeInt16(baseGroup);
    out.writeInt16(latchedGroup);
    out.writeCard8(compatState);
    out.writeCard8(grabMods);
    out.writeCard8(compatGrabMods);
    out.writeCard8(lookupMods);
    out.writeCard8(compatLookupMods);
    out.writePad(1);
    out.writeCard16(ptrBtnState);
    out.writePad(6);
  }

  public boolean isModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(mods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isBaseModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(baseMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isLatchedModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(latchedMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isLockedModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(lockedMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isCompatStateEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(compatState)) {
        return false;
      }
    }
    return true;
  }

  public boolean isGrabModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(grabMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isCompatGrabModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(compatGrabMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isLookupModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(lookupMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isCompatLookupModsEnabled(@NonNull ModMask... maskEnums) {
    for(ModMask m : maskEnums) {
      if(!m.isEnabled(compatLookupMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isPtrBtnStateEnabled(@NonNull KeyButMask... maskEnums) {
    for(KeyButMask m : maskEnums) {
      if(!m.isEnabled(ptrBtnState)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetStateReplyBuilder {
    public boolean isModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(mods)) {
          return false;
        }
      }
      return true;
    }

    public GetStateReply.GetStateReplyBuilder modsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mods((byte) m.enableFor(mods));
      }
      return this;
    }

    public GetStateReply.GetStateReplyBuilder modsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        mods((byte) m.disableFor(mods));
      }
      return this;
    }

    public boolean isBaseModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(baseMods)) {
          return false;
        }
      }
      return true;
    }

    public GetStateReply.GetStateReplyBuilder baseModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        baseMods((byte) m.enableFor(baseMods));
      }
      return this;
    }

    public GetStateReply.GetStateReplyBuilder baseModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        baseMods((byte) m.disableFor(baseMods));
      }
      return this;
    }

    public boolean isLatchedModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(latchedMods)) {
          return false;
        }
      }
      return true;
    }

    public GetStateReply.GetStateReplyBuilder latchedModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        latchedMods((byte) m.enableFor(latchedMods));
      }
      return this;
    }

    public GetStateReply.GetStateReplyBuilder latchedModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        latchedMods((byte) m.disableFor(latchedMods));
      }
      return this;
    }

    public boolean isLockedModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(lockedMods)) {
          return false;
        }
      }
      return true;
    }

    public GetStateReply.GetStateReplyBuilder lockedModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        lockedMods((byte) m.enableFor(lockedMods));
      }
      return this;
    }

    public GetStateReply.GetStateReplyBuilder lockedModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        lockedMods((byte) m.disableFor(lockedMods));
      }
      return this;
    }

    public GetStateReply.GetStateReplyBuilder group(Group group) {
      this.group = (byte) group.getValue();
      return this;
    }

    public GetStateReply.GetStateReplyBuilder group(byte group) {
      this.group = group;
      return this;
    }

    public GetStateReply.GetStateReplyBuilder lockedGroup(Group lockedGroup) {
      this.lockedGroup = (byte) lockedGroup.getValue();
      return this;
    }

    public GetStateReply.GetStateReplyBuilder lockedGroup(byte lockedGroup) {
      this.lockedGroup = lockedGroup;
      return this;
    }

    public boolean isCompatStateEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(compatState)) {
          return false;
        }
      }
      return true;
    }

    public GetStateReply.GetStateReplyBuilder compatStateEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        compatState((byte) m.enableFor(compatState));
      }
      return this;
    }

    public GetStateReply.GetStateReplyBuilder compatStateDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        compatState((byte) m.disableFor(compatState));
      }
      return this;
    }

    public boolean isGrabModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(grabMods)) {
          return false;
        }
      }
      return true;
    }

    public GetStateReply.GetStateReplyBuilder grabModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        grabMods((byte) m.enableFor(grabMods));
      }
      return this;
    }

    public GetStateReply.GetStateReplyBuilder grabModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        grabMods((byte) m.disableFor(grabMods));
      }
      return this;
    }

    public boolean isCompatGrabModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(compatGrabMods)) {
          return false;
        }
      }
      return true;
    }

    public GetStateReply.GetStateReplyBuilder compatGrabModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        compatGrabMods((byte) m.enableFor(compatGrabMods));
      }
      return this;
    }

    public GetStateReply.GetStateReplyBuilder compatGrabModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        compatGrabMods((byte) m.disableFor(compatGrabMods));
      }
      return this;
    }

    public boolean isLookupModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(lookupMods)) {
          return false;
        }
      }
      return true;
    }

    public GetStateReply.GetStateReplyBuilder lookupModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        lookupMods((byte) m.enableFor(lookupMods));
      }
      return this;
    }

    public GetStateReply.GetStateReplyBuilder lookupModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        lookupMods((byte) m.disableFor(lookupMods));
      }
      return this;
    }

    public boolean isCompatLookupModsEnabled(@NonNull ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        if(!m.isEnabled(compatLookupMods)) {
          return false;
        }
      }
      return true;
    }

    public GetStateReply.GetStateReplyBuilder compatLookupModsEnable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        compatLookupMods((byte) m.enableFor(compatLookupMods));
      }
      return this;
    }

    public GetStateReply.GetStateReplyBuilder compatLookupModsDisable(ModMask... maskEnums) {
      for(ModMask m : maskEnums) {
        compatLookupMods((byte) m.disableFor(compatLookupMods));
      }
      return this;
    }

    public boolean isPtrBtnStateEnabled(@NonNull KeyButMask... maskEnums) {
      for(KeyButMask m : maskEnums) {
        if(!m.isEnabled(ptrBtnState)) {
          return false;
        }
      }
      return true;
    }

    public GetStateReply.GetStateReplyBuilder ptrBtnStateEnable(KeyButMask... maskEnums) {
      for(KeyButMask m : maskEnums) {
        ptrBtnState((short) m.enableFor(ptrBtnState));
      }
      return this;
    }

    public GetStateReply.GetStateReplyBuilder ptrBtnStateDisable(KeyButMask... maskEnums) {
      for(KeyButMask m : maskEnums) {
        ptrBtnState((short) m.disableFor(ptrBtnState));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
