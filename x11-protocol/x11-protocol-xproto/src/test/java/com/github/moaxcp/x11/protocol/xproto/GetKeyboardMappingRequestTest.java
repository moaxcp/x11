package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Output;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class GetKeyboardMappingRequestTest {
  @Test
  void opcode() {
    assertThat(GetKeyboardMapping.OPCODE).isEqualTo((byte) 101);
  }

  @Test
  void length() {
    assertThat(GetKeyboardMapping.builder().build().getLength()).isEqualTo(2);
  }

  @Test
  void size() {
    assertThat(GetKeyboardMapping.builder().build().getSize()).isEqualTo(6);
  }

  @Test
  void write() throws IOException {
    X11Output out = mock(X11Output.class);
    GetKeyboardMapping request = GetKeyboardMapping.builder()
      .firstKeycode((byte) 22)
      .count((byte) 44)
      .build();
    request.write((byte) 0, out);

    BDDMockito.then(out).should().writeCard8(GetKeyboardMapping.OPCODE);
    BDDMockito.then(out).should().writePad(1);
    BDDMockito.then(out).should().writeCard16((short) 2);
    BDDMockito.then(out).should().writeCard8((byte) 22);
    BDDMockito.then(out).should().writeCard8((byte) 44);
    BDDMockito.then(out).should().writePadAlign(6);
  }
}
