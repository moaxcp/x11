package com.github.moaxcp.x11.x11client.api.record;

import com.github.moaxcp.x11.protocol.XObject;

import java.util.Optional;

public final class RecordData {
  private final Integer fromServerTime;
  private final Integer fromClientTime;
  private final Integer fromClientSequence;
  private final XObject xObject;

  RecordData(Integer fromServerTime, Integer fromClientTime, Integer fromClientSequence, XObject xObject) {
    this.fromServerTime = fromServerTime;
    this.fromClientTime = fromClientTime;
    this.fromClientSequence = fromClientSequence;
    this.xObject = xObject;
  }

  public static RecordDataBuilder builder() {
    return new RecordDataBuilder();
  }

  public Optional<Integer> fromServerTime() {
    return Optional.ofNullable(fromServerTime);
  }

  public Optional<Integer> fromClientTime() {
    return Optional.ofNullable(fromClientTime);
  }

  public Optional<Integer> getFromClientSequence() {
    return Optional.ofNullable(fromClientSequence);
  }

  public Integer getFromServerTime() {
    return this.fromServerTime;
  }

  public Integer getFromClientTime() {
    return this.fromClientTime;
  }

  public XObject getXObject() {
    return this.xObject;
  }

  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof RecordData)) return false;
    final RecordData other = (RecordData) o;
    final Object this$fromServerTime = this.getFromServerTime();
    final Object other$fromServerTime = other.getFromServerTime();
    if (this$fromServerTime == null ? other$fromServerTime != null : !this$fromServerTime.equals(other$fromServerTime))
      return false;
    final Object this$fromClientTime = this.getFromClientTime();
    final Object other$fromClientTime = other.getFromClientTime();
    if (this$fromClientTime == null ? other$fromClientTime != null : !this$fromClientTime.equals(other$fromClientTime))
      return false;
    final Object this$fromClientSequence = this.getFromClientSequence();
    final Object other$fromClientSequence = other.getFromClientSequence();
    if (this$fromClientSequence == null ? other$fromClientSequence != null : !this$fromClientSequence.equals(other$fromClientSequence))
      return false;
    final Object this$xObject = this.getXObject();
    final Object other$xObject = other.getXObject();
    if (this$xObject == null ? other$xObject != null : !this$xObject.equals(other$xObject)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $fromServerTime = this.getFromServerTime();
    result = result * PRIME + ($fromServerTime == null ? 43 : $fromServerTime.hashCode());
    final Object $fromClientTime = this.getFromClientTime();
    result = result * PRIME + ($fromClientTime == null ? 43 : $fromClientTime.hashCode());
    final Object $fromClientSequence = this.getFromClientSequence();
    result = result * PRIME + ($fromClientSequence == null ? 43 : $fromClientSequence.hashCode());
    final Object $xObject = this.getXObject();
    result = result * PRIME + ($xObject == null ? 43 : $xObject.hashCode());
    return result;
  }

  public String toString() {
    return "RecordData(fromServerTime=" + this.getFromServerTime() + ", fromClientTime=" + this.getFromClientTime() + ", fromClientSequence=" + this.getFromClientSequence() + ", xObject=" + this.getXObject() + ")";
  }

  public static class RecordDataBuilder {
    private Integer fromServerTime;
    private Integer fromClientTime;
    private Integer fromClientSequence;
    private XObject xObject;

    RecordDataBuilder() {
    }

    public RecordDataBuilder fromServerTime(Integer fromServerTime) {
      this.fromServerTime = fromServerTime;
      return this;
    }

    public RecordDataBuilder fromClientTime(Integer fromClientTime) {
      this.fromClientTime = fromClientTime;
      return this;
    }

    public RecordDataBuilder fromClientSequence(Integer fromClientSequence) {
      this.fromClientSequence = fromClientSequence;
      return this;
    }

    public RecordDataBuilder xObject(XObject xObject) {
      this.xObject = xObject;
      return this;
    }

    public RecordData build() {
      return new RecordData(this.fromServerTime, this.fromClientTime, this.fromClientSequence, this.xObject);
    }

    public String toString() {
      return "RecordData.RecordDataBuilder(fromServerTime=" + this.fromServerTime + ", fromClientTime=" + this.fromClientTime + ", fromClientSequence=" + this.fromClientSequence + ", xObject=" + this.xObject + ")";
    }
  }
}
