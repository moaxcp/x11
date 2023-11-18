package com.github.moaxcp.x11client.protocol.xv;

import com.github.moaxcp.x11client.protocol.TwoWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReplyFunction;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryBestSize implements TwoWayRequest<QueryBestSizeReply>, XvObject {
  public static final byte OPCODE = 12;

  private int port;

  private short vidW;

  private short vidH;

  private short drwW;

  private short drwH;

  private boolean motion;

  public XReplyFunction<QueryBestSizeReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryBestSizeReply.readQueryBestSizeReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryBestSize readQueryBestSize(X11Input in) throws IOException {
    QueryBestSize.QueryBestSizeBuilder javaBuilder = QueryBestSize.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int port = in.readCard32();
    short vidW = in.readCard16();
    short vidH = in.readCard16();
    short drwW = in.readCard16();
    short drwH = in.readCard16();
    boolean motion = in.readBool();
    byte[] pad9 = in.readPad(3);
    javaBuilder.port(port);
    javaBuilder.vidW(vidW);
    javaBuilder.vidH(vidH);
    javaBuilder.drwW(drwW);
    javaBuilder.drwH(drwH);
    javaBuilder.motion(motion);
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(port);
    out.writeCard16(vidW);
    out.writeCard16(vidH);
    out.writeCard16(drwW);
    out.writeCard16(drwH);
    out.writeBool(motion);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public static class QueryBestSizeBuilder {
    public int getSize() {
      return 20;
    }
  }
}
