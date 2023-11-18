package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TranslateCoordinatesReply implements XReply, XprotoObject {
  private boolean sameScreen;

  private short sequenceNumber;

  private int child;

  private short dstX;

  private short dstY;

  public static TranslateCoordinatesReply readTranslateCoordinatesReply(byte sameScreen,
      short sequenceNumber, X11Input in) throws IOException {
    TranslateCoordinatesReply.TranslateCoordinatesReplyBuilder javaBuilder = TranslateCoordinatesReply.builder();
    int length = in.readCard32();
    int child = in.readCard32();
    short dstX = in.readInt16();
    short dstY = in.readInt16();
    in.readPad(16);
    javaBuilder.sameScreen(sameScreen > 0);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.child(child);
    javaBuilder.dstX(dstX);
    javaBuilder.dstY(dstY);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeBool(sameScreen);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(child);
    out.writeInt16(dstX);
    out.writeInt16(dstY);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public static class TranslateCoordinatesReplyBuilder {
    public int getSize() {
      return 16;
    }
  }
}
