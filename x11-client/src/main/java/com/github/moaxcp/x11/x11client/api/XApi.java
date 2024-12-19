package com.github.moaxcp.x11.x11client.api;

import com.github.moaxcp.x11.protocol.*;
import com.github.moaxcp.x11.protocol.xproto.Screen;
import com.github.moaxcp.x11.protocol.xproto.Setup;
import com.github.moaxcp.x11.x11client.X11ClientException;

import java.io.IOException;
import java.util.Optional;

public interface XApi {

    boolean getBigEndian();

    /**
     * Returns the connection setup.
     * @return setup created by x11 server
     */
    Setup getSetup();

    /**
     * Returns the sceen for the number provided.
     * @param screen number
     */
    default Screen getScreen(int screen) {
        return getSetup().getRoots().get(screen);
    }

    int getDefaultScreenNumber();

    /**
     * Returns the default {@link Screen} object.
     */
    default Screen getDefaultScreen() {
        return getScreen(getDefaultScreenNumber());
    }

    /**
     * Returns the root for the provided screen.
     * @param screen number
     */
    default int getRoot(int screen) {
        return getScreen(screen).getRoot();
    }

    /**
     * Returns the default root.
     */
    default int getDefaultRoot() {
        return getRoot(getDefaultScreenNumber());
    }

    /**
     * Returns the white pixel for the provided screen.
     * @param screen number
     */
    default int getWhitePixel(int screen) {
        return getScreen(screen).getWhitePixel();
    }

    /**
     * Returns the default white pixel.
     */
    default int getDefaultWhitePixel() {
        return getWhitePixel(getDefaultScreenNumber());
    }

    /**
     * Returns the black pixel for the provided screen.
     * @param screen number
     */
    default int getBlackPixel(int screen) {
        return getScreen(screen).getBlackPixel();
    }

    /**
     * Returns the default black pixel.
     */
    default int getDefaultBlackPixel() {
        return getBlackPixel(getDefaultScreenNumber());
    }

    /**
     * Returns the depth for the provided screen.
     * @param screen number
     */
    default byte getDepth(int screen) {
        return getScreen(screen).getRootDepth();
    }

    /**
     * Returns the default depth.
     */
    default byte getDefaultDepth() {
        return getDepth(getDefaultScreenNumber());
    }

    /**
     * Returns the visualId for the provided screen.
     * @param screen number
     */
    default int getVisualId(int screen) {
        return getScreen(screen).getRootVisual();
    }

    /**
     * Returns the default visualId.
     */
    default int getDefaultVisualId() {
        return getVisualId(getDefaultScreenNumber());
    }

    Optional<Byte> getFirstEvent(String pluginName);

    <T extends XReply> T send(TwoWayRequest<T> request);
    /**
     * Adds a {@link OneWayRequest} request to the request queue.
     * @param request for server to execute
     */
    void send(OneWayRequest request);

    /**
     * Returns the next reply from the server using the given replyFunction to read it. If there are any events sent by
     * the server the events are added to the event queue.
     * @param replyFunction used to read the reply from the server
     * @return The reply from the server
     * @param <T> type of reply
     * @throws X11ClientException if there is an IOException when reading from the server or if there is an error reading
     * any available events from the server.
     * @throws X11ErrorException if the server returns an x11 error.
     */
    <T extends XReply> T getNextReply(XReplyFunction<T> replyFunction);

    <T extends XReply> T readReply(X11Input in, XReplyFunction<T> replyFunction);

    <T extends XRequest> T readRequest(X11Input in) throws IOException;

    <T extends XError> T readError(X11Input in);

    /**
     * Reads an event from the given bytes.
     * @param in input for the event
     * @return the event
     */
    <T extends XEvent> T readEvent(X11Input in);
}
