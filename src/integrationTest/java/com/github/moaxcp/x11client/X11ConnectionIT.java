package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XResponse;
import com.github.moaxcp.x11client.protocol.bigreq.EnableReply;
import com.github.moaxcp.x11client.protocol.bigreq.EnableRequest;
import com.github.moaxcp.x11client.protocol.xproto.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class X11ConnectionIT {
  private XephyrRunner runner;

  @BeforeEach
  void setup() throws IOException {
    runner = XephyrRunner.builder()
      .ac(true)
      .br(true)
      .noreset(true)
      .arg(":1")
      .build();
    runner.start();
  }

  @AfterEach
  void teardown() throws InterruptedException {
    runner.stop();
  }

  @Test
  void test() throws IOException {
    try(X11Connection connection = X11Connection.connect(new DisplayName(":1"))) {
      X11Output out = connection.getX11Output();
      X11Input in = connection.getX11Input();
      QueryExtensionRequest extensionRequest = QueryExtensionRequest.builder()
        .name("XC-MISC")
        .build();
      extensionRequest.write((byte) 0, out);
      byte status = in.readByte();
      if(status == 0) {
        //error
      } else if(status == 1) {
        //success
        byte field = in.readByte();
        short sequenceNumber = in.readCard16();
        QueryExtensionReply reply = QueryExtensionReply.readQueryExtensionReply(field, sequenceNumber, in);
        System.out.println(reply);
      } else {
        //event
      }
    }
  }

  @Test
  void clientTest() throws IOException {
    try(X11Client client = X11Client.connect(new DisplayName(":1"))) {
      SetupStruct setup = client.getSetup();
      GetKeyboardMappingRequest keyboard = GetKeyboardMappingRequest.builder()
        .firstKeycode(setup.getMinKeycode())
        .count((byte) (setup.getMaxKeycode() - setup.getMinKeycode() + 1))
        .build();
      System.out.println(keyboard);
      client.send(keyboard);
      GetKeyboardMappingReply keyboardReply = client.read();
      System.out.println(keyboardReply);

      QueryExtensionRequest bigRequests = QueryExtensionRequest.builder()
        .name("BIG-REQUESTS")
        .build();
      System.out.println(bigRequests);
      //assertThat(bigRequests.getLength()).isEqualTo(5);
      client.send(bigRequests);
      QueryExtensionReply bigRequestReply = client.read();
      System.out.println(bigRequestReply);

      EnableRequest enableRequest = EnableRequest.builder().build();
      System.out.println(enableRequest);
      client.send(enableRequest);
      EnableReply enableReply = client.read();
      System.out.println(enableReply);

      CreateWindowRequest window = CreateWindowRequest.builder()
        .depth(client.getDefaultDepth())
        .wid(client.nextResourceId())
        .parent(client.getDefaultRoot())
        .x((short) 10)
        .y((short) 10)
        .width((short) 600)
        .height((short) 480)
        .borderWidth((short) 5)
        .clazz(WindowClassEnum.COPY_FROM_PARENT)
        .visual(client.getDefaultVisualId())
        .backgroundPixel(client.getDefaultScreen().getWhitePixel())
        .borderPixel(client.getDefaultScreen().getBlackPixel())
        .eventMaskEnable(EventMaskEnum.EXPOSURE)
        .eventMaskEnable(EventMaskEnum.KEY_PRESS)
        .build();
      System.out.println(window);
      client.send(window);
      MapWindowRequest mapWindow = MapWindowRequest.builder()
        .window(window.getWid())
        .build();
      System.out.println(mapWindow);
      client.send(mapWindow);
      while(true) {
        XResponse read = client.read();
        System.out.println(read);
        if(read instanceof ExposeEvent) {

        } else if(read instanceof KeyPressEvent) {
          break;
        }
      }
    }
  }
}
