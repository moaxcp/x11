package com.github.moaxcp.x11client.protocol.render;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Transform implements XStruct, RenderObject {
  private int matrix11;

  private int matrix12;

  private int matrix13;

  private int matrix21;

  private int matrix22;

  private int matrix23;

  private int matrix31;

  private int matrix32;

  private int matrix33;

  public static Transform readTransform(X11Input in) throws IOException {
    Transform.TransformBuilder javaBuilder = Transform.builder();
    int matrix11 = in.readInt32();
    int matrix12 = in.readInt32();
    int matrix13 = in.readInt32();
    int matrix21 = in.readInt32();
    int matrix22 = in.readInt32();
    int matrix23 = in.readInt32();
    int matrix31 = in.readInt32();
    int matrix32 = in.readInt32();
    int matrix33 = in.readInt32();
    javaBuilder.matrix11(matrix11);
    javaBuilder.matrix12(matrix12);
    javaBuilder.matrix13(matrix13);
    javaBuilder.matrix21(matrix21);
    javaBuilder.matrix22(matrix22);
    javaBuilder.matrix23(matrix23);
    javaBuilder.matrix31(matrix31);
    javaBuilder.matrix32(matrix32);
    javaBuilder.matrix33(matrix33);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt32(matrix11);
    out.writeInt32(matrix12);
    out.writeInt32(matrix13);
    out.writeInt32(matrix21);
    out.writeInt32(matrix22);
    out.writeInt32(matrix23);
    out.writeInt32(matrix31);
    out.writeInt32(matrix32);
    out.writeInt32(matrix33);
  }

  @Override
  public int getSize() {
    return 36;
  }

  public static class TransformBuilder {
    public int getSize() {
      return 36;
    }
  }
}
