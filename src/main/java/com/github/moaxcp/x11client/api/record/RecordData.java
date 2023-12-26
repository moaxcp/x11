package com.github.moaxcp.x11client.api.record;

import com.github.moaxcp.x11client.protocol.XObject;
import java.util.Optional;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RecordData {
  Integer fromServerTime;
  Integer fromClientTime;
  Integer fromClientSequence;
  XObject xObject;

  public Optional<Integer> fromServerTime() {
    return Optional.ofNullable(fromServerTime);
  }

  public Optional<Integer> fromClientTime() {
    return Optional.ofNullable(fromClientTime);
  }

  public Optional<Integer> getFromClientSequence() {
    return Optional.ofNullable(fromClientSequence);
  }
}
