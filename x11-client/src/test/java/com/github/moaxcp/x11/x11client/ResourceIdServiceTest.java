package com.github.moaxcp.x11.x11client;

import com.github.moaxcp.x11.protocol.xc_misc.GetXIDRange;
import com.github.moaxcp.x11.protocol.xc_misc.GetXIDRangeReply;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class ResourceIdServiceTest {
  @Mock
  XProtocolService protocolService;
  ResourceIdService service;

  @BeforeEach
  void setup() {
    service = new ResourceIdService(protocolService, 0b0111, 0b1000);
  }

  @Test
  void firstIdIs1() {
    int id = service.nextResourceId();
    assertThat(id).isEqualTo(0b1001);
  }

  @Test
  void thirdIdIs3() {
    int first = service.nextResourceId();
    int second = service.nextResourceId();
    int id = service.nextResourceId();
    assertThat(id).isEqualTo(0b1011);
  }

  @Test
  void switchToXCMISCNotActivated() {
    service = new ResourceIdService(protocolService, 0b01, 0b10);
    service.nextResourceId();
    given(protocolService.activatedPlugin("XC-MISC")).willReturn(false);
    IllegalStateException exception = assertThrows(IllegalStateException.class, () -> service.nextResourceId());
    assertThat(exception).hasMessage("Core protocol is out of ids and XC-MISC is not activated.");
    then(protocolService).should().activatedPlugin("XC-MISC");
  }

  @Test
  void switchToXCMISC() {
    service = new ResourceIdService(protocolService, 0b01, 0b10);
    service.nextResourceId();
    given(protocolService.activatedPlugin("XC-MISC")).willReturn(true);
    given(protocolService.send(any(GetXIDRange.class))).willReturn(GetXIDRangeReply.builder().startId(0b100).count(2).build());
    int id = service.nextResourceId();
    then(protocolService).should().activatedPlugin("XC-MISC");
    assertThat(id).isEqualTo(4);
    id = service.nextResourceId();
    assertThat(id).isEqualTo(5);
  }
}
