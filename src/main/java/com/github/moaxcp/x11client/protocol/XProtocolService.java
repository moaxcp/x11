package com.github.moaxcp.x11client.protocol;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Supplier;

public class XProtocolService {
  private final ServiceLoader<XProtocolReader> loader = ServiceLoader.load(XProtocolReader.class);
  private final Map<Integer, Supplier<XReply>> replySequences = new HashMap<>();
  private final X11Input in;
  private final X11Output out;

  public XProtocolService(X11Input in, X11Output out) {
    this.in = in;
    this.out = out;
  }

  public XError readError() throws IOException {
    byte code = in.readCard8();
    for(XProtocolReader reader : loader) {
      if(reader.supportedError(code)) {
        return reader.readError(code, in);
      }
    }
    throw new IllegalStateException(XProtocolReader.class.getSimpleName() + " not found for error code " + code);
  }

  public XEvent readEvent() {
    return null;
  }

  public XReply readReply(int sequenceNumber) {
    return null;
  }

  public Optional<XResponse> read() throws IOException {
    byte responseCode = in.readByte();
    if(responseCode == 0) {
      return Optional.of(readError());
    } else if(responseCode == 1) {

    } else if(responseCode > 1) {
      return Optional.of(readEvent());
    }
    return Optional.empty();

  }
}
