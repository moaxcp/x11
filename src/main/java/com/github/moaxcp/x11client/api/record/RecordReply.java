package com.github.moaxcp.x11client.api.record;

import com.github.moaxcp.x11client.protocol.record.Category;
import com.github.moaxcp.x11client.protocol.record.HType;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RecordReply {
  Category category;
  short sequenceNumber;
  byte elementHeader;
  boolean clientSwapped;
  int xidBase;
  int serverTime;
  int recSequenceNum;
  List<RecordData> data;

  public boolean getFromServerTime() {
    return HType.FROM_SERVER_TIME.isEnabled(elementHeader);
  }

  public boolean getFromClientTime() {
    return HType.FROM_CLIENT_TIME.isEnabled(elementHeader);
  }

  public boolean getFromClientSequence() {
    return HType.FROM_CLIENT_SEQUENCE.isEnabled(elementHeader);
  }
}
