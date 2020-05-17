package com.github.moaxcp.x11client;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.github.moaxcp.x11client.X11Connection.connect;
import static com.github.moaxcp.x11client.XAuthority.Family.LOCAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class X11ConnectionTest {
  @Mock
  private Socket socket;

  private XAuthority xAuthority = new XAuthority(LOCAL, "host".getBytes(), 0, "MIT-MAGIC-COOKIE-1", new byte[]{1, 2, 3});

  @Test
  void constructor_fails_on_null_displayName() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new X11Connection(null, xAuthority, socket));
    assertThat(exception).hasMessage("displayName is marked non-null but is null");
  }

  @Test
  void constructor_fails_on_null_xAuthority() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new X11Connection(new DisplayName(":0"), null, socket));
    assertThat(exception).hasMessage("xAuthority is marked non-null but is null");
  }

  @Test
  void constructor_fails_on_null_socket() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> new X11Connection(new DisplayName(":0"), xAuthority, null));
    assertThat(exception).hasMessage("socket is marked non-null but is null");
  }

  @Test
  void connect_fails_on_null_displayName() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> connect(null, xAuthority));
    assertThat(exception).hasMessage("displayName is marked non-null but is null");
  }

  @Test
  void connect_fails_on_null_xAuthority() {
    NullPointerException exception = assertThrows(NullPointerException.class, () -> connect(new DisplayName((":0")), null));
    assertThat(exception).hasMessage("xAuthority is marked non-null but is null");
  }

  @Test
  void constructor_ConnectionFailure() throws IOException {
    byte[] inBytes = new byte[]{0, 5, 0, 11, 0, 0, 0, 0, 'H', 'e', 'l', 'l', 'o', 0, 0, 0};
    ByteArrayInputStream in = new ByteArrayInputStream(inBytes);
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    when(socket.getInputStream()).thenReturn(in);
    when(socket.getOutputStream()).thenReturn(out);

    DisplayName name = new DisplayName(":0");
    ConnectionFailureException exception = assertThrows(ConnectionFailureException.class, () -> new X11Connection(name, xAuthority, socket));

    assertThat(exception.getFailure()).isEqualTo(new ConnectionFailure(11, 0, "Hello"));
    assertThat(out.toByteArray()).containsExactly(
        'B',
        0,
        0, 11,
        0, 0,
        0, xAuthority.getProtocolName().length(),
        0, xAuthority.getProtocolData().length,
        0, 0,
        'M', 'I', 'T', '-', 'M', 'A', 'G', 'I', 'C', '-', 'C', 'O', 'O', 'K', 'I', 'E', '-', '1',
        0, 0,
        1, 2, 3,
        0
    );
  }

  @Test
  void constructor_Authenticate() throws IOException {
    byte[] inBytes = new byte[] {2};
    ByteArrayInputStream in = new ByteArrayInputStream(inBytes);
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    when(socket.getInputStream()).thenReturn(in);
    when(socket.getOutputStream()).thenReturn(out);

    DisplayName name = new DisplayName(":0");
    UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class, () -> new X11Connection(name, xAuthority, socket));

    assertThat(exception).hasMessage("authenticate not supported");
    assertThat(out.toByteArray()).containsExactly(
        'B',
        0,
        0, 11,
        0, 0,
        0, xAuthority.getProtocolName().length(),
        0, xAuthority.getProtocolData().length,
        0, 0,
        'M', 'I', 'T', '-', 'M', 'A', 'G', 'I', 'C', '-', 'C', 'O', 'O', 'K', 'I', 'E', '-', '1',
        0, 0,
        1, 2, 3,
        0
    );
  }

  @Test
  void constructor_Success() throws IOException {
    byte[] additionalData = BigInteger.valueOf(1707).toByteArray();
    byte[] releaseNumber = BigInteger.valueOf(12008000).toByteArray();
    byte[] resourceIdBase = BigInteger.valueOf(50331648).toByteArray();
    byte[] resourceIdMask = BigInteger.valueOf(2097151).toByteArray();
    byte[] maximumRequestLength = BigInteger.valueOf(65535).toByteArray();
    byte[] vendor = "The X.Org Foundation".getBytes(StandardCharsets.UTF_8);
    byte[] inBytes = new byte[] {1, //success
        0, //unused
        0, 11, //major version
        0, 0, //minor version
        additionalData[0], additionalData[1], //additional data
        releaseNumber[0], releaseNumber[1], releaseNumber[2], releaseNumber[3],
        resourceIdBase[0], resourceIdBase[1], resourceIdBase[2], resourceIdBase[3],
        0, resourceIdMask[0], resourceIdMask[1], resourceIdMask[2],
        0, 0, 1, 0, //motion buffer
        0, 20, //vendor length
        maximumRequestLength[0], maximumRequestLength[1],
        1, //number of screens
        2, //number of formats
        0, //imageByteOrder
        0, //bitmapFormatBitOrder
        32, //bitmapFormatScanlineUnit
        32, //bitmapFormatScanlinePad
        8, //minKeycode
        -1, //maxKeycode 255
        0, 0, 0, 0, //unused
        vendor[0], vendor[1], vendor[2], vendor[3], vendor[4], vendor[5], vendor[6], vendor[7], vendor[8], vendor[9],
        vendor[10], vendor[11], vendor[12], vendor[13], vendor[14], vendor[15], vendor[16], vendor[17], vendor[18],
        vendor[19],
        //no padding
        1, 1, 32, 0, 0, 0, 0, 0, //format
        4, 8, 32, 0, 0, 0, 0, 0, //format

    };
    ByteArrayInputStream in = new ByteArrayInputStream(inBytes);
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    when(socket.getInputStream()).thenReturn(in);
    when(socket.getOutputStream()).thenReturn(out);

    ConnectionSuccess success = ConnectionSuccess.builder()
        .protocolMajorVersion(11)
        .protocolMinorVersion(0)
        .releaseNumber(12008000)
        .resourceIdBase(50331648)
        .resourceIdMask(2097151)
        .motionBufferSize(256)
        .maximumRequestLength(255)
        .imageByteOrder(ByteOrder.LITTLE_ENDIAN)
        .bitmapFormatBitOrder(ByteOrder.LITTLE_ENDIAN)
        .bitmapFormatScanlineUnit(32)
        .bitmapFormatScanlinePad(32)
        .minKeycode(8)
        .maxKeycode(255)
        .vendor("The X.Org Foundation")
        .format(new Format(1, 1, 32))
        .format(new Format(4, 8, 32))
        .build();

    DisplayName name = new DisplayName(":0");
    X11Connection connection = new X11Connection(name, xAuthority, socket);

    assertThat(connection.getDisplayName()).isEqualTo(name);
    assertThat(connection.getXAuthority()).isEqualTo(xAuthority);
    assertThat(connection.getConnectionSuccess()).isEqualTo(success);

    assertThat(out.toByteArray()).containsExactly(
        'B',
        0,
        0, 11,
        0, 0,
        0, xAuthority.getProtocolName().length(),
        0, xAuthority.getProtocolData().length,
        0, 0,
        'M', 'I', 'T', '-', 'M', 'A', 'G', 'I', 'C', '-', 'C', 'O', 'O', 'K', 'I', 'E', '-', '1',
        0, 0,
        1, 2, 3,
        0
    );
  }
}
