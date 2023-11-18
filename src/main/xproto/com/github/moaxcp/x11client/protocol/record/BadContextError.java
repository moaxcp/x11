package com.github.moaxcp.x11client.protocol.record;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XError;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BadContextError implements XError, RecordObject {
  public static final byte CODE = 0;

  private byte firstErrorOffset;

  private short sequenceNumber;

  private short minorOpcode;

  private byte majorOpcode;

  private int invalidRecord;

  @Override
  public byte getCode() {
    return (byte) (firstErrorOffset + CODE);
  }

  public static BadContextError readBadContextError(byte firstErrorOffset, X11Input in) throws
      IOException {
    BadContextError.BadContextErrorBuilder javaBuilder = BadContextError.builder();
    short sequenceNumber = in.readCard16();
    short minorOpcode = in.readCard16();
    byte majorOpcode = in.readCard8();
    int invalidRecord = in.readCard32();
    byte[] pad6 = in.readPad(21);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.minorOpcode(minorOpcode);
    javaBuilder.majorOpcode(majorOpcode);
    javaBuilder.invalidRecord(invalidRecord);

    javaBuilder.firstErrorOffset(firstErrorOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(getCode());
    out.writeCard16(sequenceNumber);
    out.writeCard16(minorOpcode);
    out.writeCard8(majorOpcode);
    out.writeCard32(invalidRecord);
    out.writePad(21);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class BadContextErrorBuilder {
    public int getSize() {
      return 32;
    }
  }
}
