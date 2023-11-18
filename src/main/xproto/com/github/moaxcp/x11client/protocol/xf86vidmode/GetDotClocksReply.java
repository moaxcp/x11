package com.github.moaxcp.x11client.protocol.xf86vidmode;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetDotClocksReply implements XReply, Xf86vidmodeObject {
  private short sequenceNumber;

  private int flags;

  private int clocks;

  private int maxclocks;

  @NonNull
  private List<Integer> clock;

  public static GetDotClocksReply readGetDotClocksReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetDotClocksReply.GetDotClocksReplyBuilder javaBuilder = GetDotClocksReply.builder();
    int length = in.readCard32();
    int flags = in.readCard32();
    int clocks = in.readCard32();
    int maxclocks = in.readCard32();
    byte[] pad7 = in.readPad(12);
    List<Integer> clock = in.readCard32((int) ((1 - (Integer.toUnsignedLong(flags)) & (1)) * Integer.toUnsignedLong(clocks)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.flags(flags);
    javaBuilder.clocks(clocks);
    javaBuilder.maxclocks(maxclocks);
    javaBuilder.clock(clock);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(flags);
    out.writeCard32(clocks);
    out.writeCard32(maxclocks);
    out.writePad(12);
    out.writeCard32(clock);
    out.writePadAlign(getSize());
  }

  public boolean isFlagsEnabled(@NonNull ClockFlag... maskEnums) {
    for(ClockFlag m : maskEnums) {
      if(!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32 + 4 * clock.size();
  }

  public static class GetDotClocksReplyBuilder {
    public boolean isFlagsEnabled(@NonNull ClockFlag... maskEnums) {
      for(ClockFlag m : maskEnums) {
        if(!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public GetDotClocksReply.GetDotClocksReplyBuilder flagsEnable(ClockFlag... maskEnums) {
      for(ClockFlag m : maskEnums) {
        flags((int) m.enableFor(flags));
      }
      return this;
    }

    public GetDotClocksReply.GetDotClocksReplyBuilder flagsDisable(ClockFlag... maskEnums) {
      for(ClockFlag m : maskEnums) {
        flags((int) m.disableFor(flags));
      }
      return this;
    }

    public int getSize() {
      return 32 + 4 * clock.size();
    }
  }
}
