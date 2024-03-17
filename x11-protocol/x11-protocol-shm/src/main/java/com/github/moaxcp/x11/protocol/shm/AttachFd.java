package com.github.moaxcp.x11.protocol.shm;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AttachFd implements OneWayRequest {
  public static final String PLUGIN_NAME = "shm";

  public static final byte OPCODE = 6;

  private int shmseg;

  private int shmFd;

  private boolean readOnly;

  public byte getOpCode() {
    return OPCODE;
  }

  public static AttachFd readAttachFd(X11Input in) throws IOException {
    AttachFd.AttachFdBuilder javaBuilder = AttachFd.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int shmseg = in.readCard32();
    int shmFd = in.readInt32();
    boolean readOnly = in.readBool();
    byte[] pad6 = in.readPad(3);
    javaBuilder.shmseg(shmseg);
    javaBuilder.shmFd(shmFd);
    javaBuilder.readOnly(readOnly);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(shmseg);
    out.writeInt32(shmFd);
    out.writeBool(readOnly);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class AttachFdBuilder {
    public int getSize() {
      return 16;
    }
  }
}
