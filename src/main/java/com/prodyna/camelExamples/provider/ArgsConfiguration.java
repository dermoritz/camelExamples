package com.prodyna.camelExamples.provider;

import static com.google.common.base.Preconditions.checkArgument;
import static com.prodyna.camelExamples.provider.ParameterProvider.*;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Qualifier;
import javax.inject.Singleton;

import org.slf4j.Logger;

@Singleton
public class ArgsConfiguration {

    private File folder;
    private File targetFolder;
    private File downloadFolder;



    @Inject
    private ArgsConfiguration(@Parameters List<String> args, Logger log) {
        checkArgument(args.size() > 1);
        folder = checkFolder(args.get(0));
        log.info("Source folder is: " + folder);
        targetFolder = checkFolder(args.get(1));
        log.info("Target folder is: " + targetFolder);
        if (args.size() > 2) {
            downloadFolder = checkFolder(args.get(2));
            log.info("Downloadfolder is: " + downloadFolder);
        } else{
            log.info("No dedicated download folder given (3rd argument), using target folder.");
            downloadFolder = targetFolder;
        }

    }

    @Produces
    @Folder
    public File getFolder() {
        return folder;
    }

    @Produces
    @TargetFolder
    public File getTargetFolder() {
        return targetFolder;
    }

    @Produces
    @DownloadFolder
    public File getDownloadFolder() {
        return downloadFolder;
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

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public static @interface DownloadFolder {

    }

}
