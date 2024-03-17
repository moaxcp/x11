package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import com.github.moaxcp.x11.protocol.render.Transform;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetCrtcTransformReply implements XReply {
  public static final String PLUGIN_NAME = "randr";

  private short sequenceNumber;

  @NonNull
  private Transform pendingTransform;

  private boolean hasTransforms;

  @NonNull
  private Transform currentTransform;

  @NonNull
  private List<Byte> pendingFilterName;

  @NonNull
  private List<Integer> pendingParams;

  @NonNull
  private List<Byte> currentFilterName;

  @NonNull
  private List<Integer> currentParams;

  public static GetCrtcTransformReply readGetCrtcTransformReply(byte pad1, short sequenceNumber,
      X11Input in) throws IOException {
    GetCrtcTransformReply.GetCrtcTransformReplyBuilder javaBuilder = GetCrtcTransformReply.builder();
    int length = in.readCard32();
    Transform pendingTransform = Transform.readTransform(in);
    boolean hasTransforms = in.readBool();
    byte[] pad6 = in.readPad(3);
    Transform currentTransform = Transform.readTransform(in);
    byte[] pad8 = in.readPad(4);
    short pendingLen = in.readCard16();
    short pendingNparams = in.readCard16();
    short currentLen = in.readCard16();
    short currentNparams = in.readCard16();
    List<Byte> pendingFilterName = in.readChar(Short.toUnsignedInt(pendingLen));
    in.readPadAlign(Short.toUnsignedInt(pendingLen));
    List<Integer> pendingParams = in.readInt32(Short.toUnsignedInt(pendingNparams));
    List<Byte> currentFilterName = in.readChar(Short.toUnsignedInt(currentLen));
    in.readPadAlign(Short.toUnsignedInt(currentLen));
    List<Integer> currentParams = in.readInt32(Short.toUnsignedInt(currentNparams));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.pendingTransform(pendingTransform);
    javaBuilder.hasTransforms(hasTransforms);
    javaBuilder.currentTransform(currentTransform);
    javaBuilder.pendingFilterName(pendingFilterName);
    javaBuilder.pendingParams(pendingParams);
    javaBuilder.currentFilterName(currentFilterName);
    javaBuilder.currentParams(currentParams);
    if(javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    pendingTransform.write(out);
    out.writeBool(hasTransforms);
    out.writePad(3);
    currentTransform.write(out);
    out.writePad(4);
    short pendingLen = (short) pendingFilterName.size();
    out.writeCard16(pendingLen);
    short pendingNparams = (short) pendingParams.size();
    out.writeCard16(pendingNparams);
    short currentLen = (short) currentFilterName.size();
    out.writeCard16(currentLen);
    short currentNparams = (short) currentParams.size();
    out.writeCard16(currentNparams);
    out.writeChar(pendingFilterName);
    out.writePadAlign(Short.toUnsignedInt(pendingLen));
    out.writeInt32(pendingParams);
    out.writeChar(currentFilterName);
    out.writePadAlign(Short.toUnsignedInt(currentLen));
    out.writeInt32(currentParams);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 96 + 1 * pendingFilterName.size() + XObject.getSizeForPadAlign(4, 1 * pendingFilterName.size()) + 4 * pendingParams.size() + 1 * currentFilterName.size() + XObject.getSizeForPadAlign(4, 1 * currentFilterName.size()) + 4 * currentParams.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class GetCrtcTransformReplyBuilder {
    public int getSize() {
      return 96 + 1 * pendingFilterName.size() + XObject.getSizeForPadAlign(4, 1 * pendingFilterName.size()) + 4 * pendingParams.size() + 1 * currentFilterName.size() + XObject.getSizeForPadAlign(4, 1 * currentFilterName.size()) + 4 * currentParams.size();
    }
  }
}
