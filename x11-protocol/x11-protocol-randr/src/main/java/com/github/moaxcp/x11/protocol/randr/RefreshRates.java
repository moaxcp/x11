package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eclipse.collections.api.list.primitive.ShortList;

@Value
@Builder
public class RefreshRates implements XStruct {
  public static final String PLUGIN_NAME = "randr";

  @NonNull
  private ShortList rates;

  public static RefreshRates readRefreshRates(X11Input in) throws IOException {
    RefreshRates.RefreshRatesBuilder javaBuilder = RefreshRates.builder();
    short nRates = in.readCard16();
    ShortList rates = in.readCard16(Short.toUnsignedInt(nRates));
    javaBuilder.rates(rates.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    short nRates = (short) rates.size();
    out.writeCard16(nRates);
    out.writeCard16(rates);
  }

  @Override
  public int getSize() {
    return 2 + 2 * rates.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public static class RefreshRatesBuilder {
    public int getSize() {
      return 2 + 2 * rates.size();
    }
  }
}
