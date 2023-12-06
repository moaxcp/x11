package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.*;
import com.github.moaxcp.x11client.protocol.bigreq.Enable;
import com.github.moaxcp.x11client.protocol.xproto.QueryExtension;
import com.github.moaxcp.x11client.protocol.xproto.QueryExtensionReply;
import com.github.moaxcp.x11client.protocol.xproto.Setup;
import lombok.Getter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

class XProtocolService {
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

  XProtocolService(Setup setup, X11Input in, X11Output out) {
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
    QueryExtension request = QueryExtension.builder()
      .name(Utilities.toByteList(name))
      .build();
    QueryExtensionReply reply = send(request);
    if(reply.isPresent()) {
      return pluginService.activatePlugin(name, reply.getMajorOpcode(), reply.getFirstEvent(), reply.getFirstError());
    }
    return false;
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
      request.write(pluginService.majorOpcodeForRequest(request), out);
    } catch(IOException e) {
      throw new X11ClientException("exception writing request \"" + request + "\"", e);
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
    XProtocolPlugin plugin = pluginService.activePluginForError(code).orElseThrow(() -> new IllegalStateException(XProtocolPlugin.class.getSimpleName() + " not found for error code " + code));
    return plugin.readError(code, in);
  }

  private XEvent readEvent(byte responseCode) throws IOException {
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
    XProtocolPlugin plugin = pluginService.activePluginForEvent(number).orElseThrow(() -> new IllegalStateException(XProtocolPlugin.class.getSimpleName() + " not found for number " + number));
    return plugin.readEvent(number, sentEvent, in);
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
