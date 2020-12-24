package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.xc_misc.GetXIDRange;
import com.github.moaxcp.x11client.protocol.xc_misc.GetXIDRangeReply;
import lombok.NonNull;

public class ResourceIdService {
  private int nextResourceId;
  private final int resourceIdMask;
  private final int resourceIdBase;

  private boolean useXcmisc;
  private int xcmiscStartId;
  private int xcmiscCount;

  private final XProtocolService protocolService;

  public ResourceIdService(@NonNull XProtocolService protocolService, int resourceIdMask, int resourceIdBase) {
    this.protocolService = protocolService;
    this.resourceIdMask = resourceIdMask;
    this.resourceIdBase = resourceIdBase;
    this.nextResourceId = 1;
  }

  public void setUseXcmisc(boolean useXcmisc) {
    this.useXcmisc = useXcmisc;
  }

  int getNextResourceId() {
    return nextResourceId;
  }

  public int nextResourceId() {
    if (!useXcmisc && hasValidNextResourceFor(nextResourceId)) {
      return maskNextResourceId(nextResourceId++);
    }
    if(nextResourceId >= xcmiscStartId && nextResourceId < xcmiscStartId + xcmiscCount) {
      return nextResourceId++;
    }
    if(!protocolService.activatedPlugin("XC-MISC")) {
      throw new IllegalStateException("Core protocol is out of ids and XC-MISC is not activated.");
    }
    GetXIDRangeReply range = protocolService.send(GetXIDRange.builder().build());
    xcmiscStartId = range.getStartId();
    nextResourceId = xcmiscStartId;
    xcmiscCount = range.getCount();
    return nextResourceId++;
  }

  private boolean hasValidNextResourceFor(int resourceId) {
    useXcmisc = !((resourceId & ~resourceIdMask) == 0);
    return !useXcmisc;
  }

  private int maskNextResourceId(int resourceId) {
    return resourceId | resourceIdBase;
  }
}
