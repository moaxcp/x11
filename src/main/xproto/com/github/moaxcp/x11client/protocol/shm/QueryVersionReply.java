package com.github.moaxcp.x11client.protocol.shm;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QueryVersionReply implements XReply, ShmObject {
  private boolean sharedPixmaps;

  private short sequenceNumber;

  private short majorVersion;

  private short minorVersion;

  private short uid;

  private short gid;

  private byte pixmapFormat;

  public static QueryVersionReply readQueryVersionReply(byte sharedPixmaps, short sequenceNumber,
      X11Input in) throws IOException {
    QueryVersionReply.QueryVersionReplyBuilder javaBuilder = QueryVersionReply.builder();
    int length = in.readCard32();
    short majorVersion = in.readCard16();
    short minorVersion = in.readCard16();
    short uid = in.readCard16();
    short gid = in.readCard16();
    byte pixmapFormat = in.readCard8();
    byte[] pad9 = in.readPad(15);
    javaBuilder.sharedPixmaps(sharedPixmaps > 0);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.majorVersion(majorVersion);
    javaBuilder.minorVersion(minorVersion);
    javaBuilder.uid(uid);
    javaBuilder.gid(gid);
    javaBuilder.pixmapFormat(pixmapFormat);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeBool(sharedPixmaps);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(majorVersion);
    out.writeCard16(minorVersion);
    out.writeCard16(uid);
    out.writeCard16(gid);
    out.writeCard8(pixmapFormat);
    out.writePad(15);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public static class QueryVersionReplyBuilder {
    public int getSize() {
      return 32;
    }
  }
}
