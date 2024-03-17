package com.github.moaxcp.x11.protocol.xselinux;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ListItem implements XStruct {
  public static final String PLUGIN_NAME = "xselinux";

  private int name;

  @NonNull
  private List<Byte> objectContext;

  @NonNull
  private List<Byte> dataContext;

  public static ListItem readListItem(X11Input in) throws IOException {
    ListItem.ListItemBuilder javaBuilder = ListItem.builder();
    int name = in.readCard32();
    int objectContextLen = in.readCard32();
    int dataContextLen = in.readCard32();
    List<Byte> objectContext = in.readChar((int) (Integer.toUnsignedLong(objectContextLen)));
    in.readPadAlign((int) (Integer.toUnsignedLong(objectContextLen)));
    List<Byte> dataContext = in.readChar((int) (Integer.toUnsignedLong(dataContextLen)));
    in.readPadAlign((int) (Integer.toUnsignedLong(dataContextLen)));
    javaBuilder.name(name);
    javaBuilder.objectContext(objectContext);
    javaBuilder.dataContext(dataContext);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(name);
    int objectContextLen = objectContext.size();
    out.writeCard32(objectContextLen);
    int dataContextLen = dataContext.size();
    out.writeCard32(dataContextLen);
    out.writeChar(objectContext);
    out.writePadAlign((int) (Integer.toUnsignedLong(objectContextLen)));
    out.writeChar(dataContext);
    out.writePadAlign((int) (Integer.toUnsignedLong(dataContextLen)));
  }

  @Override
  public int getSize() {
    return 12 + 1 * objectContext.size() + XObject.getSizeForPadAlign(4, 1 * objectContext.size()) + 1 * dataContext.size() + XObject.getSizeForPadAlign(4, 1 * dataContext.size());
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ListItemBuilder {
    public int getSize() {
      return 12 + 1 * objectContext.size() + XObject.getSizeForPadAlign(4, 1 * objectContext.size()) + 1 * dataContext.size() + XObject.getSizeForPadAlign(4, 1 * dataContext.size());
    }
  }
}
