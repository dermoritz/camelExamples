package com.prodyna.camelExamples;

import java.io.File;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class runMain {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    private String tempPath;

    private File prepared;

    @Before
    public void prepare() {
        tempPath = temp.getRoot().getAbsolutePath();
        Util.copyAllFiles(Util.getFolderOfResourceFolder("gz"), tempPath);
        prepared = new File(tempPath + "/prepared");
        prepared.mkdirs();
    }

    @Test
    public void run() {
        org.jboss.weld.environment.se.StartMain.main(new String[] {temp.getRoot().getAbsolutePath(),
                                                                   prepared.getAbsolutePath()});
    }

}
