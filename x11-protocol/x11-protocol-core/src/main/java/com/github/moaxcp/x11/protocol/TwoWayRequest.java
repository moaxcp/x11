package com.github.moaxcp.x11.protocol;

/**
 * A request which expects an instant reply.
 * @param <T>
 */
public interface TwoWayRequest<T extends XReply> extends XRequest {
  XReplyFunction<T> getReplyFunction();
}
