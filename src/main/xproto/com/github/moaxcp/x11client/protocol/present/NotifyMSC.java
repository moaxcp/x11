package com.github.moaxcp.x11client.protocol.present;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NotifyMSC implements OneWayRequest, PresentObject {
  public static final byte OPCODE = 2;

  private int window;

  private int serial;

  private long targetMsc;

  private long divisor;

  private long remainder;

  public byte getOpCode() {
    return OPCODE;
  }

  public static NotifyMSC readNotifyMSC(X11Input in) throws IOException {
    NotifyMSC.NotifyMSCBuilder javaBuilder = NotifyMSC.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    int serial = in.readCard32();
    byte[] pad5 = in.readPad(4);
    long targetMsc = in.readCard64();
    long divisor = in.readCard64();
    long remainder = in.readCard64();
    javaBuilder.window(window);
    javaBuilder.serial(serial);
    javaBuilder.targetMsc(targetMsc);
    javaBuilder.divisor(divisor);
    javaBuilder.remainder(remainder);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(serial);
    out.writePad(4);
    out.writeCard64(targetMsc);
    out.writeCard64(divisor);
    out.writeCard64(remainder);
  }

  @Override
  public int getSize() {
    return 40;
  }

  public static class NotifyMSCBuilder {
    public int getSize() {
      return 40;
    }
  }
}
