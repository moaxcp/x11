package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.Utilities;
import com.github.moaxcp.x11.protocol.X11Output;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.BDDMockito.then;

public class QueryExtensionRequestTest {
  @Test
  void writeQueryExtensionXCMISC() throws IOException {
    X11Output out = Mockito.mock(X11Output.class);
    QueryExtension request = QueryExtension.builder()
      .name(Utilities.toList("XC-MISC".getBytes()))
      .build();

    request.write((byte) 0, out);

    then(out).should().writeCard8(QueryExtension.OPCODE);
    then(out).should().writePad(1);
    then(out).should().writeCard16((short) 4);
    then(out).should().writeCard16((short) 7);
    then(out).should().writePad(2);
    then(out).should().writeChar(Utilities.toList("XC-MISC".getBytes()));
    then(out).should().writePadAlign(15);
  }
  @Test
  void writeQueryExtensionBIGREQUESTS() throws IOException {
    X11Output out = Mockito.mock(X11Output.class);
    QueryExtension request = QueryExtension.builder()
      .name(Utilities.toList("BIG-REQUESTS".getBytes()))
      .build();

    request.write((byte) 0, out);

    then(out).should().writeCard8(QueryExtension.OPCODE);
    then(out).should().writePad(1);
    then(out).should().writeCard16((short) 5);
    then(out).should().writeCard16((short) 12);
    then(out).should().writePad(2);
    then(out).should().writeChar(Utilities.toList("BIG-REQUESTS".getBytes()));
    then(out).should().writePadAlign(20);
  }
}
