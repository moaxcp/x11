package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;

public class XProtocolService {
  private final ServiceLoader<XProtocolPlugin> loader = ServiceLoader.load(XProtocolPlugin.class);
  private final Map<Short, XReplyFunction> replySequences = new HashMap<>();
  private final X11Input in;
  private final X11Output out;
  private int nextSequenceNumber = 1;

  XProtocolService(X11Input in, X11Output out) throws IOException {
    this.in = in;
    this.out = out;
    for(XProtocolPlugin plugin : loader) {
      plugin.setupOffset(this);
    }
  }

  public int send(XRequest request) throws IOException {
    boolean sent = false;
    for(XProtocolPlugin plugin : loader) {
      if(plugin.supportedRequest(request)) {
        request.write(plugin.getOffset(), out);
        sent = true;
        break;
      }
    }
    if(!sent) {
      throw new UnsupportedOperationException(String.format("could not find plugin for request \"%s\"", request));
    }
    Optional<XReplyFunction> function = request.getReplyFunction();
    //nextSequenceNumber is truncated to match reply sequence numbers
    function.ifPresent(f -> replySequences.put((short) nextSequenceNumber, f));
    return nextSequenceNumber++;
  }

  public <T extends XResponse> T read() throws IOException {
    byte responseCode = in.readByte();
    if(responseCode == 0) {
      return (T) readError();
    } else if(responseCode == 1) {
      return (T) readReply();
    } else {
      return (T) readEvent(responseCode);
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

  private XReply readReply() throws IOException {
    byte field = in.readCard8();
    short sequenceNumber = in.readCard16();
    XReplyFunction function = replySequences.get(sequenceNumber);
    if(function == null) {
      throw new IllegalStateException("could not find function for sequenceNumber " + sequenceNumber);
    }
    XReply reply = function.get(field, sequenceNumber, in);
    replySequences.remove(sequenceNumber);
    return reply;
  }
}
