package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class InternAtom implements TwoWayRequest<InternAtomReply> {
  public static final String PLUGIN_NAME = "xproto";

  public static final byte OPCODE = 16;

  private boolean onlyIfExists;

  @NonNull
  private List<Byte> name;

  public XReplyFunction<InternAtomReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> InternAtomReply.readInternAtomReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static InternAtom readInternAtom(X11Input in) throws IOException {
    InternAtom.InternAtomBuilder javaBuilder = InternAtom.builder();
    boolean onlyIfExists = in.readBool();
    short length = in.readCard16();
    short nameLen = in.readCard16();
    byte[] pad4 = in.readPad(2);
    List<Byte> name = in.readChar(Short.toUnsignedInt(nameLen));
    javaBuilder.onlyIfExists(onlyIfExists);
    javaBuilder.name(name);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeBool(onlyIfExists);
    out.writeCard16((short) getLength());
    short nameLen = (short) name.size();
    out.writeCard16(nameLen);
    out.writePad(2);
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + 1 * name.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class InternAtomBuilder {
    public int getSize() {
      return 8 + 1 * name.size();
    }
  }
}
