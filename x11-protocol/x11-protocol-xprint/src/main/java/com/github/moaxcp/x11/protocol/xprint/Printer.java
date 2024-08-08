package com.github.moaxcp.x11.protocol.xprint;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ByteList;

@Value
@Builder
public class Printer implements XStruct {
  public static final String PLUGIN_NAME = "xprint";

  @NonNull
  private ByteList name;

  @NonNull
  private ByteList description;

  public static Printer readPrinter(X11Input in) throws IOException {
    Printer.PrinterBuilder javaBuilder = Printer.builder();
    int nameLen = in.readCard32();
    ByteList name = in.readChar((int) (Integer.toUnsignedLong(nameLen)));
    in.readPadAlign((int) (Integer.toUnsignedLong(nameLen)));
    int descLen = in.readCard32();
    ByteList description = in.readChar((int) (Integer.toUnsignedLong(descLen)));
    in.readPadAlign((int) (Integer.toUnsignedLong(descLen)));
    javaBuilder.name(name.toImmutable());
    javaBuilder.description(description.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    int nameLen = name.size();
    out.writeCard32(nameLen);
    out.writeChar(name);
    out.writePadAlign((int) (Integer.toUnsignedLong(nameLen)));
    int descLen = description.size();
    out.writeCard32(descLen);
    out.writeChar(description);
    out.writePadAlign((int) (Integer.toUnsignedLong(descLen)));
  }

  @Override
  public int getSize() {
    return 8 + 1 * name.size() + XObject.getSizeForPadAlign(4, 1 * name.size()) + 1 * description.size() + XObject.getSizeForPadAlign(4, 1 * description.size());
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PrinterBuilder {
    public int getSize() {
      return 8 + 1 * name.size() + XObject.getSizeForPadAlign(4, 1 * name.size()) + 1 * description.size() + XObject.getSizeForPadAlign(4, 1 * description.size());
    }
  }
}
