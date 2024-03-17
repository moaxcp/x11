package com.github.moaxcp.x11.protocol.record;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ClientInfo implements XStruct {
  public static final String PLUGIN_NAME = "record";

  private int clientResource;

  @NonNull
  private List<Range> ranges;

  public static ClientInfo readClientInfo(X11Input in) throws IOException {
    ClientInfo.ClientInfoBuilder javaBuilder = ClientInfo.builder();
    int clientResource = in.readCard32();
    int numRanges = in.readCard32();
    List<Range> ranges = new ArrayList<>((int) (Integer.toUnsignedLong(numRanges)));
    for(int i = 0; i < Integer.toUnsignedLong(numRanges); i++) {
      ranges.add(Range.readRange(in));
    }
    javaBuilder.clientResource(clientResource);
    javaBuilder.ranges(ranges);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(clientResource);
    int numRanges = ranges.size();
    out.writeCard32(numRanges);
    for(Range t : ranges) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(ranges);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ClientInfoBuilder {
    public ClientInfo.ClientInfoBuilder clientResource(Cs clientResource) {
      this.clientResource = (int) clientResource.getValue();
      return this;
    }

    public ClientInfo.ClientInfoBuilder clientResource(int clientResource) {
      this.clientResource = clientResource;
      return this;
    }

    public int getSize() {
      return 8 + XObject.sizeOf(ranges);
    }
  }
}
