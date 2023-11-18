package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SetupRequest implements XStruct, XprotoObject {
  private byte byteOrder;

  private short protocolMajorVersion;

  private short protocolMinorVersion;

  @NonNull
  private List<Byte> authorizationProtocolName;

  @NonNull
  private List<Byte> authorizationProtocolData;

  public static SetupRequest readSetupRequest(X11Input in) throws IOException {
    SetupRequest.SetupRequestBuilder javaBuilder = SetupRequest.builder();
    byte byteOrder = in.readCard8();
    byte[] pad1 = in.readPad(1);
    short protocolMajorVersion = in.readCard16();
    short protocolMinorVersion = in.readCard16();
    short authorizationProtocolNameLen = in.readCard16();
    short authorizationProtocolDataLen = in.readCard16();
    byte[] pad6 = in.readPad(2);
    List<Byte> authorizationProtocolName = in.readChar(Short.toUnsignedInt(authorizationProtocolNameLen));
    in.readPadAlign(Short.toUnsignedInt(authorizationProtocolNameLen));
    List<Byte> authorizationProtocolData = in.readChar(Short.toUnsignedInt(authorizationProtocolDataLen));
    in.readPadAlign(Short.toUnsignedInt(authorizationProtocolDataLen));
    javaBuilder.byteOrder(byteOrder);
    javaBuilder.protocolMajorVersion(protocolMajorVersion);
    javaBuilder.protocolMinorVersion(protocolMinorVersion);
    javaBuilder.authorizationProtocolName(authorizationProtocolName);
    javaBuilder.authorizationProtocolData(authorizationProtocolData);
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

  public static class SetupRequestBuilder {
    public int getSize() {
      return 12 + 1 * authorizationProtocolName.size() + XObject.getSizeForPadAlign(4, 1 * authorizationProtocolName.size()) + 1 * authorizationProtocolData.size() + XObject.getSizeForPadAlign(4, 1 * authorizationProtocolData.size());
    }
  }
}
