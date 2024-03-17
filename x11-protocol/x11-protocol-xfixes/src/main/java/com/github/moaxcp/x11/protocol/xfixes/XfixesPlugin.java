package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.XError;
import com.github.moaxcp.x11.protocol.XEvent;
import com.github.moaxcp.x11.protocol.XGenericEvent;
import com.github.moaxcp.x11.protocol.XProtocolPlugin;
import com.github.moaxcp.x11.protocol.XRequest;
import java.io.IOException;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

public class XfixesPlugin implements XProtocolPlugin {
  @Getter
  @Setter
  private byte majorOpcode;

  @Getter
  @Setter
  private byte firstEvent;

  @Getter
  @Setter
  private byte firstError;

  public String getPluginName() {
    return "xfixes";
  }

  public Optional<String> getExtensionXName() {
    return Optional.ofNullable("XFIXES");
  }

  public Optional<String> getExtensionName() {
    return Optional.ofNullable("XFixes");
  }

  public Optional<Boolean> getExtensionMultiword() {
    return Optional.ofNullable(true);
  }

  public Optional<Byte> getMajorVersion() {
    return Optional.ofNullable((byte) 5);
  }

  public Optional<Byte> getMinorVersion() {
    return Optional.ofNullable((byte) 0);
  }

  @Override
  public boolean supportedRequest(XRequest request) {
    return request.getPluginName().equals(getPluginName());
  }

  @Override
  public boolean supportedRequest(byte majorOpcode, byte minorOpcode) {
    boolean isMajorOpcode = majorOpcode == getMajorOpcode();
    if(minorOpcode == 0) {
      return isMajorOpcode;
    }
    if(minorOpcode == 1) {
      return isMajorOpcode;
    }
    if(minorOpcode == 2) {
      return isMajorOpcode;
    }
    if(minorOpcode == 3) {
      return isMajorOpcode;
    }
    if(minorOpcode == 4) {
      return isMajorOpcode;
    }
    if(minorOpcode == 5) {
      return isMajorOpcode;
    }
    if(minorOpcode == 6) {
      return isMajorOpcode;
    }
    if(minorOpcode == 7) {
      return isMajorOpcode;
    }
    if(minorOpcode == 8) {
      return isMajorOpcode;
    }
    if(minorOpcode == 9) {
      return isMajorOpcode;
    }
    if(minorOpcode == 10) {
      return isMajorOpcode;
    }
    if(minorOpcode == 11) {
      return isMajorOpcode;
    }
    if(minorOpcode == 12) {
      return isMajorOpcode;
    }
    if(minorOpcode == 13) {
      return isMajorOpcode;
    }
    if(minorOpcode == 14) {
      return isMajorOpcode;
    }
    if(minorOpcode == 15) {
      return isMajorOpcode;
    }
    if(minorOpcode == 16) {
      return isMajorOpcode;
    }
    if(minorOpcode == 17) {
      return isMajorOpcode;
    }
    if(minorOpcode == 18) {
      return isMajorOpcode;
    }
    if(minorOpcode == 19) {
      return isMajorOpcode;
    }
    if(minorOpcode == 20) {
      return isMajorOpcode;
    }
    if(minorOpcode == 21) {
      return isMajorOpcode;
    }
    if(minorOpcode == 22) {
      return isMajorOpcode;
    }
    if(minorOpcode == 23) {
      return isMajorOpcode;
    }
    if(minorOpcode == 24) {
      return isMajorOpcode;
    }
    if(minorOpcode == 25) {
      return isMajorOpcode;
    }
    if(minorOpcode == 26) {
      return isMajorOpcode;
    }
    if(minorOpcode == 27) {
      return isMajorOpcode;
    }
    if(minorOpcode == 28) {
      return isMajorOpcode;
    }
    if(minorOpcode == 29) {
      return isMajorOpcode;
    }
    if(minorOpcode == 30) {
      return isMajorOpcode;
    }
    if(minorOpcode == 31) {
      return isMajorOpcode;
    }
    if(minorOpcode == 32) {
      return isMajorOpcode;
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
  public XRequest readRequest(byte majorOpcode, byte minorOpcode, X11Input in) throws IOException {
    if(minorOpcode == QueryVersion.OPCODE) {
      return QueryVersion.readQueryVersion(in);
    }
    if(minorOpcode == ChangeSaveSet.OPCODE) {
      return ChangeSaveSet.readChangeSaveSet(in);
    }
    if(minorOpcode == SelectSelectionInput.OPCODE) {
      return SelectSelectionInput.readSelectSelectionInput(in);
    }
    if(minorOpcode == SelectCursorInput.OPCODE) {
      return SelectCursorInput.readSelectCursorInput(in);
    }
    if(minorOpcode == GetCursorImage.OPCODE) {
      return GetCursorImage.readGetCursorImage(in);
    }
    if(minorOpcode == CreateRegion.OPCODE) {
      return CreateRegion.readCreateRegion(in);
    }
    if(minorOpcode == CreateRegionFromBitmap.OPCODE) {
      return CreateRegionFromBitmap.readCreateRegionFromBitmap(in);
    }
    if(minorOpcode == CreateRegionFromWindow.OPCODE) {
      return CreateRegionFromWindow.readCreateRegionFromWindow(in);
    }
    if(minorOpcode == CreateRegionFromGC.OPCODE) {
      return CreateRegionFromGC.readCreateRegionFromGC(in);
    }
    if(minorOpcode == CreateRegionFromPicture.OPCODE) {
      return CreateRegionFromPicture.readCreateRegionFromPicture(in);
    }
    if(minorOpcode == DestroyRegion.OPCODE) {
      return DestroyRegion.readDestroyRegion(in);
    }
    if(minorOpcode == SetRegion.OPCODE) {
      return SetRegion.readSetRegion(in);
    }
    if(minorOpcode == CopyRegion.OPCODE) {
      return CopyRegion.readCopyRegion(in);
    }
    if(minorOpcode == UnionRegion.OPCODE) {
      return UnionRegion.readUnionRegion(in);
    }
    if(minorOpcode == IntersectRegion.OPCODE) {
      return IntersectRegion.readIntersectRegion(in);
    }
    if(minorOpcode == SubtractRegion.OPCODE) {
      return SubtractRegion.readSubtractRegion(in);
    }
    if(minorOpcode == InvertRegion.OPCODE) {
      return InvertRegion.readInvertRegion(in);
    }
    if(minorOpcode == TranslateRegion.OPCODE) {
      return TranslateRegion.readTranslateRegion(in);
    }
    if(minorOpcode == RegionExtents.OPCODE) {
      return RegionExtents.readRegionExtents(in);
    }
    if(minorOpcode == FetchRegion.OPCODE) {
      return FetchRegion.readFetchRegion(in);
    }
    if(minorOpcode == SetGCClipRegion.OPCODE) {
      return SetGCClipRegion.readSetGCClipRegion(in);
    }
    if(minorOpcode == SetWindowShapeRegion.OPCODE) {
      return SetWindowShapeRegion.readSetWindowShapeRegion(in);
    }
    if(minorOpcode == SetPictureClipRegion.OPCODE) {
      return SetPictureClipRegion.readSetPictureClipRegion(in);
    }
    if(minorOpcode == SetCursorName.OPCODE) {
      return SetCursorName.readSetCursorName(in);
    }
    if(minorOpcode == GetCursorName.OPCODE) {
      return GetCursorName.readGetCursorName(in);
    }
    if(minorOpcode == GetCursorImageAndName.OPCODE) {
      return GetCursorImageAndName.readGetCursorImageAndName(in);
    }
    if(minorOpcode == ChangeCursor.OPCODE) {
      return ChangeCursor.readChangeCursor(in);
    }
    if(minorOpcode == ChangeCursorByName.OPCODE) {
      return ChangeCursorByName.readChangeCursorByName(in);
    }
    if(minorOpcode == ExpandRegion.OPCODE) {
      return ExpandRegion.readExpandRegion(in);
    }
    if(minorOpcode == HideCursor.OPCODE) {
      return HideCursor.readHideCursor(in);
    }
    if(minorOpcode == ShowCursor.OPCODE) {
      return ShowCursor.readShowCursor(in);
    }
    if(minorOpcode == CreatePointerBarrier.OPCODE) {
      return CreatePointerBarrier.readCreatePointerBarrier(in);
    }
    if(minorOpcode == DeletePointerBarrier.OPCODE) {
      return DeletePointerBarrier.readDeletePointerBarrier(in);
    }
    throw new IllegalArgumentException("majorOpcode " + majorOpcode + ", minorOpcode " + minorOpcode + " is not supported");
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
