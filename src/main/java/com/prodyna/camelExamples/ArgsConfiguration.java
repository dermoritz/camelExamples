package com.prodyna.camelExamples;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Qualifier;

import org.jboss.weld.environment.se.bindings.Parameters;

public class ArgsConfiguration {

    private File folder;
    private File targetFolder;

    @Inject
    private ArgsConfiguration(@Parameters List<String> args) {
        checkArgument(args.size() > 1);
        folder = checkFolder(args.get(0));
        targetFolder = checkFolder(args.get(1));
    }

    @Produces
    @Folder
    public File getFolder() {
        return folder;
    }

    @Produces
    @TargetFolder
    public File getTargetFolder(){
        return targetFolder;
    }

    private File checkFolder(String string) {
        File file = new File(string);
        checkArgument(file.isDirectory());
        checkArgument(file.canRead());
        return file;
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public static @interface Folder {

    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public static @interface TargetFolder {

    }

}
