package com.github.moaxcp.x11client.protocol;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;

public class XProtocolService {
  private final ServiceLoader<XProtocolReader> loader = ServiceLoader.load(XProtocolReader.class);
  private final Map<Short, XReplyFunction> replySequences = new HashMap<>();
  private final X11Input in;
  private final X11Output out;
  private int nextSequenceNumber = 1;

  public XProtocolService(X11Input in, X11Output out) {
    this.in = in;
    this.out = out;
  }

  public int send(XRequest request) throws IOException {
    request.write(out);
    Optional<XReplyFunction> function = request.getReplyFunction();
    //nextSequenceNumber is truncated to match reply sequence numbers
    function.ifPresent(f -> replySequences.put((short) nextSequenceNumber, f));
    return nextSequenceNumber++;
  }

  public XResponse read() throws IOException {
    byte responseCode = in.readByte();
    if(responseCode == 0) {
      return readError();
    } else if(responseCode == 1) {
      return readReply();
    } else {
      return readEvent(responseCode);
    }
  }

  private XError readError() throws IOException {
    byte code = in.readCard8();
    for(XProtocolReader reader : loader) {
      if(reader.supportedError(code)) {
        return reader.readError(code, in);
      }
    }
    throw new IllegalStateException(XProtocolReader.class.getSimpleName() + " not found for error code " + code);
  }

  private XEvent readEvent(byte responseCode) throws IOException {
    boolean sentEvent = responseCode < 0;
    byte number = responseCode;
    if(sentEvent) {
      number = (byte) (responseCode ^ (byte) 0b10000000);
    }
    for(XProtocolReader reader : loader) {
      if(reader.supportedEvent(number)) {
        return reader.readEvent(number, sentEvent, in);
      }
    }
    throw new IllegalStateException(XProtocolReader.class.getSimpleName() + " not found for number " + number);

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
