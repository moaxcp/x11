package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Await implements OneWayRequest {
  public static final String PLUGIN_NAME = "sync";

  public static final byte OPCODE = 7;

  @NonNull
  private List<Waitcondition> waitList;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Await readAwait(X11Input in) throws IOException {
    Await.AwaitBuilder javaBuilder = Await.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    List<Waitcondition> waitList = new ArrayList<>(Short.toUnsignedInt(length) - javaStart);
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
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class AwaitBuilder {
    public int getSize() {
      return 4 + XObject.sizeOf(waitList);
    }
  }
}
