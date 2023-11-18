package com.github.moaxcp.x11client.protocol.sync;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Await implements OneWayRequest, SyncObject {
  public static final byte OPCODE = 7;

  @NonNull
  private List<Waitcondition> waitList;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Await readAwait(X11Input in) throws IOException {
    Await.AwaitBuilder javaBuilder = Await.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    List<Waitcondition> waitList = new ArrayList<>(length - javaStart);
    while(javaStart < Short.toUnsignedInt(length) * 4) {
      Waitcondition baseObject = Waitcondition.readWaitcondition(in);
      waitList.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.waitList(waitList);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    for(Waitcondition t : waitList) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 4 + XObject.sizeOf(waitList);
  }

  public static class AwaitBuilder {
    public int getSize() {
      return 4 + XObject.sizeOf(waitList);
    }
  }
}
