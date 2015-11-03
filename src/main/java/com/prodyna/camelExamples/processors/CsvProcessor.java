package com.prodyna.camelExamples.processors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Qualifier;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;

import com.prodyna.camelExamples.processors.CsvProcessor.CsvProc;

@CsvProc
public class CsvProcessor implements Processor {

    @Inject
    private Logger log;

	@SuppressWarnings("unchecked")
	@Override
	public void process(Exchange exchange) throws Exception {
		List<List<String>> in =  exchange.getIn().getBody(List.class);
		for (List<String> list : in) {
			if(list.size()>6){
			    for (String string : list) {
                    if(string.contains("xxx")){
                        list.set(list.indexOf(string), "xyz");
                    }
                }
			}
		}
		exchange.getIn().setBody(in);
	}

	@Qualifier
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
	public @interface CsvProc {

	}


}
