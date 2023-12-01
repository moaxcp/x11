package com.github.moaxcp.x11client.protocol.res;

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
public class QueryClientsReply implements XReply, ResObject {
  private short sequenceNumber;

  @NonNull
  private List<Client> clients;

  public static QueryClientsReply readQueryClientsReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    QueryClientsReply.QueryClientsReplyBuilder javaBuilder = QueryClientsReply.builder();
    int length = in.readCard32();
    int numClients = in.readCard32();
    byte[] pad5 = in.readPad(20);
    List<Client> clients = new ArrayList<>((int) (Integer.toUnsignedLong(numClients)));
    for(int i = 0; i < Integer.toUnsignedLong(numClients); i++) {
      clients.add(Client.readClient(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.clients(clients);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
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

  public static class QueryClientsReplyBuilder {
    public int getSize() {
      return 32 + XObject.sizeOf(clients);
    }
  }
}
