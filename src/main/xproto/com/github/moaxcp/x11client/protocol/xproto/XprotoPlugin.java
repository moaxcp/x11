package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XError;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import com.github.moaxcp.x11client.protocol.XProtocolPlugin;
import com.github.moaxcp.x11client.protocol.XRequest;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

public class XprotoPlugin implements XProtocolPlugin {
  public static final String NAME = "";

  @Getter
  private byte majorVersion = 0;

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
    if(request instanceof CreateWindow) {
      return true;
    }
    if(request instanceof ChangeWindowAttributes) {
      return true;
    }
    if(request instanceof GetWindowAttributes) {
      return true;
    }
    if(request instanceof DestroyWindow) {
      return true;
    }
    if(request instanceof DestroySubwindows) {
      return true;
    }
    if(request instanceof ChangeSaveSet) {
      return true;
    }
    if(request instanceof ReparentWindow) {
      return true;
    }
    if(request instanceof MapWindow) {
      return true;
    }
    if(request instanceof MapSubwindows) {
      return true;
    }
    if(request instanceof UnmapWindow) {
      return true;
    }
    if(request instanceof UnmapSubwindows) {
      return true;
    }
    if(request instanceof ConfigureWindow) {
      return true;
    }
    if(request instanceof CirculateWindow) {
      return true;
    }
    if(request instanceof GetGeometry) {
      return true;
    }
    if(request instanceof QueryTree) {
      return true;
    }
    if(request instanceof InternAtom) {
      return true;
    }
    if(request instanceof GetAtomName) {
      return true;
    }
    if(request instanceof ChangeProperty) {
      return true;
    }
    if(request instanceof DeleteProperty) {
      return true;
    }
    if(request instanceof GetProperty) {
      return true;
    }
    if(request instanceof ListProperties) {
      return true;
    }
    if(request instanceof SetSelectionOwner) {
      return true;
    }
    if(request instanceof GetSelectionOwner) {
      return true;
    }
    if(request instanceof ConvertSelection) {
      return true;
    }
    if(request instanceof SendEvent) {
      return true;
    }
    if(request instanceof GrabPointer) {
      return true;
    }
    if(request instanceof UngrabPointer) {
      return true;
    }
    if(request instanceof GrabButton) {
      return true;
    }
    if(request instanceof UngrabButton) {
      return true;
    }
    if(request instanceof ChangeActivePointerGrab) {
      return true;
    }
    if(request instanceof GrabKeyboard) {
      return true;
    }
    if(request instanceof UngrabKeyboard) {
      return true;
    }
    if(request instanceof GrabKey) {
      return true;
    }
    if(request instanceof UngrabKey) {
      return true;
    }
    if(request instanceof AllowEvents) {
      return true;
    }
    if(request instanceof GrabServer) {
      return true;
    }
    if(request instanceof UngrabServer) {
      return true;
    }
    if(request instanceof QueryPointer) {
      return true;
    }
    if(request instanceof GetMotionEvents) {
      return true;
    }
    if(request instanceof TranslateCoordinates) {
      return true;
    }
    if(request instanceof WarpPointer) {
      return true;
    }
    if(request instanceof SetInputFocus) {
      return true;
    }
    if(request instanceof GetInputFocus) {
      return true;
    }
    if(request instanceof QueryKeymap) {
      return true;
    }
    if(request instanceof OpenFont) {
      return true;
    }
    if(request instanceof CloseFont) {
      return true;
    }
    if(request instanceof QueryFont) {
      return true;
    }
    if(request instanceof QueryTextExtents) {
      return true;
    }
    if(request instanceof ListFonts) {
      return true;
    }
    if(request instanceof ListFontsWithInfo) {
      return true;
    }
    if(request instanceof SetFontPath) {
      return true;
    }
    if(request instanceof GetFontPath) {
      return true;
    }
    if(request instanceof CreatePixmap) {
      return true;
    }
    if(request instanceof FreePixmap) {
      return true;
    }
    if(request instanceof CreateGC) {
      return true;
    }
    if(request instanceof ChangeGC) {
      return true;
    }
    if(request instanceof CopyGC) {
      return true;
    }
    if(request instanceof SetDashes) {
      return true;
    }
    if(request instanceof SetClipRectangles) {
      return true;
    }
    if(request instanceof FreeGC) {
      return true;
    }
    if(request instanceof ClearArea) {
      return true;
    }
    if(request instanceof CopyArea) {
      return true;
    }
    if(request instanceof CopyPlane) {
      return true;
    }
    if(request instanceof PolyPoint) {
      return true;
    }
    if(request instanceof PolyLine) {
      return true;
    }
    if(request instanceof PolySegment) {
      return true;
    }
    if(request instanceof PolyRectangle) {
      return true;
    }
    if(request instanceof PolyArc) {
      return true;
    }
    if(request instanceof FillPoly) {
      return true;
    }
    if(request instanceof PolyFillRectangle) {
      return true;
    }
    if(request instanceof PolyFillArc) {
      return true;
    }
    if(request instanceof PutImage) {
      return true;
    }
    if(request instanceof GetImage) {
      return true;
    }
    if(request instanceof PolyText8) {
      return true;
    }
    if(request instanceof PolyText16) {
      return true;
    }
    if(request instanceof ImageText8) {
      return true;
    }
    if(request instanceof ImageText16) {
      return true;
    }
    if(request instanceof CreateColormap) {
      return true;
    }
    if(request instanceof FreeColormap) {
      return true;
    }
    if(request instanceof CopyColormapAndFree) {
      return true;
    }
    if(request instanceof InstallColormap) {
      return true;
    }
    if(request instanceof UninstallColormap) {
      return true;
    }
    if(request instanceof ListInstalledColormaps) {
      return true;
    }
    if(request instanceof AllocColor) {
      return true;
    }
    if(request instanceof AllocNamedColor) {
      return true;
    }
    if(request instanceof AllocColorCells) {
      return true;
    }
    if(request instanceof AllocColorPlanes) {
      return true;
    }
    if(request instanceof FreeColors) {
      return true;
    }
    if(request instanceof StoreColors) {
      return true;
    }
    if(request instanceof StoreNamedColor) {
      return true;
    }
    if(request instanceof QueryColors) {
      return true;
    }
    if(request instanceof LookupColor) {
      return true;
    }
    if(request instanceof CreateCursor) {
      return true;
    }
    if(request instanceof CreateGlyphCursor) {
      return true;
    }
    if(request instanceof FreeCursor) {
      return true;
    }
    if(request instanceof RecolorCursor) {
      return true;
    }
    if(request instanceof QueryBestSize) {
      return true;
    }
    if(request instanceof QueryExtension) {
      return true;
    }
    if(request instanceof ListExtensions) {
      return true;
    }
    if(request instanceof ChangeKeyboardMapping) {
      return true;
    }
    if(request instanceof GetKeyboardMapping) {
      return true;
    }
    if(request instanceof ChangeKeyboardControl) {
      return true;
    }
    if(request instanceof GetKeyboardControl) {
      return true;
    }
    if(request instanceof Bell) {
      return true;
    }
    if(request instanceof ChangePointerControl) {
      return true;
    }
    if(request instanceof GetPointerControl) {
      return true;
    }
    if(request instanceof SetScreenSaver) {
      return true;
    }
    if(request instanceof GetScreenSaver) {
      return true;
    }
    if(request instanceof ChangeHosts) {
      return true;
    }
    if(request instanceof ListHosts) {
      return true;
    }
    if(request instanceof SetAccessControl) {
      return true;
    }
    if(request instanceof SetCloseDownMode) {
      return true;
    }
    if(request instanceof KillClient) {
      return true;
    }
    if(request instanceof RotateProperties) {
      return true;
    }
    if(request instanceof ForceScreenSaver) {
      return true;
    }
    if(request instanceof SetPointerMapping) {
      return true;
    }
    if(request instanceof GetPointerMapping) {
      return true;
    }
    if(request instanceof SetModifierMapping) {
      return true;
    }
    if(request instanceof GetModifierMapping) {
      return true;
    }
    if(request instanceof NoOperation) {
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
  public XGenericEvent readGenericEvent(boolean sentEvent, byte extension, short sequenceNumber,
      int length, short eventType, X11Input in) throws IOException {
    if(eventType == 35) {
    }
    throw new IllegalArgumentException("eventType " + eventType + " is not supported");
  }
}
