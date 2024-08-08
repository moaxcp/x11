package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class SetupRequest implements XStruct {
  public static final String PLUGIN_NAME = "xproto";

  private byte byteOrder;

  private short protocolMajorVersion;

  private short protocolMinorVersion;

  @NonNull
  private ByteList authorizationProtocolName;

  @NonNull
  private ByteList authorizationProtocolData;

  public static SetupRequest readSetupRequest(X11Input in) throws IOException {
    SetupRequest.SetupRequestBuilder javaBuilder = SetupRequest.builder();
    byte byteOrder = in.readCard8();
    byte[] pad1 = in.readPad(1);
    short protocolMajorVersion = in.readCard16();
    short protocolMinorVersion = in.readCard16();
    short authorizationProtocolNameLen = in.readCard16();
    short authorizationProtocolDataLen = in.readCard16();
    byte[] pad6 = in.readPad(2);
    ByteList authorizationProtocolName = in.readChar(Short.toUnsignedInt(authorizationProtocolNameLen));
    in.readPadAlign(Short.toUnsignedInt(authorizationProtocolNameLen));
    ByteList authorizationProtocolData = in.readChar(Short.toUnsignedInt(authorizationProtocolDataLen));
    in.readPadAlign(Short.toUnsignedInt(authorizationProtocolDataLen));
    javaBuilder.byteOrder(byteOrder);
    javaBuilder.protocolMajorVersion(protocolMajorVersion);
    javaBuilder.protocolMinorVersion(protocolMinorVersion);
    javaBuilder.authorizationProtocolName(authorizationProtocolName.toImmutable());
    javaBuilder.authorizationProtocolData(authorizationProtocolData.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(byteOrder);
    out.writePad(1);
    out.writeCard16(protocolMajorVersion);
    out.writeCard16(protocolMinorVersion);
    short authorizationProtocolNameLen = (short) authorizationProtocolName.size();
    out.writeCard16(authorizationProtocolNameLen);
    short authorizationProtocolDataLen = (short) authorizationProtocolData.size();
    out.writeCard16(authorizationProtocolDataLen);
    out.writePad(2);
    out.writeChar(authorizationProtocolName);
    out.writePadAlign(Short.toUnsignedInt(authorizationProtocolNameLen));
    out.writeChar(authorizationProtocolData);
    out.writePadAlign(Short.toUnsignedInt(authorizationProtocolDataLen));
  }

  @Override
  public int getSize() {
    return 12 + 1 * authorizationProtocolName.size() + XObject.getSizeForPadAlign(4, 1 * authorizationProtocolName.size()) + 1 * authorizationProtocolData.size() + XObject.getSizeForPadAlign(4, 1 * authorizationProtocolData.size());
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class SetupRequestBuilder {
    public int getSize() {
      return 12 + 1 * authorizationProtocolName.size() + XObject.getSizeForPadAlign(4, 1 * authorizationProtocolName.size()) + 1 * authorizationProtocolData.size() + XObject.getSizeForPadAlign(4, 1 * authorizationProtocolData.size());
    }
  }
}
