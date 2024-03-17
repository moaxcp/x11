package com.github.moaxcp.x11.protocol.xv;

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
public class EncodingInfo implements XStruct {
  public static final String PLUGIN_NAME = "xv";

  private int encoding;

  private short width;

  private short height;

  @NonNull
  private Rational rate;

  @NonNull
  private List<Byte> name;

  public static EncodingInfo readEncodingInfo(X11Input in) throws IOException {
    EncodingInfo.EncodingInfoBuilder javaBuilder = EncodingInfo.builder();
    int encoding = in.readCard32();
    short nameSize = in.readCard16();
    short width = in.readCard16();
    short height = in.readCard16();
    byte[] pad4 = in.readPad(2);
    Rational rate = Rational.readRational(in);
    List<Byte> name = in.readChar(Short.toUnsignedInt(nameSize));
    in.readPadAlign(Short.toUnsignedInt(nameSize));
    javaBuilder.encoding(encoding);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.rate(rate);
    javaBuilder.name(name);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(encoding);
    short nameSize = (short) name.size();
    out.writeCard16(nameSize);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writePad(2);
    rate.write(out);
    out.writeChar(name);
    out.writePadAlign(Short.toUnsignedInt(nameSize));
  }

  @Override
  public int getSize() {
    return 20 + 1 * name.size() + XObject.getSizeForPadAlign(4, 1 * name.size());
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class EncodingInfoBuilder {
    public int getSize() {
      return 20 + 1 * name.size() + XObject.getSizeForPadAlign(4, 1 * name.size());
    }
  }
}
