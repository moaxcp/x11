package com.github.moaxcp.x11client.protocol.record;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class EnableContextReply implements XReply, RecordObject {
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

  @Override
  public int getSize() {
    return 32 + 1 * data.size();
  }

  public static class EnableContextReplyBuilder {
    public int getSize() {
      return 32 + 1 * data.size();
    }
  }
}
