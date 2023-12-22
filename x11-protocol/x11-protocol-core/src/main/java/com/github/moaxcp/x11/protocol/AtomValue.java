package com.github.moaxcp.x11.protocol;

import lombok.NonNull;
import lombok.Value;

/**
 * An atom. May be a predefined atom or an intern atom.
 */
@Value
public class AtomValue {
  int id;
  @NonNull
  String name;
}
