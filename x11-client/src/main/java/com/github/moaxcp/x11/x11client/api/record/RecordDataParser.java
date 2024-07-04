package com.github.moaxcp.x11.x11client.api.record;

import com.github.moaxcp.x11.protocol.*;
import com.github.moaxcp.x11.protocol.record.Category;
import com.github.moaxcp.x11.protocol.record.EnableContextReply;
import com.github.moaxcp.x11.protocol.record.HType;
import com.github.moaxcp.x11.x11client.X11Client;
import com.github.moaxcp.x11.x11client.api.record.RecordData.RecordDataBuilder;
import com.github.moaxcp.x11.x11client.api.record.RecordReply.RecordReplyBuilder;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.github.moaxcp.x11.protocol.Utilities.toX11Input;

class RecordDataParser {
  private final X11Client client;
  private final EnableContextReply reply;
  private final ReplySequenceTracker tracker;
  private final X11Input data;

  public RecordDataParser(X11Client client, EnableContextReply reply, ReplySequenceTracker tracker) {
    this.client = client;
    this.reply = reply;
    this.tracker = tracker;
    data = toX11Input(reply.getData());
  }

  public RecordReply parse() {
    if (reply.isClientSwapped()) {
      //throw new UnsupportedOperationException("client swapped is not supported " + reply);
    }
    RecordReplyBuilder builder = RecordReply.builder()
        .category(Category.getByCode(reply.getCategory()))
        .sequenceNumber(reply.getSequenceNumber())
        .elementHeader(reply.getElementHeader())
        .clientSwapped(reply.isClientSwapped())
        .xidBase(reply.getXidBase())
        .serverTime(reply.getServerTime())
        .recSequenceNum(reply.getRecSequenceNum());
    Category category = Category.getByCode(reply.getCategory());
    boolean fromServerTime = HType.FROM_SERVER_TIME.isEnabled(reply.getElementHeader());
    boolean fromClientTime = HType.FROM_CLIENT_TIME.isEnabled(reply.getElementHeader());
    boolean fromClientSequence = HType.FROM_CLIENT_SEQUENCE.isEnabled(reply.getElementHeader());

    if(reply.getData().isEmpty()) {
      return builder.build();
    }
    switch(category) {
      case FROM_SERVER:
        //parse reply, event, error
        builder.data(readFromServer(fromServerTime, fromClientTime, fromClientSequence));
        break;
      case FROM_CLIENT:
        //parse request
        builder.data(readFromClient(fromServerTime, fromClientTime, fromClientSequence));
        break;
      case CLIENT_DIED:
        //parse sequence if enabled
        break;
      case CLIENT_STARTED:
      case START_OF_DATA:
      case END_OF_DATA:
    }
    return builder.build();
  }

  private List<RecordData> readFromClient(boolean fromServerTime, boolean fromClientTime, boolean fromClientSequence) {
    List<RecordData> result = new ArrayList<>();

    int startSequence = reply.getRecSequenceNum();
    try {
      while (true) {
        startSequence++;
        RecordDataBuilder builder = RecordData.builder();
        addHeader(data, fromServerTime, fromClientTime, fromClientSequence, builder);
        XRequest xRequest = client.readRequest(data);
        if (xRequest instanceof TwoWayRequest) {
          TwoWayRequest<XReply> twoWay = (TwoWayRequest<XReply>) xRequest;

          tracker.add(reply.getXidBase(), startSequence, twoWay.getReplyFunction());
        }
        builder.xObject(xRequest);
        result.add(builder.build());
      }
    } catch(EOFException e) {
      //ignored
    } catch (IOException e) {
      throw new X11ProtocolException("could not perform readFromServer for record data", e);
    }
    return result;
  }

  private List<RecordData> readFromServer(boolean fromServerTime, boolean fromClientTime, boolean fromClientSequence) {
    List<RecordData> result = new ArrayList<>();
    try {
      while (true) {
        RecordDataBuilder builder = RecordData.builder();
        addHeader(data, fromServerTime, fromClientTime, fromClientSequence, builder);

        byte responseCode = data.peekByte();
        if (responseCode == 0) {
          data.readByte();
          builder.xObject(client.readError(data));
        } else if (responseCode == 1) {
          class ReplyHeader implements X11InputConsumer {
            byte data;
            short sequence;

            public ReplyHeader() {
            }

            @Override
            public void accept(X11Input in) throws IOException {
              data = in.readByte();
              sequence = in.readCard16();
            }

            public byte getData() {
              return this.data;
            }

            public short getSequence() {
              return this.sequence;
            }

            public void setData(byte data) {
              this.data = data;
            }

            public void setSequence(short sequence) {
              this.sequence = sequence;
            }

            public boolean equals(final Object o) {
              if (o == this) return true;
              if (!(o instanceof ReplyHeader)) return false;
              final ReplyHeader other = (ReplyHeader) o;
              if (!other.canEqual((Object) this)) return false;
              if (this.getData() != other.getData()) return false;
              if (this.getSequence() != other.getSequence()) return false;
              return true;
            }

            protected boolean canEqual(final Object other) {
              return other instanceof ReplyHeader;
            }

            public int hashCode() {
              final int PRIME = 59;
              int result = 1;
              result = result * PRIME + this.getData();
              result = result * PRIME + this.getSequence();
              return result;
            }

            public String toString() {
              return "ReplyHeader(data=" + this.getData() + ", sequence=" + this.getSequence() + ")";
            }
          }
          ReplyHeader replyHeader = new ReplyHeader();
          data.peekWith(replyHeader);
          builder.xObject(client.readReply(data, tracker.use(reply.getXidBase(), replyHeader.sequence)));
        } else {
          builder.xObject(client.readEvent(data));
        }
        result.add(builder.build());
      }
    } catch(EOFException e) {
      //ignored
    } catch (IOException e) {
      throw new X11ProtocolException("could not perform readFromServer for record data", e);
    }
    return result;
  }

  private static void addHeader(X11Input data, boolean fromServerTime, boolean fromClientTime, boolean fromClientSequence, RecordDataBuilder builder) {
    try {
      if (fromServerTime) {
        builder.fromServerTime(data.readCard32());
      } else if (fromClientTime) {
        builder.fromClientTime(data.readCard32());
      } else if (fromClientSequence) {
        builder.fromClientSequence(data.readCard32());
      }
    } catch (IOException e) {
      throw new X11ProtocolException("could not read data header", e);
    }
  }
}
