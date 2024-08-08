package com.github.moaxcp.x11.x11client.api;

import com.github.moaxcp.x11.protocol.*;
import com.github.moaxcp.x11.protocol.xproto.*;
import com.github.moaxcp.x11.x11client.X11ClientException;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ByteList;
import org.eclipse.collections.api.list.primitive.IntList;
import org.eclipse.collections.impl.factory.Lists;

import static com.github.moaxcp.x11.protocol.Utilities.*;
import static com.github.moaxcp.x11.protocol.xproto.EventMask.EXPOSURE;
import static com.github.moaxcp.x11.protocol.xproto.XprotoUtilities.toChar2bString;

public interface XprotoApi extends XApi {

    /**
     * Sends a {@link GetInputFocus} to the server. This is a {@link TwoWayRequest} which causes a {@link com.github.moaxcp.x11.x11client.X11Client#flush()} and
     * all events to be read from the server and added to the event queue.
     * See <a href="https://github.com/mirror/libX11/blob/caa71668af7fd3ebdd56353c8f0ab90824773969/src/Sync.c">...</a>
     */
    default void sync() {
        send(GetInputFocus.builder().build());
    }

    int nextResourceId();

    AtomValue getAtom(String name);

    /**
     * Creates a simple window on the default screen.
     * @param x coordinate
     * @param y coordinate
     * @param width of window
     * @param height of window
     * @param events events to receive
     * @return the resource id for the window
     */
    default int createSimpleWindow(int x, int y, int width, int height, EventMask... events) {
        int wid = nextResourceId();
        send(CreateWindow.builder()
                .depth(getDefaultDepth())
                .wid(wid)
                .parent(getDefaultRoot())
                .x((short) x)
                .y((short) y)
                .width((short) width)
                .height((short) height)
                .borderWidth((short) 0)
                .clazz(WindowClass.COPY_FROM_PARENT)
                .visual(getDefaultVisualId())
                .backgroundPixel(getDefaultWhitePixel())
                .borderPixel(getDefaultBlackPixel())
                .eventMaskEnable(events)
                .build());
        return wid;
    }

    default void changeWindowEvents(int wid, EventMask... events) {
        send(ChangeWindowAttributes.builder()
                .window(wid)
                .eventMaskEnable(events)
                .build());
    }

    //XRaiseWindow https://github.com/mirror/libX11/blob/caa71668af7fd3ebdd56353c8f0ab90824773969/src/RaiseWin.c
    default void raiseWindow(int wid) {
        send(ConfigureWindow.builder()
                .window(wid)
                .stackMode(StackMode.ABOVE)
                .build());
    }

    default void exposeWindow(int wid) {
        send(SendEvent.builder()
                .destination(wid)
                .eventMaskEnable(EXPOSURE)
                .event(toByteList(ExposeEvent.builder()
                        .firstEventOffset(getFirstEvent(ExposeEvent.PLUGIN_NAME).orElseThrow())
                        .sentEvent(true)
                        .window(wid)
                        .build()))
                .build());
    }

    default void setWindowName(int wid, String name) {
        send(ChangeProperty.builder()
                .window(wid)
                .mode(PropMode.REPLACE)
                .property(Atom.WM_NAME.getValue())
                .type(Atom.STRING.getValue())
                .format((byte) 8)
                .dataLen(name.length())
                .data(Utilities.toByteList(name))
                .build());
    }

    default void setIconName(int wid, String name) {
        send(ChangeProperty.builder()
            .window(wid)
            .mode(PropMode.REPLACE)
            .property(Atom.WM_ICON_NAME.getValue())
            .type(Atom.STRING.getValue())
            .format((byte) 8)
            .dataLen(name.length())
            .data(Utilities.toByteList(name))
            .build());
    }

    default IntList getWMProtocols(int wid) {
        GetPropertyReply property = send(GetProperty.builder()
                .window(wid)
                .property(getAtom("WM_PROTOCOLS").getId())
                .longOffset(0)
                .longLength(1000000)
                .delete(false)
                .build());
        if(property.getFormat() != 32) {
            throw new X11ClientException("expected format for property \"WM_PROTOCOLS\" to be 32 but was \"" + property.getFormat() + "\"");
        }
        if(property.getType() != Atom.ATOM.getValue()) {
            throw new X11ClientException("expected type for property \"WM_PROTOCOLS\" to be \"" + Atom.ATOM.getValue() + "\" but was \"" + property.getType() + "\"");
        }
        return toIntegers(property.getValue());
    }

    default void setWMProtocols(int wid, int atom) {
        ByteList bytes;
        if (getBigEndian()) {
            bytes = BigEndian.writeList(atom);
        } else {
            bytes = LittleEndian.writeList(atom);
        }
        send(ChangeProperty.builder()
                .window(wid)
                .property(getAtom("WM_PROTOCOLS").getId())
                .type(Atom.ATOM.getValue())
                .format((byte) 32)
                .mode(PropMode.REPLACE)
                .dataLen(1)
                .data(bytes)
                .build());
    }

    default void mapWindow(int wid) {
        send(MapWindow.builder()
                .window(wid)
                .build());
    }

    default int createGC(int screen, int wid) {
        int gc = nextResourceId();
        send(CreateGC.builder()
                .cid(gc)
                .drawable(wid)
                .background(getWhitePixel(screen))
                .foreground(getBlackPixel(screen))
                .build());
        return gc;
    }

    default void freeGC(int gc) {
        send(FreeGC.builder().gc(gc).build());
    }

    default void polyPoint(int drawable, int gc, CoordMode mode, ImmutableList<Point> points) {
        send(PolyPoint.builder()
                .drawable(drawable)
                .gc(gc)
                .coordinateMode(mode)
                .points(points)
                .build());
    }

    default void polyLine(int drawable, int gc, CoordMode mode, ImmutableList<Point> points) {
        send(PolyLine.builder()
                .drawable(drawable)
                .gc(gc)
                .coordinateMode(mode)
                .points(points)
                .build());
    }

    default void polySegment(int drawable, int gc, ImmutableList<Segment> segments) {
        send(PolySegment.builder()
                .drawable(drawable)
                .gc(gc)
                .segments(segments)
                .build());
    }

    default void polyRectangle(int drawable, int gc, ImmutableList<Rectangle> rectangles) {
        send(PolyRectangle.builder()
                .drawable(drawable)
                .gc(gc)
                .rectangles(rectangles)
                .build());
    }

    default void polyArc(int drawable, int gc, ImmutableList<Arc> arcs) {
        send(PolyArc.builder()
                .drawable(drawable)
                .gc(gc)
                .arcs(arcs)
                .build());
    }

    default void fillPoly(int drawable, int gc, PolyShape shape, CoordMode mode, ImmutableList<Point> points) {
        send(FillPoly.builder()
                .drawable(drawable)
                .gc(gc)
                .shape(shape)
                .coordinateMode(mode)
                .points(points)
                .build());
    }

    default void fillRectangle(int drawable, int gc, short x, short y, short width, short height) {
        send(PolyFillRectangle.builder()
                .drawable(drawable)
                .gc(gc)
                .rectangles(Lists.immutable.of(Rectangle.builder()
                        .x(x)
                        .y(y)
                        .width(width)
                        .height(height)
                        .build()))
                .build());
    }

    default void polyFillRectangle(int drawable, int gc, ImmutableList<Rectangle> rectangles) {
        send(PolyFillRectangle.builder()
                .drawable(drawable)
                .gc(gc)
                .rectangles(rectangles)
                .build());
    }

    default void polyFillArc(int drawable, int gc, ImmutableList<Arc> arcs) {
        send(PolyFillArc.builder()
                .drawable(drawable)
                .gc(gc)
                .arcs(arcs)
                .build());
    }

    /**
     * see https://github.com/mirror/libX11/blob/master/src/Text.c
     * @param drawable
     * @param gc
     * @param x
     * @param y
     * @param string
     */
    default void imageText8(int drawable, int gc, short x, short y, String string) {
        send(ImageText8.builder()
                .drawable(drawable)
                .gc(gc)
                .x(x)
                .y(y)
                .string(Utilities.toByteList(string))
                .build());
    }

    default void imageText16(int drawable, int gc, short x, short y, String string) {
        send(ImageText16.builder()
                .drawable(drawable)
                .gc(gc)
                .x(x)
                .y(y)
                .string(toChar2bString(string))
                .build());
    }

    default int openFont(String name) {
        var fid = nextResourceId();
        send(OpenFont.builder()
            .fid(fid)
            .name(toList(name.getBytes()))
            .build());
        return fid;
    }

    default void closeFont(int font) {
        send(CloseFont.builder()
            .font(font)
            .build());
    }

    default void killClient(int resource) {
        send(KillClient.builder()
                .resource(resource)
                .build());
    }

    default void inputFocus(int wid) {
        send(SetInputFocus.builder()
                .focus(wid)
                .revertTo(InputFocus.POINTER_ROOT)
                .build());
    }
}
