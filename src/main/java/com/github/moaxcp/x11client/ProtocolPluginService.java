package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.XProtocolPlugin;
import com.github.moaxcp.x11client.protocol.XRequest;
import com.github.moaxcp.x11client.protocol.xproto.XprotoPlugin;

import java.util.*;

import static java.util.stream.Collectors.toList;

class ProtocolPluginService {
  private final ServiceLoader<XProtocolPlugin> loader = ServiceLoader.load(XProtocolPlugin.class);
  private final List<XProtocolPlugin> activatedPlugins = new ArrayList<>();

  ProtocolPluginService() {
    for(XProtocolPlugin plugin : loader) {
      if(plugin instanceof XprotoPlugin) {
        activatedPlugins.add(plugin);
        break;
      }
    }
  }

  List<String> listLoadedPlugins() {
    List<String> result = new ArrayList<>();
    for(XProtocolPlugin plugin : loader) {
      result.add(plugin.getName());
    }
    return Collections.unmodifiableList(result);
  }

  private Optional<XProtocolPlugin> loadedPlugin(String name) {
    for(XProtocolPlugin plugin : loader) {
      if(plugin.getName().equals(name)) {
        return Optional.of(plugin);
      }
    }
    return Optional.empty();
  }

  List<String> listActivatedPlugins() {
    return activatedPlugins.stream()
        .map(XProtocolPlugin::getName)
        .collect(toList());
  }

  boolean activatePlugin(String name, byte majorOpcode, byte firstEvent, byte firstError) {
    Optional<XProtocolPlugin> find = getActivatedPlugin(name);
    if(find.isPresent()) {
      throw new IllegalStateException("Plugin \"" + name + " \" is already activated.");
    }

    Optional<XProtocolPlugin> loaded = loadedPlugin(name);
    if(loaded.isPresent()) {
      XProtocolPlugin plugin = loaded.get();
      if(plugin.getMajorVersion() != majorOpcode) {
        return false; //client must match server version
      }
      plugin.setFirstEvent(firstEvent);
      plugin.setFirstError(firstError);
      activatedPlugins.add(plugin);
      return true;
    }
    return false;
  }

  private Optional<XProtocolPlugin> getActivatedPlugin(String name) {
    return activatedPlugins.stream()
        .filter(p -> p.getName().equals(name))
        .findFirst();
  }

  boolean activatedPlugin(String name) {
    return getActivatedPlugin(name).isPresent();
  }

  byte majorVersionForRequest(XRequest request) {
    return activatedPlugins.stream()
        .filter(p -> p.supportedRequest(request))
        .findFirst()
        .map(XProtocolPlugin::getMajorVersion)
        .orElseThrow(() -> new UnsupportedOperationException("Plugin missing or not activated for request. Could not find majorOpcode for request: " + request));
  }

  Optional<XProtocolPlugin> activePluginForMajorOpcode(byte majorOpcode) {
    return activatedPlugins.stream()
        .filter(p -> p.getMajorVersion() == majorOpcode)
        .findFirst();
  }

  Optional<XProtocolPlugin> activePluginForError(byte code) {
    return activatedPlugins.stream()
        .filter(p -> p.supportedError(code))
        .findFirst();
  }

  boolean genericEventNumber(byte number) {
    return number == 35; //see xproto.xml
  }

  Optional<XProtocolPlugin> activePluginForEvent(byte number) {
    return activatedPlugins.stream()
        .filter(p -> p.supportedEvent(number))
        .findFirst();
  }
}
