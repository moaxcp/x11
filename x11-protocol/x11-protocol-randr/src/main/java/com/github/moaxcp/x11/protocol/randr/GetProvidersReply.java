package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class GetProvidersReply implements XReply {
  public static final String PLUGIN_NAME = "randr";

  private short sequenceNumber;

  private int timestamp;

  @NonNull
  private IntList providers;

  public static GetProvidersReply readGetProvidersReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetProvidersReply.GetProvidersReplyBuilder javaBuilder = GetProvidersReply.builder();
    int length = in.readCard32();
    int timestamp = in.readCard32();
    short numProviders = in.readCard16();
    byte[] pad6 = in.readPad(18);
    IntList providers = in.readCard32(Short.toUnsignedInt(numProviders));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timestamp(timestamp);
    javaBuilder.providers(providers.toImmutable());
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(timestamp);
    short numProviders = (short) providers.size();
    out.writeCard16(numProviders);
    out.writePad(18);
    out.writeCard32(providers);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * providers.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetProvidersReplyBuilder {
    public int getSize() {
      return 32 + 4 * providers.size();
    }
  }
}
