package com.github.moaxcp.x11client.protocol.shm;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AttachFd implements OneWayRequest, ShmObject {
  public static final byte OPCODE = 6;

  private int shmseg;

  private int shmFd;

  private boolean readOnly;

  public byte getOpCode() {
    return OPCODE;
  }

  public static AttachFd readAttachFd(X11Input in) throws IOException {
    AttachFd.AttachFdBuilder javaBuilder = AttachFd.builder();
    byte[] pad1 = in.readPad(1);
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
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
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

  public static class AttachFdBuilder {
    public int getSize() {
      return 16;
    }
  }
}
