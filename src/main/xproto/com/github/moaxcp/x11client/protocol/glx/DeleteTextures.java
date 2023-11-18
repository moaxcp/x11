package com.github.moaxcp.x11client.protocol.glx;

import com.github.moaxcp.x11client.protocol.OneWayRequest;
import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class DeleteTextures implements OneWayRequest, GlxObject {
  public static final byte OPCODE = (byte) 144;

  private int contextTag;

  @NonNull
  private List<Integer> textures;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DeleteTextures readDeleteTextures(X11Input in) throws IOException {
    DeleteTextures.DeleteTexturesBuilder javaBuilder = DeleteTextures.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int n = in.readInt32();
    List<Integer> textures = in.readCard32(n);
    javaBuilder.contextTag(contextTag);
    javaBuilder.textures(textures);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte offset, X11Output out) throws IOException {
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE) + Byte.toUnsignedInt(offset)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(contextTag);
    int n = textures.size();
    out.writeInt32(n);
    out.writeCard32(textures);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 4 * textures.size();
  }

  public static class DeleteTexturesBuilder {
    public int getSize() {
      return 12 + 4 * textures.size();
    }
  }
}
