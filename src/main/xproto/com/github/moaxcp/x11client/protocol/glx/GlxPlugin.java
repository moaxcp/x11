package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.XError;
import com.github.moaxcp.x11client.protocol.XEvent;
import com.github.moaxcp.x11client.protocol.XGenericEvent;
import com.github.moaxcp.x11client.protocol.XProtocolPlugin;
import com.github.moaxcp.x11client.protocol.XRequest;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

public class GlxPlugin implements XProtocolPlugin {
  public static final String NAME = "GLX";

  @Getter
  private byte majorVersion = 1;

  @Getter
  private byte minorVersion = 4;

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
    if(request instanceof Render) {
      return true;
    }
    if(request instanceof RenderLarge) {
      return true;
    }
    if(request instanceof CreateContext) {
      return true;
    }
    if(request instanceof DestroyContext) {
      return true;
    }
    if(request instanceof MakeCurrent) {
      return true;
    }
    if(request instanceof IsDirect) {
      return true;
    }
    if(request instanceof QueryVersion) {
      return true;
    }
    if(request instanceof WaitGL) {
      return true;
    }
    if(request instanceof WaitX) {
      return true;
    }
    if(request instanceof CopyContext) {
      return true;
    }
    if(request instanceof SwapBuffers) {
      return true;
    }
    if(request instanceof UseXFont) {
      return true;
    }
    if(request instanceof CreateGLXPixmap) {
      return true;
    }
    if(request instanceof GetVisualConfigs) {
      return true;
    }
    if(request instanceof DestroyGLXPixmap) {
      return true;
    }
    if(request instanceof VendorPrivate) {
      return true;
    }
    if(request instanceof VendorPrivateWithReply) {
      return true;
    }
    if(request instanceof QueryExtensionsString) {
      return true;
    }
    if(request instanceof QueryServerString) {
      return true;
    }
    if(request instanceof ClientInfo) {
      return true;
    }
    if(request instanceof GetFBConfigs) {
      return true;
    }
    if(request instanceof CreatePixmap) {
      return true;
    }
    if(request instanceof DestroyPixmap) {
      return true;
    }
    if(request instanceof CreateNewContext) {
      return true;
    }
    if(request instanceof QueryContext) {
      return true;
    }
    if(request instanceof MakeContextCurrent) {
      return true;
    }
    if(request instanceof CreatePbuffer) {
      return true;
    }
    if(request instanceof DestroyPbuffer) {
      return true;
    }
    if(request instanceof GetDrawableAttributes) {
      return true;
    }
    if(request instanceof ChangeDrawableAttributes) {
      return true;
    }
    if(request instanceof CreateWindow) {
      return true;
    }
    if(request instanceof DeleteWindow) {
      return true;
    }
    if(request instanceof SetClientInfoARB) {
      return true;
    }
    if(request instanceof CreateContextAttribsARB) {
      return true;
    }
    if(request instanceof SetClientInfo2ARB) {
      return true;
    }
    if(request instanceof NewList) {
      return true;
    }
    if(request instanceof EndList) {
      return true;
    }
    if(request instanceof DeleteLists) {
      return true;
    }
    if(request instanceof GenLists) {
      return true;
    }
    if(request instanceof FeedbackBuffer) {
      return true;
    }
    if(request instanceof SelectBuffer) {
      return true;
    }
    if(request instanceof RenderMode) {
      return true;
    }
    if(request instanceof Finish) {
      return true;
    }
    if(request instanceof PixelStoref) {
      return true;
    }
    if(request instanceof PixelStorei) {
      return true;
    }
    if(request instanceof ReadPixels) {
      return true;
    }
    if(request instanceof GetBooleanv) {
      return true;
    }
    if(request instanceof GetClipPlane) {
      return true;
    }
    if(request instanceof GetDoublev) {
      return true;
    }
    if(request instanceof GetError) {
      return true;
    }
    if(request instanceof GetFloatv) {
      return true;
    }
    if(request instanceof GetIntegerv) {
      return true;
    }
    if(request instanceof GetLightfv) {
      return true;
    }
    if(request instanceof GetLightiv) {
      return true;
    }
    if(request instanceof GetMapdv) {
      return true;
    }
    if(request instanceof GetMapfv) {
      return true;
    }
    if(request instanceof GetMapiv) {
      return true;
    }
    if(request instanceof GetMaterialfv) {
      return true;
    }
    if(request instanceof GetMaterialiv) {
      return true;
    }
    if(request instanceof GetPixelMapfv) {
      return true;
    }
    if(request instanceof GetPixelMapuiv) {
      return true;
    }
    if(request instanceof GetPixelMapusv) {
      return true;
    }
    if(request instanceof GetPolygonStipple) {
      return true;
    }
    if(request instanceof GetString) {
      return true;
    }
    if(request instanceof GetTexEnvfv) {
      return true;
    }
    if(request instanceof GetTexEnviv) {
      return true;
    }
    if(request instanceof GetTexGendv) {
      return true;
    }
    if(request instanceof GetTexGenfv) {
      return true;
    }
    if(request instanceof GetTexGeniv) {
      return true;
    }
    if(request instanceof GetTexImage) {
      return true;
    }
    if(request instanceof GetTexParameterfv) {
      return true;
    }
    if(request instanceof GetTexParameteriv) {
      return true;
    }
    if(request instanceof GetTexLevelParameterfv) {
      return true;
    }
    if(request instanceof GetTexLevelParameteriv) {
      return true;
    }
    if(request instanceof IsEnabled) {
      return true;
    }
    if(request instanceof IsList) {
      return true;
    }
    if(request instanceof Flush) {
      return true;
    }
    if(request instanceof AreTexturesResident) {
      return true;
    }
    if(request instanceof DeleteTextures) {
      return true;
    }
    if(request instanceof GenTextures) {
      return true;
    }
    if(request instanceof IsTexture) {
      return true;
    }
    if(request instanceof GetColorTable) {
      return true;
    }
    if(request instanceof GetColorTableParameterfv) {
      return true;
    }
    if(request instanceof GetColorTableParameteriv) {
      return true;
    }
    if(request instanceof GetConvolutionFilter) {
      return true;
    }
    if(request instanceof GetConvolutionParameterfv) {
      return true;
    }
    if(request instanceof GetConvolutionParameteriv) {
      return true;
    }
    if(request instanceof GetSeparableFilter) {
      return true;
    }
    if(request instanceof GetHistogram) {
      return true;
    }
    if(request instanceof GetHistogramParameterfv) {
      return true;
    }
    if(request instanceof GetHistogramParameteriv) {
      return true;
    }
    if(request instanceof GetMinmax) {
      return true;
    }
    if(request instanceof GetMinmaxParameterfv) {
      return true;
    }
    if(request instanceof GetMinmaxParameteriv) {
      return true;
    }
    if(request instanceof GetCompressedTexImageARB) {
      return true;
    }
    if(request instanceof DeleteQueriesARB) {
      return true;
    }
    if(request instanceof GenQueriesARB) {
      return true;
    }
    if(request instanceof IsQueryARB) {
      return true;
    }
    if(request instanceof GetQueryivARB) {
      return true;
    }
    if(request instanceof GetQueryObjectivARB) {
      return true;
    }
    if(request instanceof GetQueryObjectuivARB) {
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
    if(code - firstError == -1) {
      return true;
    }
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
    return false;
  }

  @Override
  public XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException {
    if(number - firstEvent == 0) {
      return PbufferClobberEvent.readPbufferClobberEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 1) {
      return BufferSwapCompleteEvent.readBufferSwapCompleteEvent(firstEvent, sentEvent, in);
    }
    throw new IllegalArgumentException("number " + number + " is not supported");
  }

  @Override
  public XError readError(byte code, X11Input in) throws IOException {
    if(code - firstError == -1) {
      return GenericError.readGenericError(firstError, in);
    }
    if(code - firstError == 0) {
      return BadContextError.readBadContextError(firstError, in);
    }
    if(code - firstError == 1) {
      return BadContextStateError.readBadContextStateError(firstError, in);
    }
    if(code - firstError == 2) {
      return BadDrawableError.readBadDrawableError(firstError, in);
    }
    if(code - firstError == 3) {
      return BadPixmapError.readBadPixmapError(firstError, in);
    }
    if(code - firstError == 4) {
      return BadContextTagError.readBadContextTagError(firstError, in);
    }
    if(code - firstError == 5) {
      return BadCurrentWindowError.readBadCurrentWindowError(firstError, in);
    }
    if(code - firstError == 6) {
      return BadRenderRequestError.readBadRenderRequestError(firstError, in);
    }
    if(code - firstError == 7) {
      return BadLargeRequestError.readBadLargeRequestError(firstError, in);
    }
    if(code - firstError == 8) {
      return UnsupportedPrivateRequestError.readUnsupportedPrivateRequestError(firstError, in);
    }
    if(code - firstError == 9) {
      return BadFBConfigError.readBadFBConfigError(firstError, in);
    }
    if(code - firstError == 10) {
      return BadPbufferError.readBadPbufferError(firstError, in);
    }
    if(code - firstError == 11) {
      return BadCurrentDrawableError.readBadCurrentDrawableError(firstError, in);
    }
    if(code - firstError == 12) {
      return BadWindowError.readBadWindowError(firstError, in);
    }
    if(code - firstError == 13) {
      return GLXBadProfileARBError.readGLXBadProfileARBError(firstError, in);
    }
    throw new IllegalArgumentException("code " + code + " is not supported");
  }

  @Override
  public XGenericEvent readGenericEvent(boolean sentEvent, byte extension, short sequenceNumber,
      int length, short eventType, X11Input in) throws IOException {
    throw new IllegalArgumentException("eventType " + eventType + " is not supported");
  }
}
