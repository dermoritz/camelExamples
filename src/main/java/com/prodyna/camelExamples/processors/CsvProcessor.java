package com.prodyna.camelExamples.processors;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@com.prodyna.camelExamples.qualifier.CsvProcessor
public class CsvProcessor implements Processor {

	@SuppressWarnings("unchecked")
	@Override
	public void process(Exchange exchange) throws Exception {
		List<List<String>> in =  exchange.getIn().getBody(List.class);
		for (List<String> list : in) {
			//System.out.println(list);
		}
	}

}
