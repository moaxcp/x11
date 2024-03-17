package com.github.moaxcp.x11.protocol.render;

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

public class RenderPlugin implements XProtocolPlugin {
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
    return "render";
  }

  public Optional<String> getExtensionXName() {
    return Optional.ofNullable("RENDER");
  }

  public Optional<String> getExtensionName() {
    return Optional.ofNullable("Render");
  }

  public Optional<Boolean> getExtensionMultiword() {
    return Optional.ofNullable(true);
  }

  public Optional<Byte> getMajorVersion() {
    return Optional.ofNullable((byte) 0);
  }

  public Optional<Byte> getMinorVersion() {
    return Optional.ofNullable((byte) 11);
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
    if(minorOpcode == 33) {
      return isMajorOpcode;
    }
    if(minorOpcode == 34) {
      return isMajorOpcode;
    }
    if(minorOpcode == 35) {
      return isMajorOpcode;
    }
    if(minorOpcode == 36) {
      return isMajorOpcode;
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
  public XRequest readRequest(byte majorOpcode, byte minorOpcode, X11Input in) throws IOException {
    if(minorOpcode == QueryVersion.OPCODE) {
      return QueryVersion.readQueryVersion(in);
    }
    if(minorOpcode == QueryPictFormats.OPCODE) {
      return QueryPictFormats.readQueryPictFormats(in);
    }
    if(minorOpcode == QueryPictIndexValues.OPCODE) {
      return QueryPictIndexValues.readQueryPictIndexValues(in);
    }
    if(minorOpcode == CreatePicture.OPCODE) {
      return CreatePicture.readCreatePicture(in);
    }
    if(minorOpcode == ChangePicture.OPCODE) {
      return ChangePicture.readChangePicture(in);
    }
    if(minorOpcode == SetPictureClipRectangles.OPCODE) {
      return SetPictureClipRectangles.readSetPictureClipRectangles(in);
    }
    if(minorOpcode == FreePicture.OPCODE) {
      return FreePicture.readFreePicture(in);
    }
    if(minorOpcode == Composite.OPCODE) {
      return Composite.readComposite(in);
    }
    if(minorOpcode == Trapezoids.OPCODE) {
      return Trapezoids.readTrapezoids(in);
    }
    if(minorOpcode == Triangles.OPCODE) {
      return Triangles.readTriangles(in);
    }
    if(minorOpcode == TriStrip.OPCODE) {
      return TriStrip.readTriStrip(in);
    }
    if(minorOpcode == TriFan.OPCODE) {
      return TriFan.readTriFan(in);
    }
    if(minorOpcode == CreateGlyphSet.OPCODE) {
      return CreateGlyphSet.readCreateGlyphSet(in);
    }
    if(minorOpcode == ReferenceGlyphSet.OPCODE) {
      return ReferenceGlyphSet.readReferenceGlyphSet(in);
    }
    if(minorOpcode == FreeGlyphSet.OPCODE) {
      return FreeGlyphSet.readFreeGlyphSet(in);
    }
    if(minorOpcode == AddGlyphs.OPCODE) {
      return AddGlyphs.readAddGlyphs(in);
    }
    if(minorOpcode == FreeGlyphs.OPCODE) {
      return FreeGlyphs.readFreeGlyphs(in);
    }
    if(minorOpcode == CompositeGlyphs8.OPCODE) {
      return CompositeGlyphs8.readCompositeGlyphs8(in);
    }
    if(minorOpcode == CompositeGlyphs16.OPCODE) {
      return CompositeGlyphs16.readCompositeGlyphs16(in);
    }
    if(minorOpcode == CompositeGlyphs32.OPCODE) {
      return CompositeGlyphs32.readCompositeGlyphs32(in);
    }
    if(minorOpcode == FillRectangles.OPCODE) {
      return FillRectangles.readFillRectangles(in);
    }
    if(minorOpcode == CreateCursor.OPCODE) {
      return CreateCursor.readCreateCursor(in);
    }
    if(minorOpcode == SetPictureTransform.OPCODE) {
      return SetPictureTransform.readSetPictureTransform(in);
    }
    if(minorOpcode == QueryFilters.OPCODE) {
      return QueryFilters.readQueryFilters(in);
    }
    if(minorOpcode == SetPictureFilter.OPCODE) {
      return SetPictureFilter.readSetPictureFilter(in);
    }
    if(minorOpcode == CreateAnimCursor.OPCODE) {
      return CreateAnimCursor.readCreateAnimCursor(in);
    }
    if(minorOpcode == AddTraps.OPCODE) {
      return AddTraps.readAddTraps(in);
    }
    if(minorOpcode == CreateSolidFill.OPCODE) {
      return CreateSolidFill.readCreateSolidFill(in);
    }
    if(minorOpcode == CreateLinearGradient.OPCODE) {
      return CreateLinearGradient.readCreateLinearGradient(in);
    }
    if(minorOpcode == CreateRadialGradient.OPCODE) {
      return CreateRadialGradient.readCreateRadialGradient(in);
    }
    if(minorOpcode == CreateConicalGradient.OPCODE) {
      return CreateConicalGradient.readCreateConicalGradient(in);
    }
    throw new IllegalArgumentException("majorOpcode " + majorOpcode + ", minorOpcode " + minorOpcode + " is not supported");
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
