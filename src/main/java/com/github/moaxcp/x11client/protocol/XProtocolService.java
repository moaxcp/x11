package com.github.moaxcp.x11client.protocol;

import com.github.moaxcp.x11client.X11ClientException;
import com.github.moaxcp.x11client.X11ErrorException;
import com.github.moaxcp.x11client.protocol.bigreq.EnableRequest;
import com.github.moaxcp.x11client.protocol.xproto.QueryExtensionReply;
import com.github.moaxcp.x11client.protocol.xproto.QueryExtensionRequest;
import com.github.moaxcp.x11client.protocol.xproto.SetupStruct;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ServiceLoader;
import lombok.Getter;

import static com.github.moaxcp.x11client.Utilities.stringToByteList;

public final class XProtocolService {
  private final ServiceLoader<XProtocolPlugin> loader = ServiceLoader.load(XProtocolPlugin.class);
  private final X11Input in;
  private final X11Output out;
  @Getter
  private int nextSequenceNumber = 1;
  @Getter
  private int maximumRequestLength;
  private final Queue<OneWayRequest> requests = new LinkedList<>();
  private final Queue<XEvent> events = new LinkedList<>();

  public XProtocolService(SetupStruct setup, X11Input in, X11Output out) throws IOException {
    this.in = in;
    this.out = out;
    maximumRequestLength = setup.getMaximumRequestLength();
    for(XProtocolPlugin plugin : loader) {
      String name = plugin.getName();
      QueryExtensionRequest request = QueryExtensionRequest.builder()
        .nameLen((short) name.length())
        .name(stringToByteList(name))
        .build();
      QueryExtensionReply reply = send(request);
      byte offset = reply.getMajorOpcode();
      plugin.setOffset(offset);
    }
    if(hasPlugin("BIG-REQUESTS")) {
      maximumRequestLength = send(EnableRequest.builder().build())
        .getMaximumRequestLength();
    }
  }

  public boolean hasPlugin(String name) {
    for(XProtocolPlugin plugin : loader) {
      if(plugin.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  public <T extends XReply> T send(TwoWayRequest<T> request) {
    flush();
    actuallySend(request);
    return readReply(request.getReplyFunction());
  }

  public void send(OneWayRequest request) {
    requests.add(request);
  }

  private void actuallySend(XRequest request) {
    boolean sent = false;
    for(XProtocolPlugin plugin : loader) {
      if(plugin.supportedRequest(request)) {
        try {
          request.write(plugin.getOffset(), out);
        } catch(IOException e) {
          throw new X11ClientException("exception when writing with plugin \"" + plugin.getName() + "\"", e);
        }
        sent = true;
        break;
      }
    }
    if(!sent) {
      throw new UnsupportedOperationException(String.format("could not find plugin for request \"%s\"", request));
    }
    nextSequenceNumber++;
  }

  private <T extends XReply> T readReply(XReplyFunction<T> function) {
    while(true) {
      try {
        byte responseCode = in.readByte();
        if (responseCode == 0) {
          throw new X11ErrorException(readError());
        } else if (responseCode == 1) {
          byte field = in.readCard8();
          short sequenceNumber = in.readCard16();
          return function.get(field, sequenceNumber, in);
        } else {
          events.add(readEvent(responseCode));
        }
      } catch (IOException e) {
        throw new X11ClientException("exception when reading reply", e);
      }
    }
  }

  private XError readError() throws IOException {
    byte code = in.readCard8();
    for(XProtocolPlugin reader : loader) {
      if(reader.supportedError(code)) {
        return reader.readError(code, in);
      }
    }
    throw new IllegalStateException(XProtocolPlugin.class.getSimpleName() + " not found for error code " + code);
  }

  private XEvent readEvent(byte responseCode) throws IOException {
    boolean sentEvent = responseCode < 0;
    byte number = responseCode;
    if(sentEvent) {
      number = (byte) (responseCode ^ (byte) 0b10000000);
    }
    for(XProtocolPlugin reader : loader) {
      if(reader.supportedEvent(number)) {
        return reader.readEvent(number, sentEvent, in);
      }
    }
    throw new IllegalStateException(XProtocolPlugin.class.getSimpleName() + " not found for number " + number);
  }

  public XEvent getNextEvent() {
    if(events.size() > 0) {
      return events.poll();
    }
    flush();
    try {
      byte responseCode = in.readByte();
      if(responseCode == 0) {
        throw new X11ErrorException(readError());
      }
      if(responseCode == 1) {
        throw new X11ClientException("reply not expected when reading events");
      }
      return readEvent(responseCode);
    } catch(IOException e) {
      throw new X11ClientException("exception when reading event", e);
    }
  }

  public void flush() {
    while(requests.size() != 0) {
      XRequest request = requests.poll();
      actuallySend(request);
    }
  }
}
