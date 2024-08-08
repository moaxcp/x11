package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Value
@Builder
public class QueryClientsReply implements XReply {
  public static final String PLUGIN_NAME = "res";

  private short sequenceNumber;

  @NonNull
  private ImmutableList<Client> clients;

  public static QueryClientsReply readQueryClientsReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    QueryClientsReply.QueryClientsReplyBuilder javaBuilder = QueryClientsReply.builder();
    int length = in.readCard32();
    int numClients = in.readCard32();
    byte[] pad5 = in.readPad(20);
    MutableList<Client> clients = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numClients)));
    for(int i = 0; i < Integer.toUnsignedLong(numClients); i++) {
      clients.add(Client.readClient(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.clients(clients.toImmutable());
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    int numClients = clients.size();
    out.writeCard32(numClients);
    out.writePad(20);
    for(Client t : clients) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(clients);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class QueryClientsReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(clients);
    }
  }
}
