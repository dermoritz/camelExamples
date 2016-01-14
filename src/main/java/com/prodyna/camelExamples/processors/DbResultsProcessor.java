package com.prodyna.camelExamples.processors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Inject;
import javax.inject.Qualifier;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.api.management.ManagedAttribute;
import org.apache.camel.api.management.ManagedResource;
import org.apache.camel.component.sql.SqlConstants;
import org.slf4j.Logger;

import com.prodyna.camelExamples.processors.DbResultsProcessor.DbResults;

@DbResults
@ManagedResource(description = "Counts lines written to db.")
public class DbResultsProcessor implements Processor {

    @Inject
    private Logger log;

    private volatile long written;

    @Override
    public void process(Exchange exchange) throws Exception {
        Integer count = exchange.getIn().getHeader(SqlConstants.SQL_UPDATE_COUNT, Integer.class);
        int realCount = -1*(count/2);
        written = written + realCount;
        log.info(realCount + " rows written.");
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
    public @interface DbResults {

    }

    @ManagedAttribute
    public long getWritten() {
        log.info("written:" + written);
        return written;
    }
}
