package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.X11InputStream;
import com.github.moaxcp.x11client.X11OutputStream;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SetupRequestStructTest {
  ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
  X11Output out = new X11OutputStream(outBytes);

  @Test
  void writeAndRead() throws IOException {
    SetupRequestStruct expected = SetupRequestStruct.builder()
      .byteOrder((byte) 'B')
      .protocolMajorVersion((short) 11)
      .protocolMinorVersion((short) 0)
      .authorizationProtocolName("MIT-MAGIC-COOKIE-1")
      .authorizationProtocolData("secret key 123457")
      .build();

    expected.write(out);

    ByteArrayInputStream inBytes = new ByteArrayInputStream(outBytes.toByteArray());
    SetupRequestStruct result = SetupRequestStruct.readSetupRequestStruct(new X11InputStream(inBytes));
    assertThat(result).isEqualTo(expected);
  }
}
