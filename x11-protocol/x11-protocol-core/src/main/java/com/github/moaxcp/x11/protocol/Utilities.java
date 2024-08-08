package com.github.moaxcp.x11.protocol;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.eclipse.collections.api.iterator.ByteIterator;
import org.eclipse.collections.api.list.primitive.ByteList;
import org.eclipse.collections.api.list.primitive.IntList;
import org.eclipse.collections.impl.factory.primitive.ByteLists;
import org.eclipse.collections.impl.factory.primitive.IntLists;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

@UtilityClass
public class Utilities {
  public static ByteList toList(@NonNull byte[] bytes) {
    return ByteLists.immutable.of(bytes);
  }

  public static X11Input toX11Input(@NonNull ByteList bytes) {
    return toX11Input(false, bytes);
  }

  public static X11Input toX11Input(boolean bigEndian, @NonNull ByteList bytes) {
    if (bigEndian) {
      return new X11BigEndianInputStream(new BufferedInputStream(new InputStream() {
        private ByteIterator iterator = bytes.byteIterator();

        @Override
        public int read() throws IOException {
          if (iterator.hasNext()) {
            return Byte.toUnsignedInt(iterator.next());
          }
          return -1;
        }
      }));
    } else {
      return new X11LittleEndianInputStream(new BufferedInputStream(new InputStream() {
        private ByteIterator iterator = bytes.byteIterator();

        @Override
        public int read() throws IOException {
          if (iterator.hasNext()) {
            return Byte.toUnsignedInt(iterator.next());
          }
          return -1;
        }
      }));
    }
  }

  public static String toString(@NonNull ByteList byteList) {
    byte[] bytes = new byte[byteList.size()];
    for(int i = 0; i < bytes.length; i++) {
      bytes[i] = byteList.get(i);
    }
    return new String(bytes);
  }

  public static String toString(@NonNull ByteList byteList, @NonNull Charset charset) {
    byte[] bytes = new byte[byteList.size()];
    for(int i = 0; i < bytes.length; i++) {
      bytes[i] = byteList.get(i);
    }
    return new String(bytes, charset);
  }

  public static String toString(@NonNull List<Byte> byteList, @NonNull Charset charset) {
    byte[] bytes = new byte[byteList.size()];
    for(int i = 0; i < bytes.length; i++) {
      bytes[i] = byteList.get(i);
    }
    return new String(bytes, charset);
  }

  public static ByteList toByteList(@NonNull String string) {
    return toList(string.getBytes());
  }

  public static ByteList toByteList(@NonNull String string, @NonNull Charset charset) {
    return toList(string.getBytes(charset));
  }

  public static ByteList toByteList(XEvent event) {
    ByteArrayOutputStream saved = new ByteArrayOutputStream();
    X11Output out = new X11BigEndianOutputStream(saved);
    try {
      event.write(out);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
    return toList(saved.toByteArray());
  }

  public static ByteList toByteList(byte majorOpcode, XRequest request) {
    ByteArrayOutputStream saved = new ByteArrayOutputStream();
    X11Output out = new X11BigEndianOutputStream(saved);
    try {
      request.write(majorOpcode, out);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
    return toList(saved.toByteArray());
  }

  public static IntList toIntegers(@NonNull ByteList bytes) {
    if(bytes.size() % 4 != 0) {
      throw new IllegalArgumentException("bytes must have size divisible by 4 to be converted to integers got: " + bytes.size());
    }

    var result = IntLists.mutable.empty();
    for(int i = 0; i < bytes.size(); i += 4) {
      int first = bytes.get(i) << 24;
      int second = bytes.get(i + 1) << 24 >>> 8;
      int third = bytes.get(i + 2) << 24 >>> 16;
      int fourth = bytes.get(i + 3) << 24 >>> 24;
      result.add(first | second | third | fourth);
    }

    return result.toImmutable();
  }
}
