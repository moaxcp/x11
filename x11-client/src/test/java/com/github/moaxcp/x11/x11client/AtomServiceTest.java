package com.github.moaxcp.x11.x11client;

import com.github.moaxcp.x11.protocol.AtomValue;
import com.github.moaxcp.x11.protocol.xproto.Atom;
import com.github.moaxcp.x11.protocol.xproto.InternAtom;
import com.github.moaxcp.x11.protocol.xproto.InternAtomReply;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class AtomServiceTest {
  @Mock
  private XProtocolService protocolService;
  private AtomService service;

  @BeforeEach
  void setup() {
    service = new AtomService(protocolService);
  }

  @Test
  void getAtomId_has_predefined_atoms() {
    AtomValue atom = service.getAtom(Atom.BITMAP);
    assertThat(atom.getId()).isEqualTo(Atom.BITMAP.getValue());
    assertThat(atom.getName()).isEqualTo("BITMAP");
  }

  @Test
  void getAtomName_has_predefined_atoms() {
    AtomValue atom = service.getAtom("BITMAP");
    assertThat(atom.getId()).isEqualTo(Atom.BITMAP.getValue());
    assertThat(atom.getName()).isEqualTo("BITMAP");
  }

  @Test
  void getAtomName_creates_new_atoms() {
    given(protocolService.send(any(InternAtom.class))).willReturn(InternAtomReply.builder().atom(100).build());
    AtomValue atom = service.getAtom("DELETE_WINDOW");
    assertThat(atom.getId()).isEqualTo(100);
    assertThat(atom.getName()).isEqualTo("DELETE_WINDOW");
    then(protocolService).should().send(any(InternAtom.class));
  }
}
