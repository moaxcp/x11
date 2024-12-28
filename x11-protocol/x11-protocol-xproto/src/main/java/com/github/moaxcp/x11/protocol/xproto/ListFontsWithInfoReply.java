package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ListFontsWithInfoReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private short sequenceNumber;

  @NonNull
  private Charinfo minBounds;

  @NonNull
  private Charinfo maxBounds;

  private short minCharOrByte2;

  private short maxCharOrByte2;

  private short defaultChar;

  private byte drawDirection;

  private byte minByte1;

  private byte maxByte1;

  private boolean allCharsExist;

  private short fontAscent;

  private short fontDescent;

  private int repliesHint;

  @NonNull
  private List<Fontprop> properties;

  @NonNull
  private List<Byte> name;

  public static ListFontsWithInfoReply readListFontsWithInfoReply(byte nameLen,
      short sequenceNumber, X11Input in) throws IOException {
    ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder javaBuilder = ListFontsWithInfoReply.builder();
    int length = in.readCard32();
    Charinfo minBounds = Charinfo.readCharinfo(in);
    byte[] pad5 = in.readPad(4);
    Charinfo maxBounds = Charinfo.readCharinfo(in);
    byte[] pad7 = in.readPad(4);
    short minCharOrByte2 = in.readCard16();
    short maxCharOrByte2 = in.readCard16();
    short defaultChar = in.readCard16();
    short propertiesLen = in.readCard16();
    byte drawDirection = in.readByte();
    byte minByte1 = in.readCard8();
    byte maxByte1 = in.readCard8();
    boolean allCharsExist = in.readBool();
    short fontAscent = in.readInt16();
    short fontDescent = in.readInt16();
    int repliesHint = in.readCard32();
    List<Fontprop> properties = new ArrayList<>(Short.toUnsignedInt(propertiesLen));
    for(int i = 0; i < Short.toUnsignedInt(propertiesLen); i++) {
      properties.add(Fontprop.readFontprop(in));
    }
    List<Byte> name = in.readChar(Byte.toUnsignedInt(nameLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.minBounds(minBounds);
    javaBuilder.maxBounds(maxBounds);
    javaBuilder.minCharOrByte2(minCharOrByte2);
    javaBuilder.maxCharOrByte2(maxCharOrByte2);
    javaBuilder.defaultChar(defaultChar);
    javaBuilder.drawDirection(drawDirection);
    javaBuilder.minByte1(minByte1);
    javaBuilder.maxByte1(maxByte1);
    javaBuilder.allCharsExist(allCharsExist);
    javaBuilder.fontAscent(fontAscent);
    javaBuilder.fontDescent(fontDescent);
    javaBuilder.repliesHint(repliesHint);
    javaBuilder.properties(properties);
    javaBuilder.name(name);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    byte nameLen = (byte) name.size();
    out.writeCard8(nameLen);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    minBounds.write(out);
    out.writePad(4);
    maxBounds.write(out);
    out.writePad(4);
    out.writeCard16(minCharOrByte2);
    out.writeCard16(maxCharOrByte2);
    out.writeCard16(defaultChar);
    short propertiesLen = (short) properties.size();
    out.writeCard16(propertiesLen);
    out.writeByte(drawDirection);
    out.writeCard8(minByte1);
    out.writeCard8(maxByte1);
    out.writeBool(allCharsExist);
    out.writeInt16(fontAscent);
    out.writeInt16(fontDescent);
    out.writeCard32(repliesHint);
    for(Fontprop t : properties) {
      t.write(out);
    }
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 60 + XObject.sizeOf(properties) + 1 * name.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ListFontsWithInfoReplyBuilder {
    public ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder drawDirection(
        FontDraw drawDirection) {
      this.drawDirection = (byte) drawDirection.getValue();
      return this;
    }

    public ListFontsWithInfoReply.ListFontsWithInfoReplyBuilder drawDirection(byte drawDirection) {
      this.drawDirection = drawDirection;
      return this;
    }

    public int getSize() {
      return 60 + XObject.sizeOf(properties) + 1 * name.size();
    }
  }
}
