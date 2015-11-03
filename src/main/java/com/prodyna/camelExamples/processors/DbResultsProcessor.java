package com.prodyna.camelExamples.processors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Inject;
import javax.inject.Qualifier;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.sql.SqlConstants;
import org.slf4j.Logger;

import com.prodyna.camelExamples.processors.DbResultsProcessor.DbResults;

@DbResults
public class DbResultsProcessor implements Processor {

    @Inject
    private Logger log;

    @Override
    public void process(Exchange exchange) throws Exception {
        Integer count = exchange.getIn().getHeader(SqlConstants.SQL_UPDATE_COUNT, Integer.class);
        log.info(count/2 + " rows written.");
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
    public @interface DbResults {

    }

}
