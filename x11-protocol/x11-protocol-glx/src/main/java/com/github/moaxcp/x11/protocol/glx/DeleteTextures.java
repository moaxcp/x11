package com.github.moaxcp.x11.protocol.glx;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.IntList;

@Value
@Builder
public class DeleteTextures implements OneWayRequest {
  public static final String PLUGIN_NAME = "glx";

  public static final byte OPCODE = (byte) 144;

  private int contextTag;

  @NonNull
  private IntList textures;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DeleteTextures readDeleteTextures(X11Input in) throws IOException {
    DeleteTextures.DeleteTexturesBuilder javaBuilder = DeleteTextures.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int contextTag = in.readCard32();
    int n = in.readInt32();
    IntList textures = in.readCard32(n);
    javaBuilder.contextTag(contextTag);
    javaBuilder.textures(textures.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte)(Byte.toUnsignedInt(OPCODE)));
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

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class DeleteTexturesBuilder {
    public int getSize() {
      return 12 + 4 * textures.size();
    }
  }
}
