package com.github.moaxcp.x11.protocol;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class ProtocolPluginService {
  private final ServiceLoader<XProtocolPlugin> loader = ServiceLoader.load(XProtocolPlugin.class);
  private final List<XProtocolPlugin> activatedPlugins = new ArrayList<>();

  public ProtocolPluginService() {
    for(XProtocolPlugin plugin : loader) {
      if(plugin.getPluginName().equals("xproto")) {
        activatedPlugins.add(plugin);
        break;
      }
    }
  }

  public List<String> listLoadedPlugins() {
    List<String> result = new ArrayList<>();
    for(XProtocolPlugin plugin : loader) {
      result.add(plugin.getPluginName());
    }
    return Collections.unmodifiableList(result);
  }

  public Optional<XProtocolPlugin> loadedPlugin(String name) {
    for(XProtocolPlugin plugin : loader) {
      if(plugin.getPluginName().equals(name)) {
        return Optional.of(plugin);
      }
    }
    return Optional.empty();
  }

  public List<String> listActivatedPlugins() {
    return activatedPlugins.stream()
        .map(XProtocolPlugin::getPluginName)
        .collect(toList());
  }

  public boolean activatePlugin(String name, byte majorOpcode, byte firstEvent, byte firstError) {
    Optional<XProtocolPlugin> find = getActivatedPlugin(name);
    if(find.isPresent()) {
      throw new IllegalStateException("Plugin \"" + name + " \" is already activated.");
    }

    Optional<XProtocolPlugin> loaded = loadedPlugin(name);
    if(loaded.isPresent()) {
      XProtocolPlugin plugin = loaded.get();
      plugin.setMajorOpcode(majorOpcode);
      plugin.setFirstEvent(firstEvent);
      plugin.setFirstError(firstError);
      activatedPlugins.add(plugin);
      return true;
    }
    return false;
  }

  private Optional<XProtocolPlugin> getActivatedPlugin(String name) {
    return activatedPlugins.stream()
        .filter(p -> p.getExtensionXName().equals(Optional.of(name)))
        .findFirst();
  }

  public boolean activatedPlugin(String name) {
    return getActivatedPlugin(name).isPresent();
  }

  public byte majorOpcodeForRequest(XRequest request) {
    return activatedPlugins.stream()
        .filter(p -> p.supportedRequest(request))
        .findFirst()
        .map(p -> {
          if (p.getPluginName().equals("xproto")) { //xproto
            return request.getOpCode();
          }
          return p.getMajorOpcode();
        })
        .orElseThrow(() -> new UnsupportedOperationException("Plugin missing or not activated for request. Could not find majorOpcode for request: " + request));
  }

  public Optional<XProtocolPlugin> activePluginForMajorOpcode(byte majorOpcode) {
    return activatedPlugins.stream()
        .filter(p -> p.getMajorOpcode() == majorOpcode)
        .findFirst();
  }

  public Optional<XProtocolPlugin> activePluginFor(byte majorOpcode, byte minorOpcode) {
    return activatedPlugins.stream()
        .filter(p -> p.supportedRequest(majorOpcode, minorOpcode))
        .findFirst();
  }

  public Optional<XProtocolPlugin> activePluginForError(byte code) {
    return activatedPlugins.stream()
        .filter(p -> p.supportedError(code))
        .findFirst();
  }

  public boolean genericEventNumber(byte number) {
    return number == 35; //see xproto.xml
  }

  public Optional<XProtocolPlugin> activePluginForEvent(byte number) {
    return activatedPlugins.stream()
        .filter(p -> p.supportedEvent(number))
        .findFirst();
  }
}
