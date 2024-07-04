package com.github.moaxcp.x11.x11client.api.record;

import com.github.moaxcp.x11.protocol.record.Category;
import com.github.moaxcp.x11.protocol.record.HType;

import java.util.List;

public final class RecordReply {
  private final Category category;
  private final short sequenceNumber;
  private final byte elementHeader;
  private final boolean clientSwapped;
  private final int xidBase;
  private final int serverTime;
  private final int recSequenceNum;
  private final List<RecordData> data;

  RecordReply(Category category, short sequenceNumber, byte elementHeader, boolean clientSwapped, int xidBase, int serverTime, int recSequenceNum, List<RecordData> data) {
    this.category = category;
    this.sequenceNumber = sequenceNumber;
    this.elementHeader = elementHeader;
    this.clientSwapped = clientSwapped;
    this.xidBase = xidBase;
    this.serverTime = serverTime;
    this.recSequenceNum = recSequenceNum;
    this.data = data;
  }

  public static RecordReplyBuilder builder() {
    return new RecordReplyBuilder();
  }

  public boolean getFromServerTime() {
    return HType.FROM_SERVER_TIME.isEnabled(elementHeader);
  }

  public boolean getFromClientTime() {
    return HType.FROM_CLIENT_TIME.isEnabled(elementHeader);
  }

  public boolean getFromClientSequence() {
    return HType.FROM_CLIENT_SEQUENCE.isEnabled(elementHeader);
  }

  public Category getCategory() {
    return this.category;
  }

  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  public byte getElementHeader() {
    return this.elementHeader;
  }

  public boolean isClientSwapped() {
    return this.clientSwapped;
  }

  public int getXidBase() {
    return this.xidBase;
  }

  public int getServerTime() {
    return this.serverTime;
  }

  public int getRecSequenceNum() {
    return this.recSequenceNum;
  }

  public List<RecordData> getData() {
    return this.data;
  }

  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof RecordReply)) return false;
    final RecordReply other = (RecordReply) o;
    final Object this$category = this.getCategory();
    final Object other$category = other.getCategory();
    if (this$category == null ? other$category != null : !this$category.equals(other$category)) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getElementHeader() != other.getElementHeader()) return false;
    if (this.isClientSwapped() != other.isClientSwapped()) return false;
    if (this.getXidBase() != other.getXidBase()) return false;
    if (this.getServerTime() != other.getServerTime()) return false;
    if (this.getRecSequenceNum() != other.getRecSequenceNum()) return false;
    final Object this$data = this.getData();
    final Object other$data = other.getData();
    if (this$data == null ? other$data != null : !this$data.equals(other$data)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $category = this.getCategory();
    result = result * PRIME + ($category == null ? 43 : $category.hashCode());
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getElementHeader();
    result = result * PRIME + (this.isClientSwapped() ? 79 : 97);
    result = result * PRIME + this.getXidBase();
    result = result * PRIME + this.getServerTime();
    result = result * PRIME + this.getRecSequenceNum();
    final Object $data = this.getData();
    result = result * PRIME + ($data == null ? 43 : $data.hashCode());
    return result;
  }

  public String toString() {
    return "RecordReply(category=" + this.getCategory() + ", sequenceNumber=" + this.getSequenceNumber() + ", elementHeader=" + this.getElementHeader() + ", clientSwapped=" + this.isClientSwapped() + ", xidBase=" + this.getXidBase() + ", serverTime=" + this.getServerTime() + ", recSequenceNum=" + this.getRecSequenceNum() + ", data=" + this.getData() + ")";
  }

  public static class RecordReplyBuilder {
    private Category category;
    private short sequenceNumber;
    private byte elementHeader;
    private boolean clientSwapped;
    private int xidBase;
    private int serverTime;
    private int recSequenceNum;
    private List<RecordData> data;

    RecordReplyBuilder() {
    }

    public RecordReplyBuilder category(Category category) {
      this.category = category;
      return this;
    }

    public RecordReplyBuilder sequenceNumber(short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    public RecordReplyBuilder elementHeader(byte elementHeader) {
      this.elementHeader = elementHeader;
      return this;
    }

    public RecordReplyBuilder clientSwapped(boolean clientSwapped) {
      this.clientSwapped = clientSwapped;
      return this;
    }

    public RecordReplyBuilder xidBase(int xidBase) {
      this.xidBase = xidBase;
      return this;
    }

    public RecordReplyBuilder serverTime(int serverTime) {
      this.serverTime = serverTime;
      return this;
    }

    public RecordReplyBuilder recSequenceNum(int recSequenceNum) {
      this.recSequenceNum = recSequenceNum;
      return this;
    }

    public RecordReplyBuilder data(List<RecordData> data) {
      this.data = data;
      return this;
    }

    public RecordReply build() {
      return new RecordReply(this.category, this.sequenceNumber, this.elementHeader, this.clientSwapped, this.xidBase, this.serverTime, this.recSequenceNum, this.data);
    }

    public String toString() {
      return "RecordReply.RecordReplyBuilder(category=" + this.category + ", sequenceNumber=" + this.sequenceNumber + ", elementHeader=" + this.elementHeader + ", clientSwapped=" + this.clientSwapped + ", xidBase=" + this.xidBase + ", serverTime=" + this.serverTime + ", recSequenceNum=" + this.recSequenceNum + ", data=" + this.data + ")";
    }
  }
}
