package com.github.moaxcp.x11.protocol.record;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Range implements XStruct {
  public static final String PLUGIN_NAME = "record";

  @NonNull
  private Range8 coreRequests;

  @NonNull
  private Range8 coreReplies;

  @NonNull
  private ExtRange extRequests;

  @NonNull
  private ExtRange extReplies;

  @NonNull
  private Range8 deliveredEvents;

  @NonNull
  private Range8 deviceEvents;

  @NonNull
  private Range8 errors;

  private boolean clientStarted;

  private boolean clientDied;

  public static Range readRange(X11Input in) throws IOException {
    Range.RangeBuilder javaBuilder = Range.builder();
    Range8 coreRequests = Range8.readRange8(in);
    Range8 coreReplies = Range8.readRange8(in);
    ExtRange extRequests = ExtRange.readExtRange(in);
    ExtRange extReplies = ExtRange.readExtRange(in);
    Range8 deliveredEvents = Range8.readRange8(in);
    Range8 deviceEvents = Range8.readRange8(in);
    Range8 errors = Range8.readRange8(in);
    boolean clientStarted = in.readBool();
    boolean clientDied = in.readBool();
    javaBuilder.coreRequests(coreRequests);
    javaBuilder.coreReplies(coreReplies);
    javaBuilder.extRequests(extRequests);
    javaBuilder.extReplies(extReplies);
    javaBuilder.deliveredEvents(deliveredEvents);
    javaBuilder.deviceEvents(deviceEvents);
    javaBuilder.errors(errors);
    javaBuilder.clientStarted(clientStarted);
    javaBuilder.clientDied(clientDied);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    coreRequests.write(out);
    coreReplies.write(out);
    extRequests.write(out);
    extReplies.write(out);
    deliveredEvents.write(out);
    deviceEvents.write(out);
    errors.write(out);
    out.writeBool(clientStarted);
    out.writeBool(clientDied);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class RangeBuilder {
    public int getSize() {
      return 24;
    }
  }
}
