package com.prodyna.camelExamples;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jboss.weld.environment.se.bindings.Parameters;

import com.prodyna.camelExamples.qualifier.Folder;

public class ArgsConfiguration {

	private List<String> args;
	private File folder;

	@Inject
	private ArgsConfiguration(@Parameters List<String> args){
		this.args = checkNotNull(args);
		folder = checkFolder();
	}

	@Produces
	@Folder
	public File getFolder(){
		return folder;
	}

	private File checkFolder(){
		checkArgument(args.size()>0);
		File file = new File(args.get(0));
		checkArgument(file.isDirectory());
		checkArgument(file.canRead());
		return file;
	}

}
