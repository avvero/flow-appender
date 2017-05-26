package com.github.avvero.flow.support.logback;

import ch.qos.logback.classic.net.LoggingEventPreSerializationTransformer;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.PreSerializationTransformer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author fxdev-belyaev-ay
 */
public class StreamPerEventSocketAppender extends AbstractStreamPerEventSocketAppender<ILoggingEvent> {

    private static final PreSerializationTransformer<ILoggingEvent> pst =
            new LoggingEventPreSerializationTransformer();

    private boolean includeCallerData = false;

    private ObjectMapper mapper = new ObjectMapper();

    public StreamPerEventSocketAppender() {
    }

    @Override
    protected byte[] transformEvent(ILoggingEvent event) throws IOException {
        Serializable serializableEvent = getPST().transform(event);
        String m = mapper.writeValueAsString(serializableEvent);
        m = "MESSAGE\n" +
                "destination:test\n" +
                "content-type:text/plain\n" +
                "content-length:" + m.getBytes().length + "\n" +
                "\n" + m;
        return m.getBytes();
    }

    @Override
    protected void postProcessEvent(ILoggingEvent event) {
        if (includeCallerData) {
            event.getCallerData();
        }
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

}
