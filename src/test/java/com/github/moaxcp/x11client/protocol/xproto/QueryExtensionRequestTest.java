package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Output;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

public class QueryExtensionRequestTest {
  @Test
  void writeQueryExtensionXCMISC() throws IOException {
    X11Output out = mock(X11Output.class);
    QueryExtensionRequest request = new QueryExtensionRequest();
    request.setName("XC-MISC");

    request.write((byte) 0, out);

    then(out).should().writeCard8(QueryExtensionRequest.OPCODE);
    then(out).should().writePad(1);
    then(out).should().writeCard16((short) 4);
    then(out).should().writeCard16((short) 7);
    then(out).should().writePad(2);
    then(out).should().writeString8("XC-MISC");
    then(out).should().writePadAlign(15);
  }
  @Test
  void writeQueryExtensionBIGREQUESTS() throws IOException {
    X11Output out = mock(X11Output.class);
    QueryExtensionRequest request = new QueryExtensionRequest();
    request.setName("BIG-REQUESTS");

    request.write((byte) 0, out);

    then(out).should().writeCard8(QueryExtensionRequest.OPCODE);
    then(out).should().writePad(1);
    then(out).should().writeCard16((short) 5);
    then(out).should().writeCard16((short) 12);
    then(out).should().writePad(2);
    then(out).should().writeString8("BIG-REQUESTS");
    then(out).should().writePadAlign(20);
  }
}
