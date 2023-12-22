package com.github.moaxcp.x11.x11client;

import com.github.moaxcp.x11.protocol.*;
import com.github.moaxcp.x11.protocol.bigreq.Enable;
import com.github.moaxcp.x11.protocol.xproto.QueryExtension;
import com.github.moaxcp.x11.protocol.xproto.Setup;
import lombok.Getter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import static com.github.moaxcp.x11.protocol.Utilities.toX11Input;

public class XProtocolService {
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
  private final ProtocolPluginService pluginService;

  public XProtocolService(Setup setup, X11Input in, X11Output out) {
    this.in = in;
    this.out = out;
    this.setup = setup;
    maximumRequestLength = setup.getMaximumRequestLength();
    pluginService = new ProtocolPluginService();
    for(String name : pluginService.listLoadedPlugins()) {
      activatePlugin(name);
    }

    if(pluginService.activatedPlugin("BIG-REQUESTS")) {
      maximumRequestLength = Integer.toUnsignedLong(send(Enable.builder().build())
        .getMaximumRequestLength());
    }
  }

  public boolean activatePlugin(String name) {
    return pluginService.loadedPlugin(name)
        .flatMap(XProtocolPlugin::getExtensionXName)
        .map(xName -> QueryExtension.builder()
            .name(Utilities.toByteList(xName))
            .build())
        .map(this::send)
        .map(reply -> reply.isPresent() && pluginService.activatePlugin(name, reply.getMajorOpcode(), reply.getFirstEvent(), reply.getFirstError()))
        .orElse(false);
  }

  public boolean loadedPlugin(String name) {
    return pluginService.listLoadedPlugins().contains(name);
  }

  public boolean activatedPlugin(String name) {
    return pluginService.listActivatedPlugins().contains(name);
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
    try {
      request.write(getMajorOpcode(request), out);
    } catch(IOException e) {
      throw new X11ProtocolException("exception writing request \"" + request + "\"", e);
    }
    nextSequenceNumber++;
  }

  public byte getMajorOpcode(XRequest request) {
    return pluginService.majorOpcodeForRequest(request);
  }

  public <T extends XReply> T readReply(XReplyFunction<T> function) {
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
        throw new X11ProtocolException("exception when reading reply", e);
      }
    }
  }

  public <T extends XReply> T readReply(X11Input in, XReplyFunction<T> function) {
    try {
      byte responseCode = in.readByte();
      if (responseCode == 1) {
        byte field = in.readCard8();
        short sequenceNumber = in.readCard16();
        return function.get(field, sequenceNumber, in);
      } else {
        throw new X11ProtocolException("expected responseCode 1 but was " + responseCode);
      }
    } catch (IOException e) {
      throw new X11ProtocolException("exception when reading reply", e);
    }
  }

  private <T extends XError> T readError() throws IOException {
    return readError(in);
  }

  public <T extends XError> T readError(X11Input in) {
    try {
      byte code = in.readCard8();
      XProtocolPlugin plugin = pluginService.activePluginForError(code).orElseThrow(() -> new IllegalStateException(XProtocolPlugin.class.getSimpleName() + " not found for error code " + code));
      return plugin.readError(code, in);
    } catch(IOException e) {
      throw new X11ProtocolException("could not read error", e);
    }
  }

  private <T extends XEvent> T readEvent(byte responseCode) throws IOException {
    return readEvent(in, responseCode);
  }

  private <T extends XEvent> T readEvent(X11Input in, byte responseCode) throws IOException {
    boolean sentEvent = responseCode < 0;
    byte number = !sentEvent ? responseCode : (byte) (responseCode ^ (byte) 0b10000000);
    if(pluginService.genericEventNumber(number)) {
      byte extension = in.readCard8();
      Optional<XProtocolPlugin> pluginExtension = pluginService.activePluginForMajorOpcode(extension);
      if(!pluginExtension.isPresent()) {
        throw new IllegalStateException("Could not find plugin for generic event with major-opcode of " + extension);
      }
      XProtocolPlugin plugin = pluginExtension.get();

      short sequenceNumber = in.readCard16();
      int length = in.readCard32();
      short eventType = in.readCard16();
      return plugin.readGenericEvent(sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    XProtocolPlugin plugin = pluginService.activePluginForEvent(number)
        .orElseThrow(() -> new IllegalStateException(XProtocolPlugin.class.getSimpleName() + " not found for number " + number));
    return plugin.readEvent(number, sentEvent, in);
  }

  public <T extends XEvent> T getNextEvent() {
    if(!events.isEmpty()) {
      return (T) events.poll();
    }
    flush();
    try {
      byte responseCode = in.readByte();
      if(responseCode == 0) {
        throw new X11ErrorException(readError());
      }
      if(responseCode == 1) {
        throw new X11ProtocolException("reply not expected when reading events");
      }
      return readEvent(responseCode);
    } catch(IOException e) {
      throw new X11ProtocolException("exception when reading event", e);
    }
  }

  public void flush() {
    while(!requests.isEmpty()) {
      XRequest request = requests.poll();
      actuallySend(request);
    }
  }

  public void discard() {
    events.clear();
  }

  public <T extends XRequest> T readRequest(X11Input in) throws IOException {
    byte majorOpcode = in.readByte();
    byte minorOpcode = in.peekByte();

    Optional<XProtocolPlugin> pluginOptional = pluginService.activePluginFor(majorOpcode, minorOpcode);
    if (!pluginOptional.isPresent()) {
      throw new IllegalStateException("Could not find plugin for request with major opcode of " + majorOpcode);
    }
    XProtocolPlugin plugin = pluginOptional.get();
    return plugin.readRequest(majorOpcode, minorOpcode, in);
  }

  public <T extends XEvent> T readEvent(X11Input in) {
    try {
      byte responseCode = in.readByte();
      return readEvent(in, responseCode);
    } catch (IOException e) {
      throw new X11ProtocolException("exception when reading event", e);
    }
  }

  public <T extends XError> T readError(List<Byte> bytes) {
    X11Input in = toX11Input(bytes);
    try {
      byte responseCode = in.readByte();
      if (responseCode != 0) {
        throw new IllegalArgumentException("first by must be 0 for errors was " + responseCode);
      }
      return readError(in);
    } catch (IOException e) {
      throw new X11ProtocolException("exception when reading error", e);
    }
  }
}
