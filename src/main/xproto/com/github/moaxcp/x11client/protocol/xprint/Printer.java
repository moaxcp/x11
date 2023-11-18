package com.github.moaxcp.x11client.protocol.xprint;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Printer implements XStruct, XprintObject {
  @NonNull
  private List<Byte> name;

  @NonNull
  private List<Byte> description;

  public static Printer readPrinter(X11Input in) throws IOException {
    Printer.PrinterBuilder javaBuilder = Printer.builder();
    int nameLen = in.readCard32();
    List<Byte> name = in.readChar((int) (Integer.toUnsignedLong(nameLen)));
    in.readPadAlign((int) (Integer.toUnsignedLong(nameLen)));
    int descLen = in.readCard32();
    List<Byte> description = in.readChar((int) (Integer.toUnsignedLong(descLen)));
    in.readPadAlign((int) (Integer.toUnsignedLong(descLen)));
    javaBuilder.name(name);
    javaBuilder.description(description);
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

  public static class PrinterBuilder {
    public int getSize() {
      return 8 + 1 * name.size() + XObject.getSizeForPadAlign(4, 1 * name.size()) + 1 * description.size() + XObject.getSizeForPadAlign(4, 1 * description.size());
    }
  }
}
