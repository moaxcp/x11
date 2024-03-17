package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import com.github.moaxcp.x11.protocol.render.SubPixel;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetOutputInfoReply implements XReply {
  public static final String PLUGIN_NAME = "randr";

  private byte status;

  private short sequenceNumber;

  private int timestamp;

  private int crtc;

  private int mmWidth;

  private int mmHeight;

  private byte connection;

  private byte subpixelOrder;

  private short numPreferred;

  @NonNull
  private List<Integer> crtcs;

  @NonNull
  private List<Integer> modes;

  @NonNull
  private List<Integer> clones;

  @NonNull
  private List<Byte> name;

  public static GetOutputInfoReply readGetOutputInfoReply(byte status, short sequenceNumber,
      X11Input in) throws IOException {
    GetOutputInfoReply.GetOutputInfoReplyBuilder javaBuilder = GetOutputInfoReply.builder();
    int length = in.readCard32();
    int timestamp = in.readCard32();
    int crtc = in.readCard32();
    int mmWidth = in.readCard32();
    int mmHeight = in.readCard32();
    byte connection = in.readCard8();
    byte subpixelOrder = in.readCard8();
    short numCrtcs = in.readCard16();
    short numModes = in.readCard16();
    short numPreferred = in.readCard16();
    short numClones = in.readCard16();
    short nameLen = in.readCard16();
    List<Integer> crtcs = in.readCard32(Short.toUnsignedInt(numCrtcs));
    List<Integer> modes = in.readCard32(Short.toUnsignedInt(numModes));
    List<Integer> clones = in.readCard32(Short.toUnsignedInt(numClones));
    List<Byte> name = in.readByte(Short.toUnsignedInt(nameLen));
    javaBuilder.status(status);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timestamp(timestamp);
    javaBuilder.crtc(crtc);
    javaBuilder.mmWidth(mmWidth);
    javaBuilder.mmHeight(mmHeight);
    javaBuilder.connection(connection);
    javaBuilder.subpixelOrder(subpixelOrder);
    javaBuilder.numPreferred(numPreferred);
    javaBuilder.crtcs(crtcs);
    javaBuilder.modes(modes);
    javaBuilder.clones(clones);
    javaBuilder.name(name);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(status);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(timestamp);
    out.writeCard32(crtc);
    out.writeCard32(mmWidth);
    out.writeCard32(mmHeight);
    out.writeCard8(connection);
    out.writeCard8(subpixelOrder);
    short numCrtcs = (short) crtcs.size();
    out.writeCard16(numCrtcs);
    short numModes = (short) modes.size();
    out.writeCard16(numModes);
    out.writeCard16(numPreferred);
    short numClones = (short) clones.size();
    out.writeCard16(numClones);
    short nameLen = (short) name.size();
    out.writeCard16(nameLen);
    out.writeCard32(crtcs);
    out.writeCard32(modes);
    out.writeCard32(clones);
    out.writeByte(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 36 + 4 * crtcs.size() + 4 * modes.size() + 4 * clones.size() + 1 * name.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetOutputInfoReplyBuilder {
    public GetOutputInfoReply.GetOutputInfoReplyBuilder status(SetConfig status) {
      this.status = (byte) status.getValue();
      return this;
    }

    public GetOutputInfoReply.GetOutputInfoReplyBuilder status(byte status) {
      this.status = status;
      return this;
    }

    public GetOutputInfoReply.GetOutputInfoReplyBuilder connection(Connection connection) {
      this.connection = (byte) connection.getValue();
      return this;
    }

    public GetOutputInfoReply.GetOutputInfoReplyBuilder connection(byte connection) {
      this.connection = connection;
      return this;
    }

    public GetOutputInfoReply.GetOutputInfoReplyBuilder subpixelOrder(SubPixel subpixelOrder) {
      this.subpixelOrder = (byte) subpixelOrder.getValue();
      return this;
    }

    public GetOutputInfoReply.GetOutputInfoReplyBuilder subpixelOrder(byte subpixelOrder) {
      this.subpixelOrder = subpixelOrder;
      return this;
    }

    public int getSize() {
      return 36 + 4 * crtcs.size() + 4 * modes.size() + 4 * clones.size() + 1 * name.size();
    }
  }
}
