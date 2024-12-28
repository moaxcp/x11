package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ListHostsReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";

  private byte mode;

  private short sequenceNumber;

  @NonNull
  private List<Host> hosts;

  public static ListHostsReply readListHostsReply(byte mode, short sequenceNumber, X11Input in)
      throws IOException {
    ListHostsReply.ListHostsReplyBuilder javaBuilder = ListHostsReply.builder();
    int length = in.readCard32();
    short hostsLen = in.readCard16();
    byte[] pad5 = in.readPad(22);
    List<Host> hosts = new ArrayList<>(Short.toUnsignedInt(hostsLen));
    for(int i = 0; i < Short.toUnsignedInt(hostsLen); i++) {
      hosts.add(Host.readHost(in));
    }
    javaBuilder.mode(mode);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.hosts(hosts);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeByte(mode);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    short hostsLen = (short) hosts.size();
    out.writeCard16(hostsLen);
    out.writePad(22);
    for(Host t : hosts) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(hosts);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class ListHostsReplyBuilder {
    public ListHostsReply.ListHostsReplyBuilder mode(AccessControl mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public ListHostsReply.ListHostsReplyBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 32 + XObject.sizeOf(hosts);
    }
  }
}
