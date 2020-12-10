package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.X11InputStream;
import com.github.moaxcp.x11client.X11OutputStream;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import static com.github.moaxcp.x11client.Utilities.byteArrayToList;
import static org.assertj.core.api.Assertions.assertThat;

public class SetupRequestTest {
  ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
  X11Output out = new X11OutputStream(outBytes);

  @Test
  void writeAndRead() throws IOException {
    SetupRequest expected = SetupRequest.builder()
      .byteOrder((byte) 'B')
      .protocolMajorVersion((short) 11)
      .protocolMinorVersion((short) 0)
      .authorizationProtocolNameLen((short) "MIT-MAGIC-COOKIE-1".length())
      .authorizationProtocolDataLen((short) "secret key 123457".length())
      .authorizationProtocolName(byteArrayToList("MIT-MAGIC-COOKIE-1".getBytes()))
      .authorizationProtocolData(byteArrayToList("secret key 123457".getBytes()))
      .build();

    expected.write(out);

    ByteArrayInputStream inBytes = new ByteArrayInputStream(outBytes.toByteArray());
    SetupRequest result = SetupRequest.readSetupRequest(new X11InputStream(inBytes));
    assertThat(result).isEqualTo(expected);
  }
}
