package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.AtomValue;
import com.github.moaxcp.x11client.protocol.xproto.Atom;
import com.github.moaxcp.x11client.protocol.xproto.GetAtomName;
import com.github.moaxcp.x11client.protocol.xproto.GetAtomNameReply;
import com.github.moaxcp.x11client.protocol.xproto.InternAtom;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.github.moaxcp.x11client.protocol.Utilities.byteListToString;
import static com.github.moaxcp.x11client.protocol.Utilities.stringToByteList;

public class AtomService {
  private final XProtocolService protocolService;
  private Map<Integer,AtomValue> atomIds = new HashMap<>();
  private Map<String, AtomValue> atomNames = new HashMap<>();
  public AtomService(XProtocolService protocolService) {
    this.protocolService = protocolService;
    for(Atom atom : Atom.values()) {
      add(new AtomValue(atom.getValue(), atom.toString()));
    }
  }

  private void add(AtomValue atom) {
    atomIds.put(atom.getId(), atom);
    atomNames.put(atom.getName(), atom);
  }

  public AtomValue getAtom(Atom atom) {
    return atomIds.get(atom.getValue());
  }

  public AtomValue getAtom(int id) {
    if(atomIds.containsKey(id)) {
      return atomIds.get(id);
    }
    GetAtomNameReply name = protocolService.send(GetAtomName.builder().atom(id).build());
    AtomValue result = new AtomValue(id, byteListToString(name.getName(), StandardCharsets.ISO_8859_1));
    add(result);
    return result;
  }

  public AtomValue getAtom(String name) {
    if(atomNames.containsKey(name)) {
      return atomNames.get(name);
    }
    int id = protocolService.send(InternAtom.builder()
      .name(stringToByteList(name, StandardCharsets.ISO_8859_1))
      .build()).getAtom();
    AtomValue result = new AtomValue(id, name);
    add(result);
    return result;
  }
}
