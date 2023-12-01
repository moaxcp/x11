package com.github.moaxcp.x11client.protocol.present;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class Pixmap implements OneWayRequest, PresentObject {
  public static final byte OPCODE = 1;

  private int window;

  private int pixmap;

  private int serial;

  private int valid;

  private int update;

  private short xOff;

  private short yOff;

  private int targetCrtc;

  private int waitFence;

  private int idleFence;

  private int options;

  private long targetMsc;

  private long divisor;

  private long remainder;

  @NonNull
  private List<Notify> notifies;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Pixmap readPixmap(X11Input in) throws IOException {
    Pixmap.PixmapBuilder javaBuilder = Pixmap.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int window = in.readCard32();
    javaStart += 4;
    int pixmap = in.readCard32();
    javaStart += 4;
    int serial = in.readCard32();
    javaStart += 4;
    int valid = in.readCard32();
    javaStart += 4;
    int update = in.readCard32();
    javaStart += 4;
    short xOff = in.readInt16();
    javaStart += 2;
    short yOff = in.readInt16();
    javaStart += 2;
    int targetCrtc = in.readCard32();
    javaStart += 4;
    int waitFence = in.readCard32();
    javaStart += 4;
    int idleFence = in.readCard32();
    javaStart += 4;
    int options = in.readCard32();
    javaStart += 4;
    byte[] pad14 = in.readPad(4);
    javaStart += 4;
    long targetMsc = in.readCard64();
    javaStart += 8;
    long divisor = in.readCard64();
    javaStart += 8;
    long remainder = in.readCard64();
    javaStart += 8;
    List<Notify> notifies = new ArrayList<>(length - javaStart);
    while(javaStart < Short.toUnsignedInt(length) * 4) {
      Notify baseObject = Notify.readNotify(in);
      notifies.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.window(window);
    javaBuilder.pixmap(pixmap);
    javaBuilder.serial(serial);
    javaBuilder.valid(valid);
    javaBuilder.update(update);
    javaBuilder.xOff(xOff);
    javaBuilder.yOff(yOff);
    javaBuilder.targetCrtc(targetCrtc);
    javaBuilder.waitFence(waitFence);
    javaBuilder.idleFence(idleFence);
    javaBuilder.options(options);
    javaBuilder.targetMsc(targetMsc);
    javaBuilder.divisor(divisor);
    javaBuilder.remainder(remainder);
    javaBuilder.notifies(notifies);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(pixmap);
    out.writeCard32(serial);
    out.writeCard32(valid);
    out.writeCard32(update);
    out.writeInt16(xOff);
    out.writeInt16(yOff);
    out.writeCard32(targetCrtc);
    out.writeCard32(waitFence);
    out.writeCard32(idleFence);
    out.writeCard32(options);
    out.writePad(4);
    out.writeCard64(targetMsc);
    out.writeCard64(divisor);
    out.writeCard64(remainder);
    for(Notify t : notifies) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 72 + XObject.sizeOf(notifies);
  }

  public static class PixmapBuilder {
    public int getSize() {
      return 72 + XObject.sizeOf(notifies);
    }
  }
}
