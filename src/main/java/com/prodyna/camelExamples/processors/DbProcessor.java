package com.prodyna.camelExamples.processors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Qualifier;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableMap;
import com.prodyna.camelExamples.endpoints.EndpointProvider;
import com.prodyna.camelExamples.processors.DbProcessor.DbProc;

@DbProc
public class DbProcessor implements Processor {

    @Inject
    private Logger log;

    @SuppressWarnings("unchecked")
    @Override
    public void process(Exchange exchange) throws Exception {
        List<String> line = exchange.getIn().getBody(List.class);
        if (line.size() >= 3) {
            ImmutableMap<String, String> values = ImmutableMap.of(EndpointProvider.UUID_PAR,
                                                                  UUID.randomUUID().toString(),
                                                                  EndpointProvider.COL1,
                                                                  line.get(0),
                                                                  EndpointProvider.COL2,
                                                                  line.get(1),
                                                                  EndpointProvider.COL3,
                                                                  line.get(2));
            exchange.getIn().setBody(values);
        } else {
            log.warn("detected line with less than 3 entries: " + line);
        }

    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
    public @interface DbProc {

    }

}
