package com.github.moaxcp.x11client.protocol.randr;

import com.github.moaxcp.x11client.protocol.X11Input;
import com.github.moaxcp.x11client.protocol.X11Output;
import com.github.moaxcp.x11client.protocol.XStruct;
import java.io.IOException;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class RefreshRates implements XStruct, RandrObject {
  @NonNull
  private List<Short> rates;

  public static RefreshRates readRefreshRates(X11Input in) throws IOException {
    RefreshRates.RefreshRatesBuilder javaBuilder = RefreshRates.builder();
    short nRates = in.readCard16();
    List<Short> rates = in.readCard16(Short.toUnsignedInt(nRates));
    javaBuilder.rates(rates);
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

  public static class RefreshRatesBuilder {
    public int getSize() {
      return 2 + 2 * rates.size();
    }
  }
}
