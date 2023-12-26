package com.github.moaxcp.x11client.protocol;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Utilities {
  public static List<Byte> toList(@NonNull byte[] bytes) {
    List<Byte> list = new ArrayList<>();
    for(byte b : bytes) {
      list.add(b);
    }
    return Collections.unmodifiableList(list);
  }

  public static X11Input toX11Input(@NonNull List<Byte> bytes) {
    return new X11InputStream(new BufferedInputStream(new InputStream() {
      private Iterator<Byte> iterator = bytes.iterator();
      @Override
      public int read() throws IOException {
        if (iterator.hasNext()) {
          return Byte.toUnsignedInt(iterator.next());
        }
        return -1;
      }
    }));
  }

  public static String toString(@NonNull List<Byte> byteList, @NonNull Charset charset) {
    byte[] bytes = new byte[byteList.size()];
    for(int i = 0; i < bytes.length; i++) {
      bytes[i] = byteList.get(i);
    }
    return new String(bytes, charset);
  }

  public static List<Byte> toByteList(@NonNull String string) {
    return toList(string.getBytes());
  }

  public static List<Byte> toByteList(@NonNull String string, @NonNull Charset charset) {
    return toList(string.getBytes(charset));
  }

  public static List<Byte> toByteList(XEvent event) {
    ByteArrayOutputStream saved = new ByteArrayOutputStream();
    X11Output out = new X11OutputStream(saved);
    try {
      event.write(out);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
    return toList(saved.toByteArray());
  }

  public static List<Byte> toByteList(byte majorOpcode, XRequest request) {
    ByteArrayOutputStream saved = new ByteArrayOutputStream();
    X11Output out = new X11OutputStream(saved);
    try {
      request.write(majorOpcode, out);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
    return toList(saved.toByteArray());
  }

  public static List<Integer> toIntegers(@NonNull List<Byte> bytes) {
    if(bytes.size() % 4 != 0) {
      throw new IllegalArgumentException("bytes must have size divisible by 4 to be converted to integers got: " + bytes.size());
    }

    List<Integer> result = new ArrayList<>();
    for(int i = 0; i < bytes.size(); i += 4) {
      int first = bytes.get(i) << 24;
      int second = bytes.get(i + 1) << 24 >>> 8;
      int third = bytes.get(i + 2) << 24 >>> 16;
      int fourth = bytes.get(i + 3) << 24 >>> 24;
      result.add(first | second | third | fourth);
    }

    return result;
  }
}
