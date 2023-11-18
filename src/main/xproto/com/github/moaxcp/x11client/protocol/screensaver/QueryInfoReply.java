package com.github.moaxcp.x11client.protocol.screensaver;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryInfoReply implements XReply, ScreensaverObject {
  private byte state;

  private short sequenceNumber;

  private int saverWindow;

  private int msUntilServer;

  private int msSinceUserInput;

  private int eventMask;

  private byte kind;

  public static QueryInfoReply readQueryInfoReply(byte state, short sequenceNumber, X11Input in)
      throws IOException {
    QueryInfoReply.QueryInfoReplyBuilder javaBuilder = QueryInfoReply.builder();
    int length = in.readCard32();
    int saverWindow = in.readCard32();
    int msUntilServer = in.readCard32();
    int msSinceUserInput = in.readCard32();
    int eventMask = in.readCard32();
    byte kind = in.readByte();
    byte[] pad9 = in.readPad(7);
    javaBuilder.state(state);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.saverWindow(saverWindow);
    javaBuilder.msUntilServer(msUntilServer);
    javaBuilder.msSinceUserInput(msSinceUserInput);
    javaBuilder.eventMask(eventMask);
    javaBuilder.kind(kind);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(state);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(saverWindow);
    out.writeCard32(msUntilServer);
    out.writeCard32(msSinceUserInput);
    out.writeCard32(eventMask);
    out.writeByte(kind);
    out.writePad(7);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class QueryInfoReplyBuilder {
    public QueryInfoReply.QueryInfoReplyBuilder kind(Kind kind) {
      this.kind = (byte) kind.getValue();
      return this;
    }

    public QueryInfoReply.QueryInfoReplyBuilder kind(byte kind) {
      this.kind = kind;
      return this;
    }

    public int getSize() {
      return 32;
    }
  }
}
