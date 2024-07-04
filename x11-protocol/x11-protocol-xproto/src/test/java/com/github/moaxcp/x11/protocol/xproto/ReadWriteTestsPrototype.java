package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class ReadWriteTestsPrototype {
  ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
  X11Output out = new X11OutputStream(outBytes);
  X11Input in;

  private void convertToInput() {
    ByteArrayInputStream inBytes = new ByteArrayInputStream(outBytes.toByteArray());
    in = new X11InputStream(inBytes);
  }

  private void assertWriteObjectEqualsReadObject(ClientMessageDataUnion message, ThrowingBiFunction<X11Input, Byte, ClientMessageDataUnion, IOException> readFunction) throws IOException {
    message.write(out);
    convertToInput();
    ClientMessageDataUnion result = readFunction.apply(in, message.getFormat());
    assertThat(result).isEqualTo(message);
    assertThat(result.getSize()).isEqualTo(outBytes.size());
  }

  private void assertWriteObjectEqualsReadObject(XStruct struct, ThrowingFunction<X11Input, XStruct, IOException> readFunction) throws IOException {
    struct.write(out);
    convertToInput();
    XStruct result = readFunction.apply(in);
    assertThat(result).isEqualTo(struct);
    assertThat(result.getSize()).isEqualTo(outBytes.size());
  }

  private void assertWriteObjectEqualsReadObject(XError error, ThrowingBiFunction<Byte, X11Input, XError, IOException> readFunction) throws IOException {
    error.write(out);
    convertToInput();
    byte responseCode = in.readCard8();
    byte code = in.readCard8();
    assertThat(responseCode).isEqualTo(error.getResponseCode());
    assertThat(code).isEqualTo(error.getCode());
    XError result = readFunction.apply((byte) 0, in);
    assertThat(result).isEqualTo(error);
    assertThat(result.getSize()).isEqualTo(outBytes.size());
  }

  private interface ThrowingFunction<T, R, E extends Exception> {
    R apply(T obj) throws E;
  }

  private interface ThrowingBiFunction<T1, T2, R, E extends Exception> {
    R apply(T1 obj1, T2 obj2) throws E;
  }

  private interface ThrowingTriFunction<T1, T2, T3, R, E extends Exception> {
    R apply(T1 obj1, T2 obj2, T3 obj3) throws E;
  }

  @Test
  void clientMessageData8() throws IOException {
    List<Byte> data8 = IntStream.range(0, 20)
        .mapToObj(i -> (byte) i)
        .collect(toList());
    ClientMessageData8 expected = new ClientMessageData8(data8);

    assertWriteObjectEqualsReadObject(expected, ClientMessageDataUnion::readClientMessageDataUnion);
  }

  @Test
  void clientMessageData16() throws IOException {
    List<Short> data16 = IntStream.range(0, 10)
        .mapToObj(i -> (short) i)
        .collect(toList());
    ClientMessageData16 expected = new ClientMessageData16(data16);

    assertWriteObjectEqualsReadObject(expected, ClientMessageDataUnion::readClientMessageDataUnion);
  }

  @Test
  void clientMessageData32() throws IOException {
    List<Integer> data32 = IntStream.range(0, 5)
        .mapToObj(i -> i)
        .collect(toList());
    ClientMessageData32 expected = new ClientMessageData32(data32);

    assertWriteObjectEqualsReadObject(expected, ClientMessageDataUnion::readClientMessageDataUnion);
  }

  @Test
  void accessError() throws IOException {
    AccessError expected = AccessError.builder()
        .sequenceNumber((short) 13)
        .badValue(1)
        .majorOpcode((byte) 2)
        .minorOpcode((short) 3)
        .build();

    assertWriteObjectEqualsReadObject(expected, AccessError::readAccessError);
  }

  @Test
  void setupRequest() throws IOException {
    SetupRequest expected = SetupRequest.builder()
      .byteOrder((byte) 'B')
      .protocolMajorVersion((short) 11)
      .protocolMinorVersion((short) 0)
      .authorizationProtocolName(Utilities.toList("MIT-MAGIC-COOKIE-1".getBytes()))
      .authorizationProtocolData(Utilities.toList("secret key 123457".getBytes()))
      .build();

    assertWriteObjectEqualsReadObject(expected, SetupRequest::readSetupRequest);
  }
}
