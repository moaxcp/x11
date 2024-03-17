package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetPanningReply implements XReply {
  public static final String PLUGIN_NAME = "randr";

  private byte status;

  private short sequenceNumber;

  private int timestamp;

  private short left;

  private short top;

  private short width;

  private short height;

  private short trackLeft;

  private short trackTop;

  private short trackWidth;

  private short trackHeight;

  private short borderLeft;

  private short borderTop;

  private short borderRight;

  private short borderBottom;

  public static GetPanningReply readGetPanningReply(byte status, short sequenceNumber, X11Input in)
      throws IOException {
    GetPanningReply.GetPanningReplyBuilder javaBuilder = GetPanningReply.builder();
    int length = in.readCard32();
    int timestamp = in.readCard32();
    short left = in.readCard16();
    short top = in.readCard16();
    short width = in.readCard16();
    short height = in.readCard16();
    short trackLeft = in.readCard16();
    short trackTop = in.readCard16();
    short trackWidth = in.readCard16();
    short trackHeight = in.readCard16();
    short borderLeft = in.readInt16();
    short borderTop = in.readInt16();
    short borderRight = in.readInt16();
    short borderBottom = in.readInt16();
    javaBuilder.status(status);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timestamp(timestamp);
    javaBuilder.left(left);
    javaBuilder.top(top);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.trackLeft(trackLeft);
    javaBuilder.trackTop(trackTop);
    javaBuilder.trackWidth(trackWidth);
    javaBuilder.trackHeight(trackHeight);
    javaBuilder.borderLeft(borderLeft);
    javaBuilder.borderTop(borderTop);
    javaBuilder.borderRight(borderRight);
    javaBuilder.borderBottom(borderBottom);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(status);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(timestamp);
    out.writeCard16(left);
    out.writeCard16(top);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(trackLeft);
    out.writeCard16(trackTop);
    out.writeCard16(trackWidth);
    out.writeCard16(trackHeight);
    out.writeInt16(borderLeft);
    out.writeInt16(borderTop);
    out.writeInt16(borderRight);
    out.writeInt16(borderBottom);
  }

  @Override
  public int getSize() {
    return 36;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetPanningReplyBuilder {
    public GetPanningReply.GetPanningReplyBuilder status(SetConfig status) {
      this.status = (byte) status.getValue();
      return this;
    }

    public GetPanningReply.GetPanningReplyBuilder status(byte status) {
      this.status = status;
      return this;
    }

    public int getSize() {
      return 36;
    }
  }
}
