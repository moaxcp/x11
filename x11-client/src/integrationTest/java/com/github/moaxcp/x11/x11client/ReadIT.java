package com.github.moaxcp.x11.x11client;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.ge.QueryVersion;
import com.github.moaxcp.x11.protocol.shape.InputSelected;
import com.github.moaxcp.x11.protocol.xproto.ButtonPressEvent;
import com.github.moaxcp.x11.protocol.xproto.CreateWindow;
import com.github.moaxcp.x11.protocol.xproto.EventMask;
import com.github.moaxcp.x11.protocol.xproto.WindowClass;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.github.moaxcp.x11.protocol.Utilities.toByteList;
import static com.github.moaxcp.x11.protocol.Utilities.toX11Input;
import static org.assertj.core.api.Assertions.assertThat;

public class ReadIT {

  @Test
  void readCreateWindow() throws IOException {
    try(X11Client client = X11Client.connect()) {
      CreateWindow create = CreateWindow.builder()
          .depth((byte) 1)
          .wid(2)
          .parent(3)
          .x((short) 4)
          .y((short) 5)
          .width((short) 6)
          .height((short) 7)
          .borderWidth((short) 8)
          .clazz(WindowClass.COPY_FROM_PARENT)
          .visual(9)
          .backgroundPixel(10)
          .borderPixel(11)
          .eventMaskEnable(EventMask.BUTTON1_MOTION)
          .build();

      X11Input in = toX11Input(toByteList(client.getMajorOpcode(create), create));

      CreateWindow result = client.readRequest(in);

      assertThat(result).isEqualTo(create);
    }
  }

  @Test
  void shapeInputSelected() throws IOException {
    try(X11Client client = X11Client.connect()) {
      InputSelected selected = InputSelected.builder()
          .destinationWindow(1)
          .build();

      X11Input in = toX11Input(toByteList(client.getMajorOpcode(selected), selected));

      InputSelected result = client.readRequest(in);

      assertThat(result).isEqualTo(selected);
    }
  }

  @Test
  void geQueryVersion() throws IOException {
    try(X11Client client = X11Client.connect()) {
      QueryVersion query = QueryVersion.builder()
          .clientMajorVersion(client.getSetup().getProtocolMajorVersion())
          .clientMinorVersion(client.getSetup().getProtocolMinorVersion())
          .build();

      X11Input in = toX11Input(toByteList(client.getMajorOpcode(query), query));

      QueryVersion result = client.readRequest(in);

      assertThat(result).isEqualTo(query);
    }
  }

  @Test
  void readButtonPressEvent() throws IOException {
    try(X11Client client = X11Client.connect()) {
      ButtonPressEvent event = ButtonPressEvent.builder()
          .firstEventOffset((byte) 0)
          .sentEvent(false)
          .detail((byte) 1)
          .sequenceNumber((short) 2)
          .time(3)
          .root(4)
          .child(5)
          .rootX((short) 6)
          .rootY((short) 7)
          .eventX((short) 8)
          .eventY((short) 9)
          .state((short) 10)
          .sameScreen(true)
          .build();

      X11Input in = toX11Input(toByteList(event));
      ButtonPressEvent result = client.readEvent(in);

      assertThat(result).isEqualTo(event);
    }
  }

  @Test
  void readButtonPressEvent_send() throws IOException {
    try(X11Client client = X11Client.connect()) {
      ButtonPressEvent event = ButtonPressEvent.builder()
          .firstEventOffset((byte) 0)
          .sentEvent(true)
          .detail((byte) 1)
          .sequenceNumber((short) 2)
          .time(3)
          .root(4)
          .child(5)
          .rootX((short) 6)
          .rootY((short) 7)
          .eventX((short) 8)
          .eventY((short) 9)
          .state((short) 10)
          .sameScreen(true)
          .build();

      X11Input in = toX11Input(toByteList(event));
      ButtonPressEvent result = client.readEvent(in);

      assertThat(result).isEqualTo(event);
    }
  }
}
