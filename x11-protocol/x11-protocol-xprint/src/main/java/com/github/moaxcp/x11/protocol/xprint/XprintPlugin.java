package com.github.moaxcp.x11.protocol.xprint;

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

public class XprintPlugin implements XProtocolPlugin {
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
    return "xprint";
  }

  public Optional<String> getExtensionXName() {
    return Optional.ofNullable("XpExtension");
  }

  public Optional<String> getExtensionName() {
    return Optional.ofNullable("XPrint");
  }

  public Optional<Boolean> getExtensionMultiword() {
    return Optional.ofNullable(true);
  }

  public Optional<Byte> getMajorVersion() {
    return Optional.ofNullable((byte) 1);
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
    if(minorOpcode == 20) {
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
    if(minorOpcode == 19) {
      return isMajorOpcode;
    }
    if(minorOpcode == 18) {
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
    if(code - firstError == 1) {
      return true;
    }
    return false;
  }

  @Override
  public XRequest readRequest(byte majorOpcode, byte minorOpcode, X11Input in) throws IOException {
    if(minorOpcode == PrintQueryVersion.OPCODE) {
      return PrintQueryVersion.readPrintQueryVersion(in);
    }
    if(minorOpcode == PrintGetPrinterList.OPCODE) {
      return PrintGetPrinterList.readPrintGetPrinterList(in);
    }
    if(minorOpcode == PrintRehashPrinterList.OPCODE) {
      return PrintRehashPrinterList.readPrintRehashPrinterList(in);
    }
    if(minorOpcode == CreateContext.OPCODE) {
      return CreateContext.readCreateContext(in);
    }
    if(minorOpcode == PrintSetContext.OPCODE) {
      return PrintSetContext.readPrintSetContext(in);
    }
    if(minorOpcode == PrintGetContext.OPCODE) {
      return PrintGetContext.readPrintGetContext(in);
    }
    if(minorOpcode == PrintDestroyContext.OPCODE) {
      return PrintDestroyContext.readPrintDestroyContext(in);
    }
    if(minorOpcode == PrintGetScreenOfContext.OPCODE) {
      return PrintGetScreenOfContext.readPrintGetScreenOfContext(in);
    }
    if(minorOpcode == PrintStartJob.OPCODE) {
      return PrintStartJob.readPrintStartJob(in);
    }
    if(minorOpcode == PrintEndJob.OPCODE) {
      return PrintEndJob.readPrintEndJob(in);
    }
    if(minorOpcode == PrintStartDoc.OPCODE) {
      return PrintStartDoc.readPrintStartDoc(in);
    }
    if(minorOpcode == PrintEndDoc.OPCODE) {
      return PrintEndDoc.readPrintEndDoc(in);
    }
    if(minorOpcode == PrintPutDocumentData.OPCODE) {
      return PrintPutDocumentData.readPrintPutDocumentData(in);
    }
    if(minorOpcode == PrintGetDocumentData.OPCODE) {
      return PrintGetDocumentData.readPrintGetDocumentData(in);
    }
    if(minorOpcode == PrintStartPage.OPCODE) {
      return PrintStartPage.readPrintStartPage(in);
    }
    if(minorOpcode == PrintEndPage.OPCODE) {
      return PrintEndPage.readPrintEndPage(in);
    }
    if(minorOpcode == PrintSelectInput.OPCODE) {
      return PrintSelectInput.readPrintSelectInput(in);
    }
    if(minorOpcode == PrintInputSelected.OPCODE) {
      return PrintInputSelected.readPrintInputSelected(in);
    }
    if(minorOpcode == PrintGetAttributes.OPCODE) {
      return PrintGetAttributes.readPrintGetAttributes(in);
    }
    if(minorOpcode == PrintGetOneAttributes.OPCODE) {
      return PrintGetOneAttributes.readPrintGetOneAttributes(in);
    }
    if(minorOpcode == PrintSetAttributes.OPCODE) {
      return PrintSetAttributes.readPrintSetAttributes(in);
    }
    if(minorOpcode == PrintGetPageDimensions.OPCODE) {
      return PrintGetPageDimensions.readPrintGetPageDimensions(in);
    }
    if(minorOpcode == PrintQueryScreens.OPCODE) {
      return PrintQueryScreens.readPrintQueryScreens(in);
    }
    if(minorOpcode == PrintSetImageResolution.OPCODE) {
      return PrintSetImageResolution.readPrintSetImageResolution(in);
    }
    if(minorOpcode == PrintGetImageResolution.OPCODE) {
      return PrintGetImageResolution.readPrintGetImageResolution(in);
    }
    throw new IllegalArgumentException("majorOpcode " + majorOpcode + ", minorOpcode " + minorOpcode + " is not supported");
  }

  @Override
  public XEvent readEvent(byte number, boolean sentEvent, X11Input in) throws IOException {
    if(number - firstEvent == 0) {
      return NotifyEvent.readNotifyEvent(firstEvent, sentEvent, in);
    }
    if(number - firstEvent == 1) {
      return AttributNotifyEvent.readAttributNotifyEvent(firstEvent, sentEvent, in);
    }
    throw new IllegalArgumentException("number " + number + " is not supported");
  }

  @Override
  public XError readError(byte code, X11Input in) throws IOException {
    if(code - firstError == 0) {
      return BadContextError.readBadContextError(firstError, in);
    }
    if(code - firstError == 1) {
      return BadSequenceError.readBadSequenceError(firstError, in);
    }
    throw new IllegalArgumentException("code " + code + " is not supported");
  }

  @Override
  public XGenericEvent readGenericEvent(byte firstEventOffset, boolean sentEvent, byte extension,
      short sequenceNumber, int length, short eventType, X11Input in) throws IOException {
    throw new IllegalArgumentException("eventType " + eventType + " is not supported");
  }
}
