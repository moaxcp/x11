package com.github.moaxcp.x11client.protocol.xselinux;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ListPropertiesReply implements XReply, XselinuxObject {
  private short sequenceNumber;

  @NonNull
  private List<ListItem> properties;

  public static ListPropertiesReply readListPropertiesReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    ListPropertiesReply.ListPropertiesReplyBuilder javaBuilder = ListPropertiesReply.builder();
    int length = in.readCard32();
    int propertiesLen = in.readCard32();
    byte[] pad5 = in.readPad(20);
    List<ListItem> properties = new ArrayList<>((int) (Integer.toUnsignedLong(propertiesLen)));
    for(int i = 0; i < Integer.toUnsignedLong(propertiesLen); i++) {
      properties.add(ListItem.readListItem(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.properties(properties);
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
    int propertiesLen = properties.size();
    out.writeCard32(propertiesLen);
    out.writePad(20);
    for(ListItem t : properties) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(properties);
  }

  public static class ListPropertiesReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(properties);
    }
  }
}
