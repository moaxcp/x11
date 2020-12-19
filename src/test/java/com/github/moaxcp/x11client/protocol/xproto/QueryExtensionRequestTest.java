package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Output;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.github.moaxcp.x11client.protocol.Utilities.byteArrayToList;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

public class QueryExtensionRequestTest {
  @Test
  void writeQueryExtensionXCMISC() throws IOException {
    X11Output out = mock(X11Output.class);
    QueryExtension request = QueryExtension.builder()
      .nameLen((short) "XC-MISC".length())
      .name(byteArrayToList("XC-MISC".getBytes()))
      .build();

    request.write((byte) 0, out);

    then(out).should().writeCard8(QueryExtension.OPCODE);
    then(out).should().writePad(1);
    then(out).should().writeCard16((short) 4);
    then(out).should().writeCard16((short) 7);
    then(out).should().writePad(2);
    then(out).should().writeChar(byteArrayToList("XC-MISC".getBytes()));
    then(out).should().writePadAlign(15);
  }
  @Test
  void writeQueryExtensionBIGREQUESTS() throws IOException {
    X11Output out = mock(X11Output.class);
    QueryExtension request = QueryExtension.builder()
      .nameLen((short) "BIG-REQUESTS".length())
      .name(byteArrayToList("BIG-REQUESTS".getBytes()))
      .build();

    request.write((byte) 0, out);

    then(out).should().writeCard8(QueryExtension.OPCODE);
    then(out).should().writePad(1);
    then(out).should().writeCard16((short) 5);
    then(out).should().writeCard16((short) 12);
    then(out).should().writePad(2);
    then(out).should().writeChar(byteArrayToList("BIG-REQUESTS".getBytes()));
    then(out).should().writePadAlign(20);
  }
}
