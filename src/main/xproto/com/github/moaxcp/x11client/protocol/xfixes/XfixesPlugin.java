package com.github.moaxcp.x11client.protocol.xfixes;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XError;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import com.github.moaxcp.x11client.protocol.XProtocolPlugin;
import com.github.moaxcp.x11client.protocol.XRequest;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

public class XfixesPlugin implements XProtocolPlugin {
  public static final String NAME = "XFIXES";

  @Getter
  private byte majorVersion = 5;

  @Getter
  private byte minorVersion = 0;

  @Getter
  @Setter
  private byte firstEvent;

  @Getter
  @Setter
  private byte firstError;

  public String getName() {
    return NAME;
  }

  @Override
  public boolean supportedRequest(XRequest request) {
    if(request instanceof QueryVersion) {
      return true;
    }
    if(request instanceof ChangeSaveSet) {
      return true;
    }
    if(request instanceof SelectSelectionInput) {
      return true;
    }
    if(request instanceof SelectCursorInput) {
      return true;
    }
    if(request instanceof GetCursorImage) {
      return true;
    }
    if(request instanceof CreateRegion) {
      return true;
    }
    if(request instanceof CreateRegionFromBitmap) {
      return true;
    }
    if(request instanceof CreateRegionFromWindow) {
      return true;
    }
    if(request instanceof CreateRegionFromGC) {
      return true;
    }
    if(request instanceof CreateRegionFromPicture) {
      return true;
    }
    if(request instanceof DestroyRegion) {
      return true;
    }
    if(request instanceof SetRegion) {
      return true;
    }
    if(request instanceof CopyRegion) {
      return true;
    }
    if(request instanceof UnionRegion) {
      return true;
    }
    if(request instanceof IntersectRegion) {
      return true;
    }
    if(request instanceof SubtractRegion) {
      return true;
    }
    if(request instanceof InvertRegion) {
      return true;
    }
    if(request instanceof TranslateRegion) {
      return true;
    }
    if(request instanceof RegionExtents) {
      return true;
    }
    if(request instanceof FetchRegion) {
      return true;
    }
    if(request instanceof SetGCClipRegion) {
      return true;
    }
    if(request instanceof SetWindowShapeRegion) {
      return true;
    }
    if(request instanceof SetPictureClipRegion) {
      return true;
    }
    if(request instanceof SetCursorName) {
      return true;
    }
    if(request instanceof GetCursorName) {
      return true;
    }
    if(request instanceof GetCursorImageAndName) {
      return true;
    }
    if(request instanceof ChangeCursor) {
      return true;
    }
    if(request instanceof ChangeCursorByName) {
      return true;
    }
    if(request instanceof ExpandRegion) {
      return true;
    }
    if(request instanceof HideCursor) {
      return true;
    }
    if(request instanceof ShowCursor) {
      return true;
    }
    if(request instanceof CreatePointerBarrier) {
      return true;
    }
    if(request instanceof DeletePointerBarrier) {
      return true;
    }
    return false;
  }

  @Override
  public boolean supportedEvent(byte number) {
    if(number - firstEvent == 0) {
      return true;
    }
    if(number - firstEvent == 1) {
      return true;
    }
    return false;
  }

  @Override
  public boolean supportedError(byte code) {
    if(code - firstError == 0) {
      return true;
    }
    return false;
  }

  @Override
  public XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException {
    if(number - firstEvent == 0) {
      return SelectionNotifyEvent.readSelectionNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 1) {
      return CursorNotifyEvent.readCursorNotifyEvent(firstEvent, sentEvent, in);
    }
    throw new IllegalArgumentException("number " + number + " is not supported");
  }

  @Override
  public XError readError(byte code, X11Input in) throws IOException {
    if(code - firstError == 0) {
      return BadRegionError.readBadRegionError(firstError, in);
    }
    throw new IllegalArgumentException("code " + code + " is not supported");
  }

  @Override
  public XGenericEvent readGenericEvent(boolean sentEvent, byte extension, short sequenceNumber,
      int length, short eventType, X11Input in) throws IOException {
    throw new IllegalArgumentException("eventType " + eventType + " is not supported");
  }
}
