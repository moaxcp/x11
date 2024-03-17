package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetMap implements TwoWayRequest<GetMapReply> {
  public static final String PLUGIN_NAME = "xkb";

  public static final byte OPCODE = 8;

  private short deviceSpec;

  private short full;

  private short partial;

  private byte firstType;

  private byte nTypes;

  private byte firstKeySym;

  private byte nKeySyms;

  private byte firstKeyAction;

  private byte nKeyActions;

  private byte firstKeyBehavior;

  private byte nKeyBehaviors;

  private short virtualMods;

  private byte firstKeyExplicit;

  private byte nKeyExplicit;

  private byte firstModMapKey;

  private byte nModMapKeys;

  private byte firstVModMapKey;

  private byte nVModMapKeys;

  public XReplyFunction<GetMapReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetMapReply.readGetMapReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetMap readGetMap(X11Input in) throws IOException {
    GetMap.GetMapBuilder javaBuilder = GetMap.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    short full = in.readCard16();
    short partial = in.readCard16();
    byte firstType = in.readCard8();
    byte nTypes = in.readCard8();
    byte firstKeySym = in.readCard8();
    byte nKeySyms = in.readCard8();
    byte firstKeyAction = in.readCard8();
    byte nKeyActions = in.readCard8();
    byte firstKeyBehavior = in.readCard8();
    byte nKeyBehaviors = in.readCard8();
    short virtualMods = in.readCard16();
    byte firstKeyExplicit = in.readCard8();
    byte nKeyExplicit = in.readCard8();
    byte firstModMapKey = in.readCard8();
    byte nModMapKeys = in.readCard8();
    byte firstVModMapKey = in.readCard8();
    byte nVModMapKeys = in.readCard8();
    byte[] pad21 = in.readPad(2);
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.full(full);
    javaBuilder.partial(partial);
    javaBuilder.firstType(firstType);
    javaBuilder.nTypes(nTypes);
    javaBuilder.firstKeySym(firstKeySym);
    javaBuilder.nKeySyms(nKeySyms);
    javaBuilder.firstKeyAction(firstKeyAction);
    javaBuilder.nKeyActions(nKeyActions);
    javaBuilder.firstKeyBehavior(firstKeyBehavior);
    javaBuilder.nKeyBehaviors(nKeyBehaviors);
    javaBuilder.virtualMods(virtualMods);
    javaBuilder.firstKeyExplicit(firstKeyExplicit);
    javaBuilder.nKeyExplicit(nKeyExplicit);
    javaBuilder.firstModMapKey(firstModMapKey);
    javaBuilder.nModMapKeys(nModMapKeys);
    javaBuilder.firstVModMapKey(firstVModMapKey);
    javaBuilder.nVModMapKeys(nVModMapKeys);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard16(full);
    out.writeCard16(partial);
    out.writeCard8(firstType);
    out.writeCard8(nTypes);
    out.writeCard8(firstKeySym);
    out.writeCard8(nKeySyms);
    out.writeCard8(firstKeyAction);
    out.writeCard8(nKeyActions);
    out.writeCard8(firstKeyBehavior);
    out.writeCard8(nKeyBehaviors);
    out.writeCard16(virtualMods);
    out.writeCard8(firstKeyExplicit);
    out.writeCard8(nKeyExplicit);
    out.writeCard8(firstModMapKey);
    out.writeCard8(nModMapKeys);
    out.writeCard8(firstVModMapKey);
    out.writeCard8(nVModMapKeys);
    out.writePad(2);
  }

  public boolean isFullEnabled(@NonNull MapPart... maskEnums) {
    for(MapPart m : maskEnums) {
      if(!m.isEnabled(full)) {
        return false;
      }
    }
    return true;
  }

  public boolean isPartialEnabled(@NonNull MapPart... maskEnums) {
    for(MapPart m : maskEnums) {
      if(!m.isEnabled(partial)) {
        return false;
      }
    }
    return true;
  }

  public boolean isVirtualModsEnabled(@NonNull VMod... maskEnums) {
    for(VMod m : maskEnums) {
      if(!m.isEnabled(virtualMods)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetMapBuilder {
    public boolean isFullEnabled(@NonNull MapPart... maskEnums) {
      for(MapPart m : maskEnums) {
        if(!m.isEnabled(full)) {
          return false;
        }
      }
      return true;
    }

    public GetMap.GetMapBuilder fullEnable(MapPart... maskEnums) {
      for(MapPart m : maskEnums) {
        full((short) m.enableFor(full));
      }
      return this;
    }

    public GetMap.GetMapBuilder fullDisable(MapPart... maskEnums) {
      for(MapPart m : maskEnums) {
        full((short) m.disableFor(full));
      }
      return this;
    }

    public boolean isPartialEnabled(@NonNull MapPart... maskEnums) {
      for(MapPart m : maskEnums) {
        if(!m.isEnabled(partial)) {
          return false;
        }
      }
      return true;
    }

    public GetMap.GetMapBuilder partialEnable(MapPart... maskEnums) {
      for(MapPart m : maskEnums) {
        partial((short) m.enableFor(partial));
      }
      return this;
    }

    public GetMap.GetMapBuilder partialDisable(MapPart... maskEnums) {
      for(MapPart m : maskEnums) {
        partial((short) m.disableFor(partial));
      }
      return this;
    }

    public boolean isVirtualModsEnabled(@NonNull VMod... maskEnums) {
      for(VMod m : maskEnums) {
        if(!m.isEnabled(virtualMods)) {
          return false;
        }
      }
      return true;
    }

    public GetMap.GetMapBuilder virtualModsEnable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        virtualMods((short) m.enableFor(virtualMods));
      }
      return this;
    }

    public GetMap.GetMapBuilder virtualModsDisable(VMod... maskEnums) {
      for(VMod m : maskEnums) {
        virtualMods((short) m.disableFor(virtualMods));
      }
      return this;
    }

    public int getSize() {
      return 28;
    }
  }
}
