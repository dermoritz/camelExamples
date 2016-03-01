package com.prodyna.camelExamples.endpoints;

import com.prodyna.camelExamples.provider.ArgsConfiguration;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.component.file.FileEndpoint;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Qualifier;
import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by moritz on 01.03.2016.
 */
public class FileEndpointProvider {

    private final CamelContext context;

    @Inject
    public FileEndpointProvider(CamelContext context) {
        this.context = context;
    }

    @Produces
    @FolderEndpoint
    private Endpoint getFolderEndpoint(@ArgsConfiguration.Folder File folder) {
        FileEndpoint endpoint = context.getEndpoint("file:" + folder.getPath(), FileEndpoint.class);
        return endpoint;
    }

    @Produces
    @TargetFolderEndpoint
    private Endpoint getTargetFolder(@ArgsConfiguration.TargetFolder File folder) {
        FileEndpoint endpoint = context.getEndpoint("file:" + folder.getPath(), FileEndpoint.class);
        return endpoint;
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public static @interface TargetFolderEndpoint {

    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    public static @interface FolderEndpoint {

    }

}
