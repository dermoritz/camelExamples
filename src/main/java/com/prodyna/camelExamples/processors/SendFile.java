package com.prodyna.camelExamples.processors;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Qualifier;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;

import com.google.common.collect.Lists;
import com.prodyna.camelExamples.provider.ArgsConfiguration.DownloadFolder;
import com.prodyna.camelExamples.processors.SendFile.SendFileProc;

@SendFileProc
public class SendFile implements Processor {

    private File folder;
    private List<Path> files = Lists.newArrayList();
    private Random random = new Random();
    @Inject
    private Logger log;

    @Inject
    private SendFile(@DownloadFolder File folder) {
        this.folder = folder;
    }

    private void fetchAllFiles() {
        files.clear();
        try {
            Files.walkFileTree(folder.toPath(), new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (!attrs.isDirectory()) {
                        files.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }

            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Problem reading folder: ", e);
        }
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        fetchAllFiles();
        if (files.size() > 0) {
            int index = random.nextInt(files.size());
            File file = files.get(index).toFile();
            if (file.canRead()) {
                exchange.getIn().setBody(file);
            }else{
                log.info("File " + file + " not found updating list...");
                exchange.getIn().setBody("file moved or deleted, try again...");
            }
        } else {
            exchange.getIn().setBody("no file found :-(");
            fetchAllFiles();
        }
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
    public @interface SendFileProc {

    }
}
