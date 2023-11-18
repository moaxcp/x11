package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LeaseNotify implements NotifyDataUnion, XStruct, RandrObject {
  private int timestamp;

  private int window;

  private int lease;

  private byte created;

  public static LeaseNotify readLeaseNotify(X11Input in) throws IOException {
    LeaseNotify.LeaseNotifyBuilder javaBuilder = LeaseNotify.builder();
    int timestamp = in.readCard32();
    int window = in.readCard32();
    int lease = in.readCard32();
    byte created = in.readCard8();
    byte[] pad4 = in.readPad(15);
    javaBuilder.timestamp(timestamp);
    javaBuilder.window(window);
    javaBuilder.lease(lease);
    javaBuilder.created(created);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(timestamp);
    out.writeCard32(window);
    out.writeCard32(lease);
    out.writeCard8(created);
    out.writePad(15);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public static class LeaseNotifyBuilder {
    public int getSize() {
      return 28;
    }
  }
}
