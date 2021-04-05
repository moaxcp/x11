package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.*;
import com.github.moaxcp.x11client.protocol.bigreq.Enable;
import com.github.moaxcp.x11client.protocol.xproto.*;
import java.io.IOException;
import java.util.*;
import lombok.Getter;

import static com.github.moaxcp.x11client.protocol.Utilities.stringToByteList;

class XProtocolService {
  private final ServiceLoader<XProtocolPlugin> loader = ServiceLoader.load(XProtocolPlugin.class);
  private final X11Input in;
  private final X11Output out;
  @Getter
  private final Setup setup;
  @Getter
  private int nextSequenceNumber = 1;
  @Getter
  private long maximumRequestLength;
  private final Queue<OneWayRequest> requests = new LinkedList<>();
  private final Queue<XEvent> events = new LinkedList<>();
  private final List<XProtocolPlugin> activatedPlugins = new ArrayList<>();

  XProtocolService(Setup setup, X11Input in, X11Output out) {
    this.in = in;
    this.out = out;
    this.setup = setup;
    maximumRequestLength = setup.getMaximumRequestLength();
    for(XProtocolPlugin plugin : loader) {
      if(plugin instanceof XprotoPlugin) {
        activatedPlugins.add(plugin);
        continue;
      }
      String name = plugin.getName();
      QueryExtension request = QueryExtension.builder()
        .name(stringToByteList(name))
        .build();
      QueryExtensionReply reply = send(request);
      if(reply.isPresent()) {
        plugin.setMajorOpcode(reply.getMajorOpcode());
        plugin.setFirstEvent(reply.getFirstEvent());
        plugin.setFirstError(reply.getFirstError());
        activatedPlugins.add(plugin);
      }
    }

    if(loadedPlugin("BIG-REQUESTS")) {
      maximumRequestLength = Integer.toUnsignedLong(send(Enable.builder().build())
        .getMaximumRequestLength());
    }
  }

  public boolean loadedPlugin(String name) {
    for(XProtocolPlugin plugin : loader) {
      if(plugin.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  public boolean activatedPlugin(String name) {
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
    for(XProtocolPlugin plugin : activatedPlugins) {
      if(plugin.supportedRequest(request)) {
        try {
          request.write(plugin.getMajorOpcode(), out);
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
    for(XProtocolPlugin reader : activatedPlugins) {
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
    for(XProtocolPlugin reader : activatedPlugins) {
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

  public void discard() {
    events.clear();
  }
}
