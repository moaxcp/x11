package com.github.moaxcp.x11client.protocol.xinput;

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
public class OpenDeviceReply implements XReply, XinputObject {
  private byte xiReplyType;

  private short sequenceNumber;

  @NonNull
  private List<InputClassInfo> classInfo;

  public static OpenDeviceReply readOpenDeviceReply(byte xiReplyType, short sequenceNumber,
      X11Input in) throws IOException {
    OpenDeviceReply.OpenDeviceReplyBuilder javaBuilder = OpenDeviceReply.builder();
    int length = in.readCard32();
    byte numClasses = in.readCard8();
    byte[] pad5 = in.readPad(23);
    List<InputClassInfo> classInfo = new ArrayList<>(Byte.toUnsignedInt(numClasses));
    for(int i = 0; i < Byte.toUnsignedInt(numClasses); i++) {
      classInfo.add(InputClassInfo.readInputClassInfo(in));
    }
    in.readPadAlign(Byte.toUnsignedInt(numClasses));
    javaBuilder.xiReplyType(xiReplyType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.classInfo(classInfo);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(xiReplyType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    byte numClasses = (byte) classInfo.size();
    out.writeCard8(numClasses);
    out.writePad(23);
    for(InputClassInfo t : classInfo) {
      t.write(out);
    }
    out.writePadAlign(Byte.toUnsignedInt(numClasses));
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(classInfo) + XObject.getSizeForPadAlign(4, XObject.sizeOf(classInfo));
  }

  public static class OpenDeviceReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(classInfo) + XObject.getSizeForPadAlign(4, XObject.sizeOf(classInfo));
    }
  }
}
