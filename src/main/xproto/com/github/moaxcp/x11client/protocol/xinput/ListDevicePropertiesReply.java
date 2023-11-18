package com.github.moaxcp.x11client.protocol.xinput;

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
public class ListDevicePropertiesReply implements XReply, XinputObject {
  private byte xiReplyType;

  private short sequenceNumber;

  @NonNull
  private List<Integer> atoms;

  public static ListDevicePropertiesReply readListDevicePropertiesReply(byte xiReplyType,
      short sequenceNumber, X11Input in) throws IOException {
    ListDevicePropertiesReply.ListDevicePropertiesReplyBuilder javaBuilder = ListDevicePropertiesReply.builder();
    int length = in.readCard32();
    short numAtoms = in.readCard16();
    byte[] pad5 = in.readPad(22);
    List<Integer> atoms = in.readCard32(Short.toUnsignedInt(numAtoms));
    javaBuilder.xiReplyType(xiReplyType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.atoms(atoms);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(xiReplyType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    short numAtoms = (short) atoms.size();
    out.writeCard16(numAtoms);
    out.writePad(22);
    out.writeCard32(atoms);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * atoms.size();
  }

  public static class ListDevicePropertiesReplyBuilder {
    public int getSize() {
      return 32 + 4 * atoms.size();
    }
  }
}
