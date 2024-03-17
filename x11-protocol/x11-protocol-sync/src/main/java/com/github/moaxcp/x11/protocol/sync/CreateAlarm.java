package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CreateAlarm implements OneWayRequest {
  public static final String PLUGIN_NAME = "sync";

  public static final byte OPCODE = 8;

  private int id;

  private int valueMask;

  private int counter;

  private int valueType;

  private Int64 value;

  private int testType;

  private Int64 delta;

  private int events;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateAlarm readCreateAlarm(X11Input in) throws IOException {
    CreateAlarm.CreateAlarmBuilder javaBuilder = CreateAlarm.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int id = in.readCard32();
    int valueMask = in.readCard32();
    int counter = 0;
    int valueType = 0;
    Int64 value = null;
    int testType = 0;
    Int64 delta = null;
    int events = 0;
    javaBuilder.id(id);
    javaBuilder.valueMask(valueMask);
    if(Ca.COUNTER.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      counter = in.readCard32();
      javaBuilder.counter(counter);
    }
    if(Ca.VALUE_TYPE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      valueType = in.readCard32();
      javaBuilder.valueType(valueType);
    }
    if(Ca.VALUE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      value = Int64.readInt64(in);
      javaBuilder.value(value);
    }
    if(Ca.TEST_TYPE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      testType = in.readCard32();
      javaBuilder.testType(testType);
    }
    if(Ca.DELTA.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      delta = Int64.readInt64(in);
      javaBuilder.delta(delta);
    }
    if(Ca.EVENTS.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      events = in.readCard32();
      javaBuilder.events(events);
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(id);
    out.writeCard32(valueMask);
    if(Ca.COUNTER.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(counter);
    }
    if(Ca.VALUE_TYPE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(valueType);
    }
    if(Ca.VALUE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      value.write(out);
    }
    if(Ca.TEST_TYPE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(testType);
    }
    if(Ca.DELTA.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      delta.write(out);
    }
    if(Ca.EVENTS.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(events);
    }
    out.writePadAlign(getSize());
  }

  public boolean isValueMaskEnabled(@NonNull Ca... maskEnums) {
    for(Ca m : maskEnums) {
      if(!m.isEnabled(valueMask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 28 + (Ca.COUNTER.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Ca.VALUE_TYPE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Ca.TEST_TYPE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Ca.EVENTS.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class CreateAlarmBuilder {
    private CreateAlarm.CreateAlarmBuilder valueMask(int valueMask) {
      this.valueMask = valueMask;
      return this;
    }

    public boolean isValueMaskEnabled(@NonNull Ca... maskEnums) {
      for(Ca m : maskEnums) {
        if(!m.isEnabled(valueMask)) {
          return false;
        }
      }
      return true;
    }

    private CreateAlarm.CreateAlarmBuilder valueMaskEnable(Ca... maskEnums) {
      for(Ca m : maskEnums) {
        valueMask((int) m.enableFor(valueMask));
      }
      return this;
    }

    private CreateAlarm.CreateAlarmBuilder valueMaskDisable(Ca... maskEnums) {
      for(Ca m : maskEnums) {
        valueMask((int) m.disableFor(valueMask));
      }
      return this;
    }

    public CreateAlarm.CreateAlarmBuilder counter(int counter) {
      this.counter = counter;
      valueMaskEnable(Ca.COUNTER);
      return this;
    }

    public CreateAlarm.CreateAlarmBuilder valueType(int valueType) {
      this.valueType = valueType;
      valueMaskEnable(Ca.VALUE_TYPE);
      return this;
    }

    public CreateAlarm.CreateAlarmBuilder valueType(Valuetype valueType) {
      this.valueType = (int) valueType.getValue();
      valueMaskEnable(Ca.VALUE_TYPE);
      return this;
    }

    public CreateAlarm.CreateAlarmBuilder value(Int64 value) {
      this.value = value;
      valueMaskEnable(Ca.VALUE);
      return this;
    }

    public CreateAlarm.CreateAlarmBuilder testType(int testType) {
      this.testType = testType;
      valueMaskEnable(Ca.TEST_TYPE);
      return this;
    }

    public CreateAlarm.CreateAlarmBuilder testType(Testtype testType) {
      this.testType = (int) testType.getValue();
      valueMaskEnable(Ca.TEST_TYPE);
      return this;
    }

    public CreateAlarm.CreateAlarmBuilder delta(Int64 delta) {
      this.delta = delta;
      valueMaskEnable(Ca.DELTA);
      return this;
    }

    public CreateAlarm.CreateAlarmBuilder events(int events) {
      this.events = events;
      valueMaskEnable(Ca.EVENTS);
      return this;
    }

    public int getSize() {
      return 28 + (Ca.COUNTER.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Ca.VALUE_TYPE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Ca.TEST_TYPE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Ca.EVENTS.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
    }
  }
}
