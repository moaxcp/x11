package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XError;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CounterError implements XError {
  public static final String PLUGIN_NAME = "sync";

  public static final byte CODE = 0;

  private byte firstErrorOffset;

  private short sequenceNumber;

  private int badCounter;

  private short minorOpcode;

  private byte majorOpcode;

  @Override
  public byte getCode() {
    return (byte) (firstErrorOffset + CODE);
  }

  public static CounterError readCounterError(byte firstErrorOffset, X11Input in) throws
      IOException {
    CounterError.CounterErrorBuilder javaBuilder = CounterError.builder();
    short sequenceNumber = in.readCard16();
    int badCounter = in.readCard32();
    short minorOpcode = in.readCard16();
    byte majorOpcode = in.readCard8();
    byte[] pad6 = in.readPad(21);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.badCounter(badCounter);
    javaBuilder.minorOpcode(minorOpcode);
    javaBuilder.majorOpcode(majorOpcode);

    javaBuilder.firstErrorOffset(firstErrorOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(getCode());
    out.writeCard16(sequenceNumber);
    out.writeCard32(badCounter);
    out.writeCard16(minorOpcode);
    out.writeCard8(majorOpcode);
    out.writePad(21);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CounterErrorBuilder {
    public int getSize() {
      return 32;
    }
  }
}
