package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.Utilities;
import com.github.moaxcp.x11.protocol.X11BigEndianInputStream;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.X11BigEndianOutputStream;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangePropertyTest {
  ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
  X11Output out = new X11BigEndianOutputStream(outBytes);

  @Test
  void writeAndRead() throws IOException {
    ChangeProperty expected = ChangeProperty.builder()
      .window(12)
      .property(Atom.WM_NAME.getValue())
      .type(Atom.STRING.getValue())
      .format((byte) 8)
      .data(Utilities.toByteList("Hello World!"))
      .dataLen("Hello World!".length())
      .build();

    expected.write((byte) 0, out);

    X11BigEndianInputStream in = new X11BigEndianInputStream(new ByteArrayInputStream(outBytes.toByteArray()));
    byte opCode = in.readCard8();
    assertThat(opCode).isEqualTo(ChangeProperty.OPCODE);
    ChangeProperty result = ChangeProperty.readChangeProperty(in);
    assertThat(result).isEqualTo(expected);
    assertThat(result.getSize()).isEqualTo(outBytes.size());
  }
}
