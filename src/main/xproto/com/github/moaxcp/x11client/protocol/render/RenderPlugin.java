package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XError;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import com.github.moaxcp.x11client.protocol.XProtocolPlugin;
import com.github.moaxcp.x11client.protocol.XRequest;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

public class RenderPlugin implements XProtocolPlugin {
  public static final String NAME = "RENDER";

  @Getter
  private byte majorVersion = 0;

  @Getter
  private byte minorVersion = 11;

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
    if(request instanceof QueryPictFormats) {
      return true;
    }
    if(request instanceof QueryPictIndexValues) {
      return true;
    }
    if(request instanceof CreatePicture) {
      return true;
    }
    if(request instanceof ChangePicture) {
      return true;
    }
    if(request instanceof SetPictureClipRectangles) {
      return true;
    }
    if(request instanceof FreePicture) {
      return true;
    }
    if(request instanceof Composite) {
      return true;
    }
    if(request instanceof Trapezoids) {
      return true;
    }
    if(request instanceof Triangles) {
      return true;
    }
    if(request instanceof TriStrip) {
      return true;
    }
    if(request instanceof TriFan) {
      return true;
    }
    if(request instanceof CreateGlyphSet) {
      return true;
    }
    if(request instanceof ReferenceGlyphSet) {
      return true;
    }
    if(request instanceof FreeGlyphSet) {
      return true;
    }
    if(request instanceof AddGlyphs) {
      return true;
    }
    if(request instanceof FreeGlyphs) {
      return true;
    }
    if(request instanceof CompositeGlyphs8) {
      return true;
    }
    if(request instanceof CompositeGlyphs16) {
      return true;
    }
    if(request instanceof CompositeGlyphs32) {
      return true;
    }
    if(request instanceof FillRectangles) {
      return true;
    }
    if(request instanceof CreateCursor) {
      return true;
    }
    if(request instanceof SetPictureTransform) {
      return true;
    }
    if(request instanceof QueryFilters) {
      return true;
    }
    if(request instanceof SetPictureFilter) {
      return true;
    }
    if(request instanceof CreateAnimCursor) {
      return true;
    }
    if(request instanceof AddTraps) {
      return true;
    }
    if(request instanceof CreateSolidFill) {
      return true;
    }
    if(request instanceof CreateLinearGradient) {
      return true;
    }
    if(request instanceof CreateRadialGradient) {
      return true;
    }
    if(request instanceof CreateConicalGradient) {
      return true;
    }
    return false;
  }

  @Override
  public boolean supportedEvent(byte number) {
    return false;
  }

  @Override
  public boolean supportedError(byte code) {
    if(code - firstError == 0) {
      return true;
    }
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
    return false;
  }

  @Override
  public XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException {
    throw new IllegalArgumentException("number " + number + " is not supported");
  }

  @Override
  public XError readError(byte code, X11Input in) throws IOException {
    if(code - firstError == 0) {
      return PictFormatError.readPictFormatError(firstError, in);
    }
    if(code - firstError == 1) {
      return PictureError.readPictureError(firstError, in);
    }
    if(code - firstError == 2) {
      return PictOpError.readPictOpError(firstError, in);
    }
    if(code - firstError == 3) {
      return GlyphSetError.readGlyphSetError(firstError, in);
    }
    if(code - firstError == 4) {
      return GlyphError.readGlyphError(firstError, in);
    }
    throw new IllegalArgumentException("code " + code + " is not supported");
  }

  @Override
  public XGenericEvent readGenericEvent(boolean sentEvent, byte extension, short sequenceNumber,
      int length, short eventType, X11Input in) throws IOException {
    throw new IllegalArgumentException("eventType " + eventType + " is not supported");
  }
}
