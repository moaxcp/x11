package com.github.moaxcp.x11.protocol.record;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class CreateContext implements OneWayRequest {
  public static final String PLUGIN_NAME = "record";

  public static final byte OPCODE = 1;

  private int context;

  private byte elementHeader;

  @NonNull
  private IntList clientSpecs;

  @NonNull
  private ImmutableList<Range> ranges;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateContext readCreateContext(X11Input in) throws IOException {
    CreateContext.CreateContextBuilder javaBuilder = CreateContext.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int context = in.readCard32();
    byte elementHeader = in.readCard8();
    byte[] pad5 = in.readPad(3);
    int numClientSpecs = in.readCard32();
    int numRanges = in.readCard32();
    IntList clientSpecs = in.readCard32((int) (Integer.toUnsignedLong(numClientSpecs)));
    MutableList<Range> ranges = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numRanges)));
    for(int i = 0; i < Integer.toUnsignedLong(numRanges); i++) {
      ranges.add(Range.readRange(in));
    }
    javaBuilder.context(context);
    javaBuilder.elementHeader(elementHeader);
    javaBuilder.clientSpecs(clientSpecs.toImmutable());
    javaBuilder.ranges(ranges.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public boolean isElementHeaderEnabled(@NonNull HType... maskEnums) {
    for(HType m : maskEnums) {
      if(!m.isEnabled(elementHeader)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 20 + 4 * clientSpecs.size() + XObject.sizeOf(ranges);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateContextBuilder {
    public boolean isElementHeaderEnabled(@NonNull HType... maskEnums) {
      for(HType m : maskEnums) {
        if(!m.isEnabled(elementHeader)) {
          return false;
        }
      }
      return true;
    }

    public CreateContext.CreateContextBuilder elementHeaderEnable(HType... maskEnums) {
      for(HType m : maskEnums) {
        elementHeader((byte) m.enableFor(elementHeader));
      }
      return this;
    }

    public CreateContext.CreateContextBuilder elementHeaderDisable(HType... maskEnums) {
      for(HType m : maskEnums) {
        elementHeader((byte) m.disableFor(elementHeader));
      }
      return this;
    }

    public int getSize() {
      return 20 + 4 * clientSpecs.size() + XObject.sizeOf(ranges);
    }
  }
}
