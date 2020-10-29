package com.github.moaxcp.x11client;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
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
      QueryExtensionRequest extensionRequest = new QueryExtensionRequest();
      extensionRequest.setName("XC-MISC");
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
      GetKeyboardMappingRequest keyboard = new GetKeyboardMappingRequest();
      keyboard.setFirstKeycode(setup.getMinKeycode());
      keyboard.setCount((byte) (setup.getMaxKeycode() - setup.getMinKeycode() + 1));
      client.send(keyboard);
      GetKeyboardMappingReply keyboardReply = client.read();
      System.out.println(keyboardReply);

      QueryExtensionRequest bigRequests = new QueryExtensionRequest();
      bigRequests.setName("BIG-REQUESTS");
      //assertThat(bigRequests.getLength()).isEqualTo(5);
      client.send(bigRequests);
      QueryExtensionReply bigRequestReply = client.read();
      System.out.println(bigRequestReply);

      EnableRequest enableRequest = new EnableRequest();
      client.send(enableRequest);
      EnableReply enableReply = client.read();
      System.out.println(enableReply);

      CreateWindowRequest window = new CreateWindowRequest();
      window.setDepth(client.getDefaultDepth());
      window.setWid(client.nextResourceId());
      window.setParent(client.getDefaultRoot());
      window.setX((short) 10);
      window.setY((short) 10);
      window.setWidth((short) 600);
      window.setHeight((short) 480);
      window.setBorderWidth((short) 5);
      window.setClazz(WindowClassEnum.COPY_FROM_PARENT);
      window.setVisual(client.getDefaultVisualId());
      client.send(window);
      System.out.println(window);
    }
  }
}
