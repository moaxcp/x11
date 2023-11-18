package com.github.moaxcp.x11client.protocol.record;

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
public class RegisterClients implements OneWayRequest, RecordObject {
  public static final byte OPCODE = 2;

  private int context;

  private byte elementHeader;

  @NonNull
  private List<Integer> clientSpecs;

  @NonNull
  private List<Range> ranges;

  public byte getOpCode() {
    return OPCODE;
  }

  public static RegisterClients readRegisterClients(X11Input in) throws IOException {
    RegisterClients.RegisterClientsBuilder javaBuilder = RegisterClients.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int context = in.readCard32();
    byte elementHeader = in.readCard8();
    byte[] pad5 = in.readPad(3);
    int numClientSpecs = in.readCard32();
    int numRanges = in.readCard32();
    List<Integer> clientSpecs = in.readCard32((int) (Integer.toUnsignedLong(numClientSpecs)));
    List<Range> ranges = new ArrayList<>((int) (Integer.toUnsignedLong(numRanges)));
    for(int i = 0; i < Integer.toUnsignedLong(numRanges); i++) {
      ranges.add(Range.readRange(in));
    }
    javaBuilder.context(context);
    javaBuilder.elementHeader(elementHeader);
    javaBuilder.clientSpecs(clientSpecs);
    javaBuilder.ranges(ranges);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(context);
    out.writeCard8(elementHeader);
    out.writePad(3);
    int numClientSpecs = clientSpecs.size();
    out.writeCard32(numClientSpecs);
    int numRanges = ranges.size();
    out.writeCard32(numRanges);
    out.writeCard32(clientSpecs);
    for(Range t : ranges) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 20 + 4 * clientSpecs.size() + XObject.sizeOf(ranges);
  }

  public static class RegisterClientsBuilder {
    public int getSize() {
      return 20 + 4 * clientSpecs.size() + XObject.sizeOf(ranges);
    }
  }
}
