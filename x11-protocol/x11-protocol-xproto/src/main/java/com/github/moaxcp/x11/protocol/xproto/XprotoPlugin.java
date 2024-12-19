package com.github.moaxcp.x11.protocol.xproto;

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

public class XprotoPlugin implements XProtocolPlugin {
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
    return "xproto";
  }

  public Optional<String> getExtensionXName() {
    return Optional.ofNullable("");
  }

  public Optional<String> getExtensionName() {
    return Optional.ofNullable("");
  }

  public Optional<Boolean> getExtensionMultiword() {
    return Optional.ofNullable(true);
  }

  public Optional<Byte> getMajorVersion() {
    return Optional.ofNullable((byte) 0);
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
    if(majorOpcode == 1) {
      return true;
    }
    if(majorOpcode == 2) {
      return true;
    }
    if(majorOpcode == 3) {
      return true;
    }
    if(majorOpcode == 4) {
      return true;
    }
    if(majorOpcode == 5) {
      return true;
    }
    if(majorOpcode == 6) {
      return true;
    }
    if(majorOpcode == 7) {
      return true;
    }
    if(majorOpcode == 8) {
      return true;
    }
    if(majorOpcode == 9) {
      return true;
    }
    if(majorOpcode == 10) {
      return true;
    }
    if(majorOpcode == 11) {
      return true;
    }
    if(majorOpcode == 12) {
      return true;
    }
    if(majorOpcode == 13) {
      return true;
    }
    if(majorOpcode == 14) {
      return true;
    }
    if(majorOpcode == 15) {
      return true;
    }
    if(majorOpcode == 16) {
      return true;
    }
    if(majorOpcode == 17) {
      return true;
    }
    if(majorOpcode == 18) {
      return true;
    }
    if(majorOpcode == 19) {
      return true;
    }
    if(majorOpcode == 20) {
      return true;
    }
    if(majorOpcode == 21) {
      return true;
    }
    if(majorOpcode == 22) {
      return true;
    }
    if(majorOpcode == 23) {
      return true;
    }
    if(majorOpcode == 24) {
      return true;
    }
    if(majorOpcode == 25) {
      return true;
    }
    if(majorOpcode == 26) {
      return true;
    }
    if(majorOpcode == 27) {
      return true;
    }
    if(majorOpcode == 28) {
      return true;
    }
    if(majorOpcode == 29) {
      return true;
    }
    if(majorOpcode == 30) {
      return true;
    }
    if(majorOpcode == 31) {
      return true;
    }
    if(majorOpcode == 32) {
      return true;
    }
    if(majorOpcode == 33) {
      return true;
    }
    if(majorOpcode == 34) {
      return true;
    }
    if(majorOpcode == 35) {
      return true;
    }
    if(majorOpcode == 36) {
      return true;
    }
    if(majorOpcode == 37) {
      return true;
    }
    if(majorOpcode == 38) {
      return true;
    }
    if(majorOpcode == 39) {
      return true;
    }
    if(majorOpcode == 40) {
      return true;
    }
    if(majorOpcode == 41) {
      return true;
    }
    if(majorOpcode == 42) {
      return true;
    }
    if(majorOpcode == 43) {
      return true;
    }
    if(majorOpcode == 44) {
      return true;
    }
    if(majorOpcode == 45) {
      return true;
    }
    if(majorOpcode == 46) {
      return true;
    }
    if(majorOpcode == 47) {
      return true;
    }
    if(majorOpcode == 48) {
      return true;
    }
    if(majorOpcode == 49) {
      return true;
    }
    if(majorOpcode == 50) {
      return true;
    }
    if(majorOpcode == 51) {
      return true;
    }
    if(majorOpcode == 52) {
      return true;
    }
    if(majorOpcode == 53) {
      return true;
    }
    if(majorOpcode == 54) {
      return true;
    }
    if(majorOpcode == 55) {
      return true;
    }
    if(majorOpcode == 56) {
      return true;
    }
    if(majorOpcode == 57) {
      return true;
    }
    if(majorOpcode == 58) {
      return true;
    }
    if(majorOpcode == 59) {
      return true;
    }
    if(majorOpcode == 60) {
      return true;
    }
    if(majorOpcode == 61) {
      return true;
    }
    if(majorOpcode == 62) {
      return true;
    }
    if(majorOpcode == 63) {
      return true;
    }
    if(majorOpcode == 64) {
      return true;
    }
    if(majorOpcode == 65) {
      return true;
    }
    if(majorOpcode == 66) {
      return true;
    }
    if(majorOpcode == 67) {
      return true;
    }
    if(majorOpcode == 68) {
      return true;
    }
    if(majorOpcode == 69) {
      return true;
    }
    if(majorOpcode == 70) {
      return true;
    }
    if(majorOpcode == 71) {
      return true;
    }
    if(majorOpcode == 72) {
      return true;
    }
    if(majorOpcode == 73) {
      return true;
    }
    if(majorOpcode == 74) {
      return true;
    }
    if(majorOpcode == 75) {
      return true;
    }
    if(majorOpcode == 76) {
      return true;
    }
    if(majorOpcode == 77) {
      return true;
    }
    if(majorOpcode == 78) {
      return true;
    }
    if(majorOpcode == 79) {
      return true;
    }
    if(majorOpcode == 80) {
      return true;
    }
    if(majorOpcode == 81) {
      return true;
    }
    if(majorOpcode == 82) {
      return true;
    }
    if(majorOpcode == 83) {
      return true;
    }
    if(majorOpcode == 84) {
      return true;
    }
    if(majorOpcode == 85) {
      return true;
    }
    if(majorOpcode == 86) {
      return true;
    }
    if(majorOpcode == 87) {
      return true;
    }
    if(majorOpcode == 88) {
      return true;
    }
    if(majorOpcode == 89) {
      return true;
    }
    if(majorOpcode == 90) {
      return true;
    }
    if(majorOpcode == 91) {
      return true;
    }
    if(majorOpcode == 92) {
      return true;
    }
    if(majorOpcode == 93) {
      return true;
    }
    if(majorOpcode == 94) {
      return true;
    }
    if(majorOpcode == 95) {
      return true;
    }
    if(majorOpcode == 96) {
      return true;
    }
    if(majorOpcode == 97) {
      return true;
    }
    if(majorOpcode == 98) {
      return true;
    }
    if(majorOpcode == 99) {
      return true;
    }
    if(majorOpcode == 100) {
      return true;
    }
    if(majorOpcode == 101) {
      return true;
    }
    if(majorOpcode == 102) {
      return true;
    }
    if(majorOpcode == 103) {
      return true;
    }
    if(majorOpcode == 104) {
      return true;
    }
    if(majorOpcode == 105) {
      return true;
    }
    if(majorOpcode == 106) {
      return true;
    }
    if(majorOpcode == 107) {
      return true;
    }
    if(majorOpcode == 108) {
      return true;
    }
    if(majorOpcode == 109) {
      return true;
    }
    if(majorOpcode == 110) {
      return true;
    }
    if(majorOpcode == 111) {
      return true;
    }
    if(majorOpcode == 112) {
      return true;
    }
    if(majorOpcode == 113) {
      return true;
    }
    if(majorOpcode == 114) {
      return true;
    }
    if(majorOpcode == 115) {
      return true;
    }
    if(majorOpcode == 116) {
      return true;
    }
    if(majorOpcode == 117) {
      return true;
    }
    if(majorOpcode == 118) {
      return true;
    }
    if(majorOpcode == 119) {
      return true;
    }
    if(majorOpcode == 127) {
      return true;
    }
    return false;
  }

  @Override
  public boolean supportedEvent(byte number) {
    if(number - firstEvent == 2) {
      return true;
    }
    if(number - firstEvent == 3) {
      return true;
    }
    if(number - firstEvent == 4) {
      return true;
    }
    if(number - firstEvent == 5) {
      return true;
    }
    if(number - firstEvent == 6) {
      return true;
    }
    if(number - firstEvent == 7) {
      return true;
    }
    if(number - firstEvent == 8) {
      return true;
    }
    if(number - firstEvent == 9) {
      return true;
    }
    if(number - firstEvent == 10) {
      return true;
    }
    if(number - firstEvent == 11) {
      return true;
    }
    if(number - firstEvent == 12) {
      return true;
    }
    if(number - firstEvent == 13) {
      return true;
    }
    if(number - firstEvent == 14) {
      return true;
    }
    if(number - firstEvent == 15) {
      return true;
    }
    if(number - firstEvent == 16) {
      return true;
    }
    if(number - firstEvent == 17) {
      return true;
    }
    if(number - firstEvent == 18) {
      return true;
    }
    if(number - firstEvent == 19) {
      return true;
    }
    if(number - firstEvent == 20) {
      return true;
    }
    if(number - firstEvent == 21) {
      return true;
    }
    if(number - firstEvent == 22) {
      return true;
    }
    if(number - firstEvent == 23) {
      return true;
    }
    if(number - firstEvent == 24) {
      return true;
    }
    if(number - firstEvent == 25) {
      return true;
    }
    if(number - firstEvent == 26) {
      return true;
    }
    if(number - firstEvent == 27) {
      return true;
    }
    if(number - firstEvent == 28) {
      return true;
    }
    if(number - firstEvent == 29) {
      return true;
    }
    if(number - firstEvent == 30) {
      return true;
    }
    if(number - firstEvent == 31) {
      return true;
    }
    if(number - firstEvent == 32) {
      return true;
    }
    if(number - firstEvent == 33) {
      return true;
    }
    if(number - firstEvent == 34) {
      return true;
    }
    return false;
  }

  @Override
  public boolean supportedError(byte code) {
    if(code - firstError == 1) {
      return true;
    }
    if(code - firstError == 2) {
      return true;
    }
    if(code - firstError == 3) {
      return true;
    }
    if(code - firstError == 4) {
      return true;
    }
    if(code - firstError == 5) {
      return true;
    }
    if(code - firstError == 6) {
      return true;
    }
    if(code - firstError == 7) {
      return true;
    }
    if(code - firstError == 8) {
      return true;
    }
    if(code - firstError == 9) {
      return true;
    }
    if(code - firstError == 10) {
      return true;
    }
    if(code - firstError == 11) {
      return true;
    }
    if(code - firstError == 12) {
      return true;
    }
    if(code - firstError == 13) {
      return true;
    }
    if(code - firstError == 14) {
      return true;
    }
    if(code - firstError == 15) {
      return true;
    }
    if(code - firstError == 16) {
      return true;
    }
    if(code - firstError == 17) {
      return true;
    }
    return false;
  }

  @Override
  public XRequest readRequest(byte majorOpcode, byte minorOpcode, X11Input in) throws IOException {
    if(majorOpcode == CreateWindow.OPCODE) {
      return CreateWindow.readCreateWindow(in);
    }
    if(majorOpcode == ChangeWindowAttributes.OPCODE) {
      return ChangeWindowAttributes.readChangeWindowAttributes(in);
    }
    if(majorOpcode == GetWindowAttributes.OPCODE) {
      return GetWindowAttributes.readGetWindowAttributes(in);
    }
    if(majorOpcode == DestroyWindow.OPCODE) {
      return DestroyWindow.readDestroyWindow(in);
    }
    if(majorOpcode == DestroySubwindows.OPCODE) {
      return DestroySubwindows.readDestroySubwindows(in);
    }
    if(majorOpcode == ChangeSaveSet.OPCODE) {
      return ChangeSaveSet.readChangeSaveSet(in);
    }
    if(majorOpcode == ReparentWindow.OPCODE) {
      return ReparentWindow.readReparentWindow(in);
    }
    if(majorOpcode == MapWindow.OPCODE) {
      return MapWindow.readMapWindow(in);
    }
    if(majorOpcode == MapSubwindows.OPCODE) {
      return MapSubwindows.readMapSubwindows(in);
    }
    if(majorOpcode == UnmapWindow.OPCODE) {
      return UnmapWindow.readUnmapWindow(in);
    }
    if(majorOpcode == UnmapSubwindows.OPCODE) {
      return UnmapSubwindows.readUnmapSubwindows(in);
    }
    if(majorOpcode == ConfigureWindow.OPCODE) {
      return ConfigureWindow.readConfigureWindow(in);
    }
    if(majorOpcode == CirculateWindow.OPCODE) {
      return CirculateWindow.readCirculateWindow(in);
    }
    if(majorOpcode == GetGeometry.OPCODE) {
      return GetGeometry.readGetGeometry(in);
    }
    if(majorOpcode == QueryTree.OPCODE) {
      return QueryTree.readQueryTree(in);
    }
    if(majorOpcode == InternAtom.OPCODE) {
      return InternAtom.readInternAtom(in);
    }
    if(majorOpcode == GetAtomName.OPCODE) {
      return GetAtomName.readGetAtomName(in);
    }
    if(majorOpcode == ChangeProperty.OPCODE) {
      return ChangeProperty.readChangeProperty(in);
    }
    if(majorOpcode == DeleteProperty.OPCODE) {
      return DeleteProperty.readDeleteProperty(in);
    }
    if(majorOpcode == GetProperty.OPCODE) {
      return GetProperty.readGetProperty(in);
    }
    if(majorOpcode == ListProperties.OPCODE) {
      return ListProperties.readListProperties(in);
    }
    if(majorOpcode == SetSelectionOwner.OPCODE) {
      return SetSelectionOwner.readSetSelectionOwner(in);
    }
    if(majorOpcode == GetSelectionOwner.OPCODE) {
      return GetSelectionOwner.readGetSelectionOwner(in);
    }
    if(majorOpcode == ConvertSelection.OPCODE) {
      return ConvertSelection.readConvertSelection(in);
    }
    if(majorOpcode == SendEvent.OPCODE) {
      return SendEvent.readSendEvent(in);
    }
    if(majorOpcode == GrabPointer.OPCODE) {
      return GrabPointer.readGrabPointer(in);
    }
    if(majorOpcode == UngrabPointer.OPCODE) {
      return UngrabPointer.readUngrabPointer(in);
    }
    if(majorOpcode == GrabButton.OPCODE) {
      return GrabButton.readGrabButton(in);
    }
    if(majorOpcode == UngrabButton.OPCODE) {
      return UngrabButton.readUngrabButton(in);
    }
    if(majorOpcode == ChangeActivePointerGrab.OPCODE) {
      return ChangeActivePointerGrab.readChangeActivePointerGrab(in);
    }
    if(majorOpcode == GrabKeyboard.OPCODE) {
      return GrabKeyboard.readGrabKeyboard(in);
    }
    if(majorOpcode == UngrabKeyboard.OPCODE) {
      return UngrabKeyboard.readUngrabKeyboard(in);
    }
    if(majorOpcode == GrabKey.OPCODE) {
      return GrabKey.readGrabKey(in);
    }
    if(majorOpcode == UngrabKey.OPCODE) {
      return UngrabKey.readUngrabKey(in);
    }
    if(majorOpcode == AllowEvents.OPCODE) {
      return AllowEvents.readAllowEvents(in);
    }
    if(majorOpcode == GrabServer.OPCODE) {
      return GrabServer.readGrabServer(in);
    }
    if(majorOpcode == UngrabServer.OPCODE) {
      return UngrabServer.readUngrabServer(in);
    }
    if(majorOpcode == QueryPointer.OPCODE) {
      return QueryPointer.readQueryPointer(in);
    }
    if(majorOpcode == GetMotionEvents.OPCODE) {
      return GetMotionEvents.readGetMotionEvents(in);
    }
    if(majorOpcode == TranslateCoordinates.OPCODE) {
      return TranslateCoordinates.readTranslateCoordinates(in);
    }
    if(majorOpcode == WarpPointer.OPCODE) {
      return WarpPointer.readWarpPointer(in);
    }
    if(majorOpcode == SetInputFocus.OPCODE) {
      return SetInputFocus.readSetInputFocus(in);
    }
    if(majorOpcode == GetInputFocus.OPCODE) {
      return GetInputFocus.readGetInputFocus(in);
    }
    if(majorOpcode == QueryKeymap.OPCODE) {
      return QueryKeymap.readQueryKeymap(in);
    }
    if(majorOpcode == OpenFont.OPCODE) {
      return OpenFont.readOpenFont(in);
    }
    if(majorOpcode == CloseFont.OPCODE) {
      return CloseFont.readCloseFont(in);
    }
    if(majorOpcode == QueryFont.OPCODE) {
      return QueryFont.readQueryFont(in);
    }
    if(majorOpcode == QueryTextExtents.OPCODE) {
      return QueryTextExtents.readQueryTextExtents(in);
    }
    if(majorOpcode == ListFonts.OPCODE) {
      return ListFonts.readListFonts(in);
    }
    if(majorOpcode == ListFontsWithInfo.OPCODE) {
      return ListFontsWithInfo.readListFontsWithInfo(in);
    }
    if(majorOpcode == SetFontPath.OPCODE) {
      return SetFontPath.readSetFontPath(in);
    }
    if(majorOpcode == GetFontPath.OPCODE) {
      return GetFontPath.readGetFontPath(in);
    }
    if(majorOpcode == CreatePixmap.OPCODE) {
      return CreatePixmap.readCreatePixmap(in);
    }
    if(majorOpcode == FreePixmap.OPCODE) {
      return FreePixmap.readFreePixmap(in);
    }
    if(majorOpcode == CreateGC.OPCODE) {
      return CreateGC.readCreateGC(in);
    }
    if(majorOpcode == ChangeGC.OPCODE) {
      return ChangeGC.readChangeGC(in);
    }
    if(majorOpcode == CopyGC.OPCODE) {
      return CopyGC.readCopyGC(in);
    }
    if(majorOpcode == SetDashes.OPCODE) {
      return SetDashes.readSetDashes(in);
    }
    if(majorOpcode == SetClipRectangles.OPCODE) {
      return SetClipRectangles.readSetClipRectangles(in);
    }
    if(majorOpcode == FreeGC.OPCODE) {
      return FreeGC.readFreeGC(in);
    }
    if(majorOpcode == ClearArea.OPCODE) {
      return ClearArea.readClearArea(in);
    }
    if(majorOpcode == CopyArea.OPCODE) {
      return CopyArea.readCopyArea(in);
    }
    if(majorOpcode == CopyPlane.OPCODE) {
      return CopyPlane.readCopyPlane(in);
    }
    if(majorOpcode == PolyPoint.OPCODE) {
      return PolyPoint.readPolyPoint(in);
    }
    if(majorOpcode == PolyLine.OPCODE) {
      return PolyLine.readPolyLine(in);
    }
    if(majorOpcode == PolySegment.OPCODE) {
      return PolySegment.readPolySegment(in);
    }
    if(majorOpcode == PolyRectangle.OPCODE) {
      return PolyRectangle.readPolyRectangle(in);
    }
    if(majorOpcode == PolyArc.OPCODE) {
      return PolyArc.readPolyArc(in);
    }
    if(majorOpcode == FillPoly.OPCODE) {
      return FillPoly.readFillPoly(in);
    }
    if(majorOpcode == PolyFillRectangle.OPCODE) {
      return PolyFillRectangle.readPolyFillRectangle(in);
    }
    if(majorOpcode == PolyFillArc.OPCODE) {
      return PolyFillArc.readPolyFillArc(in);
    }
    if(majorOpcode == PutImage.OPCODE) {
      return PutImage.readPutImage(in);
    }
    if(majorOpcode == GetImage.OPCODE) {
      return GetImage.readGetImage(in);
    }
    if(majorOpcode == PolyText8.OPCODE) {
      return PolyText8.readPolyText8(in);
    }
    if(majorOpcode == PolyText16.OPCODE) {
      return PolyText16.readPolyText16(in);
    }
    if(majorOpcode == ImageText8.OPCODE) {
      return ImageText8.readImageText8(in);
    }
    if(majorOpcode == ImageText16.OPCODE) {
      return ImageText16.readImageText16(in);
    }
    if(majorOpcode == CreateColormap.OPCODE) {
      return CreateColormap.readCreateColormap(in);
    }
    if(majorOpcode == FreeColormap.OPCODE) {
      return FreeColormap.readFreeColormap(in);
    }
    if(majorOpcode == CopyColormapAndFree.OPCODE) {
      return CopyColormapAndFree.readCopyColormapAndFree(in);
    }
    if(majorOpcode == InstallColormap.OPCODE) {
      return InstallColormap.readInstallColormap(in);
    }
    if(majorOpcode == UninstallColormap.OPCODE) {
      return UninstallColormap.readUninstallColormap(in);
    }
    if(majorOpcode == ListInstalledColormaps.OPCODE) {
      return ListInstalledColormaps.readListInstalledColormaps(in);
    }
    if(majorOpcode == AllocColor.OPCODE) {
      return AllocColor.readAllocColor(in);
    }
    if(majorOpcode == AllocNamedColor.OPCODE) {
      return AllocNamedColor.readAllocNamedColor(in);
    }
    if(majorOpcode == AllocColorCells.OPCODE) {
      return AllocColorCells.readAllocColorCells(in);
    }
    if(majorOpcode == AllocColorPlanes.OPCODE) {
      return AllocColorPlanes.readAllocColorPlanes(in);
    }
    if(majorOpcode == FreeColors.OPCODE) {
      return FreeColors.readFreeColors(in);
    }
    if(majorOpcode == StoreColors.OPCODE) {
      return StoreColors.readStoreColors(in);
    }
    if(majorOpcode == StoreNamedColor.OPCODE) {
      return StoreNamedColor.readStoreNamedColor(in);
    }
    if(majorOpcode == QueryColors.OPCODE) {
      return QueryColors.readQueryColors(in);
    }
    if(majorOpcode == LookupColor.OPCODE) {
      return LookupColor.readLookupColor(in);
    }
    if(majorOpcode == CreateCursor.OPCODE) {
      return CreateCursor.readCreateCursor(in);
    }
    if(majorOpcode == CreateGlyphCursor.OPCODE) {
      return CreateGlyphCursor.readCreateGlyphCursor(in);
    }
    if(majorOpcode == FreeCursor.OPCODE) {
      return FreeCursor.readFreeCursor(in);
    }
    if(majorOpcode == RecolorCursor.OPCODE) {
      return RecolorCursor.readRecolorCursor(in);
    }
    if(majorOpcode == QueryBestSize.OPCODE) {
      return QueryBestSize.readQueryBestSize(in);
    }
    if(majorOpcode == QueryExtension.OPCODE) {
      return QueryExtension.readQueryExtension(in);
    }
    if(majorOpcode == ListExtensions.OPCODE) {
      return ListExtensions.readListExtensions(in);
    }
    if(majorOpcode == ChangeKeyboardMapping.OPCODE) {
      return ChangeKeyboardMapping.readChangeKeyboardMapping(in);
    }
    if(majorOpcode == GetKeyboardMapping.OPCODE) {
      return GetKeyboardMapping.readGetKeyboardMapping(in);
    }
    if(majorOpcode == ChangeKeyboardControl.OPCODE) {
      return ChangeKeyboardControl.readChangeKeyboardControl(in);
    }
    if(majorOpcode == GetKeyboardControl.OPCODE) {
      return GetKeyboardControl.readGetKeyboardControl(in);
    }
    if(majorOpcode == Bell.OPCODE) {
      return Bell.readBell(in);
    }
    if(majorOpcode == ChangePointerControl.OPCODE) {
      return ChangePointerControl.readChangePointerControl(in);
    }
    if(majorOpcode == GetPointerControl.OPCODE) {
      return GetPointerControl.readGetPointerControl(in);
    }
    if(majorOpcode == SetScreenSaver.OPCODE) {
      return SetScreenSaver.readSetScreenSaver(in);
    }
    if(majorOpcode == GetScreenSaver.OPCODE) {
      return GetScreenSaver.readGetScreenSaver(in);
    }
    if(majorOpcode == ChangeHosts.OPCODE) {
      return ChangeHosts.readChangeHosts(in);
    }
    if(majorOpcode == ListHosts.OPCODE) {
      return ListHosts.readListHosts(in);
    }
    if(majorOpcode == SetAccessControl.OPCODE) {
      return SetAccessControl.readSetAccessControl(in);
    }
    if(majorOpcode == SetCloseDownMode.OPCODE) {
      return SetCloseDownMode.readSetCloseDownMode(in);
    }
    if(majorOpcode == KillClient.OPCODE) {
      return KillClient.readKillClient(in);
    }
    if(majorOpcode == RotateProperties.OPCODE) {
      return RotateProperties.readRotateProperties(in);
    }
    if(majorOpcode == ForceScreenSaver.OPCODE) {
      return ForceScreenSaver.readForceScreenSaver(in);
    }
    if(majorOpcode == SetPointerMapping.OPCODE) {
      return SetPointerMapping.readSetPointerMapping(in);
    }
    if(majorOpcode == GetPointerMapping.OPCODE) {
      return GetPointerMapping.readGetPointerMapping(in);
    }
    if(majorOpcode == SetModifierMapping.OPCODE) {
      return SetModifierMapping.readSetModifierMapping(in);
    }
    if(majorOpcode == GetModifierMapping.OPCODE) {
      return GetModifierMapping.readGetModifierMapping(in);
    }
    if(majorOpcode == NoOperation.OPCODE) {
      return NoOperation.readNoOperation(in);
    }
    throw new IllegalArgumentException("majorOpcode " + majorOpcode + ", minorOpcode " + minorOpcode + " is not supported");
  }

  @Override
  public XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException {
    if(number - firstEvent == 2) {
      return KeyPressEvent.readKeyPressEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 3) {
      return KeyReleaseEvent.readKeyReleaseEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 4) {
      return ButtonPressEvent.readButtonPressEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 5) {
      return ButtonReleaseEvent.readButtonReleaseEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 6) {
      return MotionNotifyEvent.readMotionNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 7) {
      return EnterNotifyEvent.readEnterNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 8) {
      return LeaveNotifyEvent.readLeaveNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 9) {
      return FocusInEvent.readFocusInEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 10) {
      return FocusOutEvent.readFocusOutEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 11) {
      return KeymapNotifyEvent.readKeymapNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 12) {
      return ExposeEvent.readExposeEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 13) {
      return GraphicsExposureEvent.readGraphicsExposureEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 14) {
      return NoExposureEvent.readNoExposureEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 15) {
      return VisibilityNotifyEvent.readVisibilityNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 16) {
      return CreateNotifyEvent.readCreateNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 17) {
      return DestroyNotifyEvent.readDestroyNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 18) {
      return UnmapNotifyEvent.readUnmapNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 19) {
      return MapNotifyEvent.readMapNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 20) {
      return MapRequestEvent.readMapRequestEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 21) {
      return ReparentNotifyEvent.readReparentNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 22) {
      return ConfigureNotifyEvent.readConfigureNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 23) {
      return ConfigureRequestEvent.readConfigureRequestEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 24) {
      return GravityNotifyEvent.readGravityNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 25) {
      return ResizeRequestEvent.readResizeRequestEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 26) {
      return CirculateNotifyEvent.readCirculateNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 27) {
      return CirculateRequestEvent.readCirculateRequestEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 28) {
      return PropertyNotifyEvent.readPropertyNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 29) {
      return SelectionClearEvent.readSelectionClearEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 30) {
      return SelectionRequestEvent.readSelectionRequestEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 31) {
      return SelectionNotifyEvent.readSelectionNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 32) {
      return ColormapNotifyEvent.readColormapNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 33) {
      return ClientMessageEvent.readClientMessageEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 34) {
      return MappingNotifyEvent.readMappingNotifyEvent(firstEvent, sentEvent, in);
    }
    throw new IllegalArgumentException("number " + number + " is not supported");
  }

  @Override
  public XError readError(byte code, X11Input in) throws IOException {
    if(code - firstError == 1) {
      return RequestError.readRequestError(firstError, in);
    }
    if(code - firstError == 2) {
      return ValueError.readValueError(firstError, in);
    }
    if(code - firstError == 3) {
      return WindowError.readWindowError(firstError, in);
    }
    if(code - firstError == 4) {
      return PixmapError.readPixmapError(firstError, in);
    }
    if(code - firstError == 5) {
      return AtomError.readAtomError(firstError, in);
    }
    if(code - firstError == 6) {
      return CursorError.readCursorError(firstError, in);
    }
    if(code - firstError == 7) {
      return FontError.readFontError(firstError, in);
    }
    if(code - firstError == 8) {
      return MatchError.readMatchError(firstError, in);
    }
    if(code - firstError == 9) {
      return DrawableError.readDrawableError(firstError, in);
    }
    if(code - firstError == 10) {
      return AccessError.readAccessError(firstError, in);
    }
    if(code - firstError == 11) {
      return AllocError.readAllocError(firstError, in);
    }
    if(code - firstError == 12) {
      return ColormapError.readColormapError(firstError, in);
    }
    if(code - firstError == 13) {
      return GContextError.readGContextError(firstError, in);
    }
    if(code - firstError == 14) {
      return IDChoiceError.readIDChoiceError(firstError, in);
    }
    if(code - firstError == 15) {
      return NameError.readNameError(firstError, in);
    }
    if(code - firstError == 16) {
      return LengthError.readLengthError(firstError, in);
    }
    if(code - firstError == 17) {
      return ImplementationError.readImplementationError(firstError, in);
    }
    throw new IllegalArgumentException("code " + code + " is not supported");
  }

  @Override
  public XGenericEvent readGenericEvent(byte firstEventOffset, boolean sentEvent, byte extension,
      short sequenceNumber, int length, short eventType, X11Input in) throws IOException {
    if(eventType == 35) {
      return GeGenericEvent.readGeGenericEvent(firstEventOffset, sentEvent, extension, sequenceNumber, length, eventType, in);
    }
    throw new IllegalArgumentException("eventType " + eventType + " is not supported");
  }
}
