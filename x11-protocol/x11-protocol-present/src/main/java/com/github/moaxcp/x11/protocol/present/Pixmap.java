package com.github.moaxcp.x11.protocol.present;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

@Value
@Builder
public class Pixmap implements OneWayRequest {
  public static final String PLUGIN_NAME = "present";

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
  private ImmutableList<Notify> notifies;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Pixmap readPixmap(X11Input in) throws IOException {
    Pixmap.PixmapBuilder javaBuilder = Pixmap.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
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
    MutableList<Notify> notifies = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(length) - javaStart);
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
    javaBuilder.notifies(notifies.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class PixmapBuilder {
    public int getSize() {
      return 72 + XObject.sizeOf(notifies);
    }
  }
}
