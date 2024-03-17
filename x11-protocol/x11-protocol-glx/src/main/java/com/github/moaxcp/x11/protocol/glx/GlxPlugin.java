package com.github.moaxcp.x11.protocol.glx;

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

public class GlxPlugin implements XProtocolPlugin {
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
    return "glx";
  }

  public Optional<String> getExtensionXName() {
    return Optional.ofNullable("GLX");
  }

  public Optional<String> getExtensionName() {
    return Optional.ofNullable("Glx");
  }

  public Optional<Boolean> getExtensionMultiword() {
    return Optional.ofNullable(true);
  }

  public Optional<Byte> getMajorVersion() {
    return Optional.ofNullable((byte) 1);
  }

  public Optional<Byte> getMinorVersion() {
    return Optional.ofNullable((byte) 4);
  }

  @Override
  public boolean supportedRequest(XRequest request) {
    return request.getPluginName().equals(getPluginName());
  }

  @Override
  public boolean supportedRequest(byte majorOpcode, byte minorOpcode) {
    boolean isMajorOpcode = majorOpcode == getMajorOpcode();
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
    if(minorOpcode == 33) {
      return isMajorOpcode;
    }
    if(minorOpcode == 34) {
      return isMajorOpcode;
    }
    if(minorOpcode == 35) {
      return isMajorOpcode;
    }
    if(minorOpcode == 101) {
      return isMajorOpcode;
    }
    if(minorOpcode == 102) {
      return isMajorOpcode;
    }
    if(minorOpcode == 103) {
      return isMajorOpcode;
    }
    if(minorOpcode == 104) {
      return isMajorOpcode;
    }
    if(minorOpcode == 105) {
      return isMajorOpcode;
    }
    if(minorOpcode == 106) {
      return isMajorOpcode;
    }
    if(minorOpcode == 107) {
      return isMajorOpcode;
    }
    if(minorOpcode == 108) {
      return isMajorOpcode;
    }
    if(minorOpcode == 109) {
      return isMajorOpcode;
    }
    if(minorOpcode == 110) {
      return isMajorOpcode;
    }
    if(minorOpcode == 111) {
      return isMajorOpcode;
    }
    if(minorOpcode == 112) {
      return isMajorOpcode;
    }
    if(minorOpcode == 113) {
      return isMajorOpcode;
    }
    if(minorOpcode == 114) {
      return isMajorOpcode;
    }
    if(minorOpcode == 115) {
      return isMajorOpcode;
    }
    if(minorOpcode == 116) {
      return isMajorOpcode;
    }
    if(minorOpcode == 117) {
      return isMajorOpcode;
    }
    if(minorOpcode == 118) {
      return isMajorOpcode;
    }
    if(minorOpcode == 119) {
      return isMajorOpcode;
    }
    if(minorOpcode == 120) {
      return isMajorOpcode;
    }
    if(minorOpcode == 121) {
      return isMajorOpcode;
    }
    if(minorOpcode == 122) {
      return isMajorOpcode;
    }
    if(minorOpcode == 123) {
      return isMajorOpcode;
    }
    if(minorOpcode == 124) {
      return isMajorOpcode;
    }
    if(minorOpcode == 125) {
      return isMajorOpcode;
    }
    if(minorOpcode == 126) {
      return isMajorOpcode;
    }
    if(minorOpcode == 127) {
      return isMajorOpcode;
    }
    if(minorOpcode == 128) {
      return isMajorOpcode;
    }
    if(minorOpcode == 129) {
      return isMajorOpcode;
    }
    if(minorOpcode == 130) {
      return isMajorOpcode;
    }
    if(minorOpcode == 131) {
      return isMajorOpcode;
    }
    if(minorOpcode == 132) {
      return isMajorOpcode;
    }
    if(minorOpcode == 133) {
      return isMajorOpcode;
    }
    if(minorOpcode == 134) {
      return isMajorOpcode;
    }
    if(minorOpcode == 135) {
      return isMajorOpcode;
    }
    if(minorOpcode == 136) {
      return isMajorOpcode;
    }
    if(minorOpcode == 137) {
      return isMajorOpcode;
    }
    if(minorOpcode == 138) {
      return isMajorOpcode;
    }
    if(minorOpcode == 139) {
      return isMajorOpcode;
    }
    if(minorOpcode == 140) {
      return isMajorOpcode;
    }
    if(minorOpcode == 141) {
      return isMajorOpcode;
    }
    if(minorOpcode == 142) {
      return isMajorOpcode;
    }
    if(minorOpcode == 143) {
      return isMajorOpcode;
    }
    if(minorOpcode == 144) {
      return isMajorOpcode;
    }
    if(minorOpcode == 145) {
      return isMajorOpcode;
    }
    if(minorOpcode == 146) {
      return isMajorOpcode;
    }
    if(minorOpcode == 147) {
      return isMajorOpcode;
    }
    if(minorOpcode == 148) {
      return isMajorOpcode;
    }
    if(minorOpcode == 149) {
      return isMajorOpcode;
    }
    if(minorOpcode == 150) {
      return isMajorOpcode;
    }
    if(minorOpcode == 151) {
      return isMajorOpcode;
    }
    if(minorOpcode == 152) {
      return isMajorOpcode;
    }
    if(minorOpcode == 153) {
      return isMajorOpcode;
    }
    if(minorOpcode == 154) {
      return isMajorOpcode;
    }
    if(minorOpcode == 155) {
      return isMajorOpcode;
    }
    if(minorOpcode == 156) {
      return isMajorOpcode;
    }
    if(minorOpcode == 157) {
      return isMajorOpcode;
    }
    if(minorOpcode == 158) {
      return isMajorOpcode;
    }
    if(minorOpcode == 159) {
      return isMajorOpcode;
    }
    if(minorOpcode == 160) {
      return isMajorOpcode;
    }
    if(minorOpcode == 161) {
      return isMajorOpcode;
    }
    if(minorOpcode == 162) {
      return isMajorOpcode;
    }
    if(minorOpcode == 163) {
      return isMajorOpcode;
    }
    if(minorOpcode == 164) {
      return isMajorOpcode;
    }
    if(minorOpcode == 165) {
      return isMajorOpcode;
    }
    if(minorOpcode == 166) {
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
  public XRequest readRequest(byte majorOpcode, byte minorOpcode, X11Input in) throws IOException {
    if(minorOpcode == Render.OPCODE) {
      return Render.readRender(in);
    }
    if(minorOpcode == RenderLarge.OPCODE) {
      return RenderLarge.readRenderLarge(in);
    }
    if(minorOpcode == CreateContext.OPCODE) {
      return CreateContext.readCreateContext(in);
    }
    if(minorOpcode == DestroyContext.OPCODE) {
      return DestroyContext.readDestroyContext(in);
    }
    if(minorOpcode == MakeCurrent.OPCODE) {
      return MakeCurrent.readMakeCurrent(in);
    }
    if(minorOpcode == IsDirect.OPCODE) {
      return IsDirect.readIsDirect(in);
    }
    if(minorOpcode == QueryVersion.OPCODE) {
      return QueryVersion.readQueryVersion(in);
    }
    if(minorOpcode == WaitGL.OPCODE) {
      return WaitGL.readWaitGL(in);
    }
    if(minorOpcode == WaitX.OPCODE) {
      return WaitX.readWaitX(in);
    }
    if(minorOpcode == CopyContext.OPCODE) {
      return CopyContext.readCopyContext(in);
    }
    if(minorOpcode == SwapBuffers.OPCODE) {
      return SwapBuffers.readSwapBuffers(in);
    }
    if(minorOpcode == UseXFont.OPCODE) {
      return UseXFont.readUseXFont(in);
    }
    if(minorOpcode == CreateGLXPixmap.OPCODE) {
      return CreateGLXPixmap.readCreateGLXPixmap(in);
    }
    if(minorOpcode == GetVisualConfigs.OPCODE) {
      return GetVisualConfigs.readGetVisualConfigs(in);
    }
    if(minorOpcode == DestroyGLXPixmap.OPCODE) {
      return DestroyGLXPixmap.readDestroyGLXPixmap(in);
    }
    if(minorOpcode == VendorPrivate.OPCODE) {
      return VendorPrivate.readVendorPrivate(in);
    }
    if(minorOpcode == VendorPrivateWithReply.OPCODE) {
      return VendorPrivateWithReply.readVendorPrivateWithReply(in);
    }
    if(minorOpcode == QueryExtensionsString.OPCODE) {
      return QueryExtensionsString.readQueryExtensionsString(in);
    }
    if(minorOpcode == QueryServerString.OPCODE) {
      return QueryServerString.readQueryServerString(in);
    }
    if(minorOpcode == ClientInfo.OPCODE) {
      return ClientInfo.readClientInfo(in);
    }
    if(minorOpcode == GetFBConfigs.OPCODE) {
      return GetFBConfigs.readGetFBConfigs(in);
    }
    if(minorOpcode == CreatePixmap.OPCODE) {
      return CreatePixmap.readCreatePixmap(in);
    }
    if(minorOpcode == DestroyPixmap.OPCODE) {
      return DestroyPixmap.readDestroyPixmap(in);
    }
    if(minorOpcode == CreateNewContext.OPCODE) {
      return CreateNewContext.readCreateNewContext(in);
    }
    if(minorOpcode == QueryContext.OPCODE) {
      return QueryContext.readQueryContext(in);
    }
    if(minorOpcode == MakeContextCurrent.OPCODE) {
      return MakeContextCurrent.readMakeContextCurrent(in);
    }
    if(minorOpcode == CreatePbuffer.OPCODE) {
      return CreatePbuffer.readCreatePbuffer(in);
    }
    if(minorOpcode == DestroyPbuffer.OPCODE) {
      return DestroyPbuffer.readDestroyPbuffer(in);
    }
    if(minorOpcode == GetDrawableAttributes.OPCODE) {
      return GetDrawableAttributes.readGetDrawableAttributes(in);
    }
    if(minorOpcode == ChangeDrawableAttributes.OPCODE) {
      return ChangeDrawableAttributes.readChangeDrawableAttributes(in);
    }
    if(minorOpcode == CreateWindow.OPCODE) {
      return CreateWindow.readCreateWindow(in);
    }
    if(minorOpcode == DeleteWindow.OPCODE) {
      return DeleteWindow.readDeleteWindow(in);
    }
    if(minorOpcode == SetClientInfoARB.OPCODE) {
      return SetClientInfoARB.readSetClientInfoARB(in);
    }
    if(minorOpcode == CreateContextAttribsARB.OPCODE) {
      return CreateContextAttribsARB.readCreateContextAttribsARB(in);
    }
    if(minorOpcode == SetClientInfo2ARB.OPCODE) {
      return SetClientInfo2ARB.readSetClientInfo2ARB(in);
    }
    if(minorOpcode == NewList.OPCODE) {
      return NewList.readNewList(in);
    }
    if(minorOpcode == EndList.OPCODE) {
      return EndList.readEndList(in);
    }
    if(minorOpcode == DeleteLists.OPCODE) {
      return DeleteLists.readDeleteLists(in);
    }
    if(minorOpcode == GenLists.OPCODE) {
      return GenLists.readGenLists(in);
    }
    if(minorOpcode == FeedbackBuffer.OPCODE) {
      return FeedbackBuffer.readFeedbackBuffer(in);
    }
    if(minorOpcode == SelectBuffer.OPCODE) {
      return SelectBuffer.readSelectBuffer(in);
    }
    if(minorOpcode == RenderMode.OPCODE) {
      return RenderMode.readRenderMode(in);
    }
    if(minorOpcode == Finish.OPCODE) {
      return Finish.readFinish(in);
    }
    if(minorOpcode == PixelStoref.OPCODE) {
      return PixelStoref.readPixelStoref(in);
    }
    if(minorOpcode == PixelStorei.OPCODE) {
      return PixelStorei.readPixelStorei(in);
    }
    if(minorOpcode == ReadPixels.OPCODE) {
      return ReadPixels.readReadPixels(in);
    }
    if(minorOpcode == GetBooleanv.OPCODE) {
      return GetBooleanv.readGetBooleanv(in);
    }
    if(minorOpcode == GetClipPlane.OPCODE) {
      return GetClipPlane.readGetClipPlane(in);
    }
    if(minorOpcode == GetDoublev.OPCODE) {
      return GetDoublev.readGetDoublev(in);
    }
    if(minorOpcode == GetError.OPCODE) {
      return GetError.readGetError(in);
    }
    if(minorOpcode == GetFloatv.OPCODE) {
      return GetFloatv.readGetFloatv(in);
    }
    if(minorOpcode == GetIntegerv.OPCODE) {
      return GetIntegerv.readGetIntegerv(in);
    }
    if(minorOpcode == GetLightfv.OPCODE) {
      return GetLightfv.readGetLightfv(in);
    }
    if(minorOpcode == GetLightiv.OPCODE) {
      return GetLightiv.readGetLightiv(in);
    }
    if(minorOpcode == GetMapdv.OPCODE) {
      return GetMapdv.readGetMapdv(in);
    }
    if(minorOpcode == GetMapfv.OPCODE) {
      return GetMapfv.readGetMapfv(in);
    }
    if(minorOpcode == GetMapiv.OPCODE) {
      return GetMapiv.readGetMapiv(in);
    }
    if(minorOpcode == GetMaterialfv.OPCODE) {
      return GetMaterialfv.readGetMaterialfv(in);
    }
    if(minorOpcode == GetMaterialiv.OPCODE) {
      return GetMaterialiv.readGetMaterialiv(in);
    }
    if(minorOpcode == GetPixelMapfv.OPCODE) {
      return GetPixelMapfv.readGetPixelMapfv(in);
    }
    if(minorOpcode == GetPixelMapuiv.OPCODE) {
      return GetPixelMapuiv.readGetPixelMapuiv(in);
    }
    if(minorOpcode == GetPixelMapusv.OPCODE) {
      return GetPixelMapusv.readGetPixelMapusv(in);
    }
    if(minorOpcode == GetPolygonStipple.OPCODE) {
      return GetPolygonStipple.readGetPolygonStipple(in);
    }
    if(minorOpcode == GetString.OPCODE) {
      return GetString.readGetString(in);
    }
    if(minorOpcode == GetTexEnvfv.OPCODE) {
      return GetTexEnvfv.readGetTexEnvfv(in);
    }
    if(minorOpcode == GetTexEnviv.OPCODE) {
      return GetTexEnviv.readGetTexEnviv(in);
    }
    if(minorOpcode == GetTexGendv.OPCODE) {
      return GetTexGendv.readGetTexGendv(in);
    }
    if(minorOpcode == GetTexGenfv.OPCODE) {
      return GetTexGenfv.readGetTexGenfv(in);
    }
    if(minorOpcode == GetTexGeniv.OPCODE) {
      return GetTexGeniv.readGetTexGeniv(in);
    }
    if(minorOpcode == GetTexImage.OPCODE) {
      return GetTexImage.readGetTexImage(in);
    }
    if(minorOpcode == GetTexParameterfv.OPCODE) {
      return GetTexParameterfv.readGetTexParameterfv(in);
    }
    if(minorOpcode == GetTexParameteriv.OPCODE) {
      return GetTexParameteriv.readGetTexParameteriv(in);
    }
    if(minorOpcode == GetTexLevelParameterfv.OPCODE) {
      return GetTexLevelParameterfv.readGetTexLevelParameterfv(in);
    }
    if(minorOpcode == GetTexLevelParameteriv.OPCODE) {
      return GetTexLevelParameteriv.readGetTexLevelParameteriv(in);
    }
    if(minorOpcode == IsEnabled.OPCODE) {
      return IsEnabled.readIsEnabled(in);
    }
    if(minorOpcode == IsList.OPCODE) {
      return IsList.readIsList(in);
    }
    if(minorOpcode == Flush.OPCODE) {
      return Flush.readFlush(in);
    }
    if(minorOpcode == AreTexturesResident.OPCODE) {
      return AreTexturesResident.readAreTexturesResident(in);
    }
    if(minorOpcode == DeleteTextures.OPCODE) {
      return DeleteTextures.readDeleteTextures(in);
    }
    if(minorOpcode == GenTextures.OPCODE) {
      return GenTextures.readGenTextures(in);
    }
    if(minorOpcode == IsTexture.OPCODE) {
      return IsTexture.readIsTexture(in);
    }
    if(minorOpcode == GetColorTable.OPCODE) {
      return GetColorTable.readGetColorTable(in);
    }
    if(minorOpcode == GetColorTableParameterfv.OPCODE) {
      return GetColorTableParameterfv.readGetColorTableParameterfv(in);
    }
    if(minorOpcode == GetColorTableParameteriv.OPCODE) {
      return GetColorTableParameteriv.readGetColorTableParameteriv(in);
    }
    if(minorOpcode == GetConvolutionFilter.OPCODE) {
      return GetConvolutionFilter.readGetConvolutionFilter(in);
    }
    if(minorOpcode == GetConvolutionParameterfv.OPCODE) {
      return GetConvolutionParameterfv.readGetConvolutionParameterfv(in);
    }
    if(minorOpcode == GetConvolutionParameteriv.OPCODE) {
      return GetConvolutionParameteriv.readGetConvolutionParameteriv(in);
    }
    if(minorOpcode == GetSeparableFilter.OPCODE) {
      return GetSeparableFilter.readGetSeparableFilter(in);
    }
    if(minorOpcode == GetHistogram.OPCODE) {
      return GetHistogram.readGetHistogram(in);
    }
    if(minorOpcode == GetHistogramParameterfv.OPCODE) {
      return GetHistogramParameterfv.readGetHistogramParameterfv(in);
    }
    if(minorOpcode == GetHistogramParameteriv.OPCODE) {
      return GetHistogramParameteriv.readGetHistogramParameteriv(in);
    }
    if(minorOpcode == GetMinmax.OPCODE) {
      return GetMinmax.readGetMinmax(in);
    }
    if(minorOpcode == GetMinmaxParameterfv.OPCODE) {
      return GetMinmaxParameterfv.readGetMinmaxParameterfv(in);
    }
    if(minorOpcode == GetMinmaxParameteriv.OPCODE) {
      return GetMinmaxParameteriv.readGetMinmaxParameteriv(in);
    }
    if(minorOpcode == GetCompressedTexImageARB.OPCODE) {
      return GetCompressedTexImageARB.readGetCompressedTexImageARB(in);
    }
    if(minorOpcode == DeleteQueriesARB.OPCODE) {
      return DeleteQueriesARB.readDeleteQueriesARB(in);
    }
    if(minorOpcode == GenQueriesARB.OPCODE) {
      return GenQueriesARB.readGenQueriesARB(in);
    }
    if(minorOpcode == IsQueryARB.OPCODE) {
      return IsQueryARB.readIsQueryARB(in);
    }
    if(minorOpcode == GetQueryivARB.OPCODE) {
      return GetQueryivARB.readGetQueryivARB(in);
    }
    if(minorOpcode == GetQueryObjectivARB.OPCODE) {
      return GetQueryObjectivARB.readGetQueryObjectivARB(in);
    }
    if(minorOpcode == GetQueryObjectuivARB.OPCODE) {
      return GetQueryObjectuivARB.readGetQueryObjectuivARB(in);
    }
    throw new IllegalArgumentException("majorOpcode " + majorOpcode + ", minorOpcode " + minorOpcode + " is not supported");
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
