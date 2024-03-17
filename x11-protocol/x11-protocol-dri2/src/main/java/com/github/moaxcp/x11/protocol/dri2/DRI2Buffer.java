package com.github.moaxcp.x11.protocol.dri2;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DRI2Buffer implements XStruct {
  public static final String PLUGIN_NAME = "dri2";

  private int attachment;

  private int name;

  private int pitch;

  private int cpp;

  private int flags;

  public static DRI2Buffer readDRI2Buffer(X11Input in) throws IOException {
    DRI2Buffer.DRI2BufferBuilder javaBuilder = DRI2Buffer.builder();
    int attachment = in.readCard32();
    int name = in.readCard32();
    int pitch = in.readCard32();
    int cpp = in.readCard32();
    int flags = in.readCard32();
    javaBuilder.attachment(attachment);
    javaBuilder.name(name);
    javaBuilder.pitch(pitch);
    javaBuilder.cpp(cpp);
    javaBuilder.flags(flags);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(attachment);
    out.writeCard32(name);
    out.writeCard32(pitch);
    out.writeCard32(cpp);
    out.writeCard32(flags);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DRI2BufferBuilder {
    public DRI2Buffer.DRI2BufferBuilder attachment(Attachment attachment) {
      this.attachment = (int) attachment.getValue();
      return this;
    }

    public DRI2Buffer.DRI2BufferBuilder attachment(int attachment) {
      this.attachment = attachment;
      return this;
    }

    public int getSize() {
      return 20;
    }
  }
}
