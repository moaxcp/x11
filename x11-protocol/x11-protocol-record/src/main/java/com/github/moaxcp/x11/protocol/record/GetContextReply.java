package com.github.moaxcp.x11.protocol.record;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Value
@Builder
public class GetContextReply implements XReply {
  public static final String PLUGIN_NAME = "record";

  private boolean enabled;

  private short sequenceNumber;

  private byte elementHeader;

  @NonNull
  private ImmutableList<ClientInfo> interceptedClients;

  public static GetContextReply readGetContextReply(byte enabled, short sequenceNumber, X11Input in)
      throws IOException {
    GetContextReply.GetContextReplyBuilder javaBuilder = GetContextReply.builder();
    int length = in.readCard32();
    byte elementHeader = in.readCard8();
    byte[] pad5 = in.readPad(3);
    int numInterceptedClients = in.readCard32();
    byte[] pad7 = in.readPad(16);
    MutableList<ClientInfo> interceptedClients = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numInterceptedClients)));
    for(int i = 0; i < Integer.toUnsignedLong(numInterceptedClients); i++) {
      interceptedClients.add(ClientInfo.readClientInfo(in));
    }
    javaBuilder.enabled(enabled > 0);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.elementHeader(elementHeader);
    javaBuilder.interceptedClients(interceptedClients.toImmutable());
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeBool(enabled);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard8(elementHeader);
    out.writePad(3);
    int numInterceptedClients = interceptedClients.size();
    out.writeCard32(numInterceptedClients);
    out.writePad(16);
    for(ClientInfo t : interceptedClients) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  public boolean isElementHeaderEnabled(@NonNull HType... maskEnums) {
    for(HType m : maskEnums) {
      if(!m.isEnabled(elementHeader)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(interceptedClients);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetContextReplyBuilder {
    public boolean isElementHeaderEnabled(@NonNull HType... maskEnums) {
      for(HType m : maskEnums) {
        if(!m.isEnabled(elementHeader)) {
          return false;
        }
      }
      return true;
    }

    public GetContextReply.GetContextReplyBuilder elementHeaderEnable(HType... maskEnums) {
      for(HType m : maskEnums) {
        elementHeader((byte) m.enableFor(elementHeader));
      }
      return this;
    }

    public GetContextReply.GetContextReplyBuilder elementHeaderDisable(HType... maskEnums) {
      for(HType m : maskEnums) {
        elementHeader((byte) m.disableFor(elementHeader));
      }
      return this;
    }

    public int getSize() {
      return 32 + XObject.sizeOf(interceptedClients);
    }
  }
}
