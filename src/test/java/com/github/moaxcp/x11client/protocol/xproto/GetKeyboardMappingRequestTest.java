package com.github.moaxcp.x11client.protocol.xproto;

import com.github.moaxcp.x11client.protocol.X11Output;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

public class GetKeyboardMappingRequestTest {
  @Test
  void opcode() {
    assertThat(GetKeyboardMappingRequest.OPCODE).isEqualTo((byte) 101);
  }

  @Test
  void length() {
    assertThat(new GetKeyboardMappingRequest().getLength()).isEqualTo(2);
  }

  @Test
  void size() {
    assertThat(new GetKeyboardMappingRequest().getSize()).isEqualTo(6);
  }

  @Test
  void write() throws IOException {
    X11Output out = mock(X11Output.class);
    GetKeyboardMappingRequest request = new GetKeyboardMappingRequest();
    request.setFirstKeycode((byte) 22);
    request.setCount((byte) 44);
    request.write(out);

    then(out).should().writeCard8(GetKeyboardMappingRequest.OPCODE);
    then(out).should().writePad(1);
    then(out).should().writeCard16((short) 2);
    then(out).should().writeCard8((byte) 22);
    then(out).should().writeCard8((byte) 44);
    then(out).should().writePadAlign(6);
  }
}
