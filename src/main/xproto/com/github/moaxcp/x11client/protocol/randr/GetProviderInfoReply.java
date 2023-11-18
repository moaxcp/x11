package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetProviderInfoReply implements XReply, RandrObject {
  private byte status;

  private short sequenceNumber;

  private int timestamp;

  private int capabilities;

  @NonNull
  private List<Integer> crtcs;

  @NonNull
  private List<Integer> outputs;

  @NonNull
  private List<Integer> associatedProviders;

  @NonNull
  private List<Integer> associatedCapability;

  @NonNull
  private List<Byte> name;

  public static GetProviderInfoReply readGetProviderInfoReply(byte status, short sequenceNumber,
      X11Input in) throws IOException {
    GetProviderInfoReply.GetProviderInfoReplyBuilder javaBuilder = GetProviderInfoReply.builder();
    int length = in.readCard32();
    int timestamp = in.readCard32();
    int capabilities = in.readCard32();
    short numCrtcs = in.readCard16();
    short numOutputs = in.readCard16();
    short numAssociatedProviders = in.readCard16();
    short nameLen = in.readCard16();
    byte[] pad10 = in.readPad(8);
    List<Integer> crtcs = in.readCard32(Short.toUnsignedInt(numCrtcs));
    List<Integer> outputs = in.readCard32(Short.toUnsignedInt(numOutputs));
    List<Integer> associatedProviders = in.readCard32(Short.toUnsignedInt(numAssociatedProviders));
    List<Integer> associatedCapability = in.readCard32(Short.toUnsignedInt(numAssociatedProviders));
    List<Byte> name = in.readChar(Short.toUnsignedInt(nameLen));
    javaBuilder.status(status);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timestamp(timestamp);
    javaBuilder.capabilities(capabilities);
    javaBuilder.crtcs(crtcs);
    javaBuilder.outputs(outputs);
    javaBuilder.associatedProviders(associatedProviders);
    javaBuilder.associatedCapability(associatedCapability);
    javaBuilder.name(name);
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
    out.writeCard32(capabilities);
    short numCrtcs = (short) crtcs.size();
    out.writeCard16(numCrtcs);
    short numOutputs = (short) outputs.size();
    out.writeCard16(numOutputs);
    short numAssociatedProviders = (short) associatedCapability.size();
    out.writeCard16(numAssociatedProviders);
    short nameLen = (short) name.size();
    out.writeCard16(nameLen);
    out.writePad(8);
    out.writeCard32(crtcs);
    out.writeCard32(outputs);
    out.writeCard32(associatedProviders);
    out.writeCard32(associatedCapability);
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  public boolean isCapabilitiesEnabled(@NonNull ProviderCapability... maskEnums) {
    for(ProviderCapability m : maskEnums) {
      if(!m.isEnabled(capabilities)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32 + 4 * crtcs.size() + 4 * outputs.size() + 4 * associatedProviders.size() + 4 * associatedCapability.size() + 1 * name.size();
  }

  public static class GetProviderInfoReplyBuilder {
    public boolean isCapabilitiesEnabled(@NonNull ProviderCapability... maskEnums) {
      for(ProviderCapability m : maskEnums) {
        if(!m.isEnabled(capabilities)) {
          return false;
        }
      }
      return true;
    }

    public GetProviderInfoReply.GetProviderInfoReplyBuilder capabilitiesEnable(
        ProviderCapability... maskEnums) {
      for(ProviderCapability m : maskEnums) {
        capabilities((int) m.enableFor(capabilities));
      }
      return this;
    }

    public GetProviderInfoReply.GetProviderInfoReplyBuilder capabilitiesDisable(
        ProviderCapability... maskEnums) {
      for(ProviderCapability m : maskEnums) {
        capabilities((int) m.disableFor(capabilities));
      }
      return this;
    }

    public int getSize() {
      return 32 + 4 * crtcs.size() + 4 * outputs.size() + 4 * associatedProviders.size() + 4 * associatedCapability.size() + 1 * name.size();
    }
  }
}
