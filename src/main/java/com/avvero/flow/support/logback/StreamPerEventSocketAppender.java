package com.avvero.flow.support.logback;

import ch.qos.logback.classic.net.LoggingEventPreSerializationTransformer;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.PreSerializationTransformer;

/**
 * @author fxdev-belyaev-ay
 */
public class StreamPerEventSocketAppender extends AbstractStreamPerEventSocketAppender<ILoggingEvent> {

    private static final PreSerializationTransformer<ILoggingEvent> pst =
            new LoggingEventPreSerializationTransformer();

    private boolean includeCallerData = false;

    public StreamPerEventSocketAppender() {
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
