package com.github.moaxcp.x11.protocol.record;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class EnableContextReply implements XReply {
  public static final String PLUGIN_NAME = "record";

  private byte category;

  private short sequenceNumber;

  private byte elementHeader;

  private boolean clientSwapped;

  private int xidBase;

  private int serverTime;

  private int recSequenceNum;

  @NonNull
  private List<Byte> data;

  public static EnableContextReply readEnableContextReply(byte category, short sequenceNumber,
      X11Input in) throws IOException {
    EnableContextReply.EnableContextReplyBuilder javaBuilder = EnableContextReply.builder();
    int length = in.readCard32();
    byte elementHeader = in.readCard8();
    boolean clientSwapped = in.readBool();
    byte[] pad6 = in.readPad(2);
    int xidBase = in.readCard32();
    int serverTime = in.readCard32();
    int recSequenceNum = in.readCard32();
    byte[] pad10 = in.readPad(8);
    List<Byte> data = in.readByte((int) (Integer.toUnsignedLong(length) * 4));
    javaBuilder.category(category);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.elementHeader(elementHeader);
    javaBuilder.clientSwapped(clientSwapped);
    javaBuilder.xidBase(xidBase);
    javaBuilder.serverTime(serverTime);
    javaBuilder.recSequenceNum(recSequenceNum);
    javaBuilder.data(data);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(category);
    out.writeCard16(sequenceNumber);
    int length = data.size();
    out.writeCard32(getLength());
    out.writeCard8(elementHeader);
    out.writeBool(clientSwapped);
    out.writePad(2);
    out.writeCard32(xidBase);
    out.writeCard32(serverTime);
    out.writeCard32(recSequenceNum);
    out.writePad(8);
    out.writeByte(data);
    out.writePadAlign(getSize());
  }

  public boolean isElementHeaderEnabled(@NonNull HType... maskEnums) {
    for(HType m : maskEnums) {
      if(!m.isEnabled(elementHeader)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32 + 1 * data.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class EnableContextReplyBuilder {
    public EnableContextReply.EnableContextReplyBuilder category(Category category) {
      this.category = (byte) category.getValue();
      return this;
    }

    public EnableContextReply.EnableContextReplyBuilder category(byte category) {
      this.category = category;
      return this;
    }

    public boolean isElementHeaderEnabled(@NonNull HType... maskEnums) {
      for(HType m : maskEnums) {
        if(!m.isEnabled(elementHeader)) {
          return false;
        }
      }
      return true;
    }

    public EnableContextReply.EnableContextReplyBuilder elementHeaderEnable(HType... maskEnums) {
      for(HType m : maskEnums) {
        elementHeader((byte) m.enableFor(elementHeader));
      }
      return this;
    }

    public EnableContextReply.EnableContextReplyBuilder elementHeaderDisable(HType... maskEnums) {
      for(HType m : maskEnums) {
        elementHeader((byte) m.disableFor(elementHeader));
      }
      return this;
    }

    public int getSize() {
      return 32 + 1 * data.size();
    }
  }
}
