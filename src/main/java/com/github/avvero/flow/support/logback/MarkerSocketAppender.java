package com.github.avvero.flow.support.logback;

import ch.qos.logback.classic.net.LoggingEventPreSerializationTransformer;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.LoggingEventVO;
import ch.qos.logback.core.spi.PreSerializationTransformer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.io.IOException;
import java.io.Serializable;

/**
 * Mark all income events with marker
 *
 * @author fxdev-belyaev-ay
 */
public class MarkerSocketAppender extends AbstractStreamPerEventSocketAppender<ILoggingEvent> {

    public static final String MT = "MESSAGE\ndestination:%s\ncontent-type:text/plain\ncontent-length:%s\n\n%s";

    private static final PreSerializationTransformer<ILoggingEvent> pst =
            new LoggingEventPreSerializationTransformer();

    private boolean includeCallerData = false;
    private String marker;
    private ObjectMapper mapper = new ObjectMapper();

    public MarkerSocketAppender() {
    }

    @Override
    protected void postProcessEvent(ILoggingEvent event) {
        if (includeCallerData) {
            event.getCallerData();
        }
    }

    @Override
    protected byte[] transformEvent(ILoggingEvent event) throws IOException {
        Serializable serializableEvent = getPST().transform(event);
        String m = mapper.writeValueAsString(serializableEvent);
        return String.format(MT, marker, m.getBytes().length, m).getBytes();
    }

    public void setIncludeCallerData(boolean includeCallerData) {
        this.includeCallerData = includeCallerData;
    }

    public PreSerializationTransformer<ILoggingEvent> getPST() {
        return pst;
    }

    public void addError(String msg, Throwable ex) {
        System.out.println(msg);
    }

    public void addInfo(String msg, Throwable ex) {
        System.out.println(msg);
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void append(ILoggingEvent event) {
        if (marker != null && !marker.trim().equals("")) {
            Marker markerObject = MarkerFactory.getMarker(marker);
            if (event instanceof LoggingEvent) {
                ((LoggingEvent) event).setMarker(markerObject);
            }
            if (event instanceof LoggingEventVO) {
                ((LoggingEvent) event).setMarker(markerObject);
            }
        }
        super.append(event);
    }
}
