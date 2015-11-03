package com.prodyna.camelExamples.processors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Qualifier;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import com.google.common.collect.Lists;
import com.prodyna.camelExamples.processors.MergerAggregator.Merger;

/**
 * Merges exchanges into a list;
 *
 */
@Merger
public class MergerAggregator implements AggregationStrategy{

    @SuppressWarnings("unchecked")
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if(oldExchange == null){
            LinkedList<Object> list = Lists.newLinkedList();
            list.add(newExchange.getIn().getBody());
            newExchange.getIn().setBody(list);
            return newExchange;
        }else {
            List<Object> list = oldExchange.getIn().getBody(List.class);
            list.add(newExchange.getIn().getBody());
            return oldExchange;
        }
    }


    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
    public @interface Merger {

    }
}
