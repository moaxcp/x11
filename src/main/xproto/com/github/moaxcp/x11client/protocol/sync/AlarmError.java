package com.github.moaxcp.x11client.protocol.sync;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XError;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AlarmError implements XError, SyncObject {
  public static final byte CODE = 1;

  private byte firstErrorOffset;

  private short sequenceNumber;

  private int badAlarm;

  private short minorOpcode;

  private byte majorOpcode;

  @Override
  public byte getCode() {
    return (byte) (firstErrorOffset + CODE);
  }

  public static AlarmError readAlarmError(byte firstErrorOffset, X11Input in) throws IOException {
    AlarmError.AlarmErrorBuilder javaBuilder = AlarmError.builder();
    short sequenceNumber = in.readCard16();
    int badAlarm = in.readCard32();
    short minorOpcode = in.readCard16();
    byte majorOpcode = in.readCard8();
    byte[] pad6 = in.readPad(21);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.badAlarm(badAlarm);
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
    out.writeCard32(badAlarm);
    out.writeCard16(minorOpcode);
    out.writeCard8(majorOpcode);
    out.writePad(21);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class AlarmErrorBuilder {
    public int getSize() {
      return 32;
    }
  }
}
