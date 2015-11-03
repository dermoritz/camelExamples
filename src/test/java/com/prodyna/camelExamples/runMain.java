package com.prodyna.camelExamples;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class runMain {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Before
    public void prepare(){
        Util.copyAllFiles(Util.getFolderOfResourceFolder("gz"), temp.getRoot().getAbsolutePath());
    }

	@Test
	public void run(){
		org.jboss.weld.environment.se.StartMain.main(new String[]{temp.getRoot().getAbsolutePath(),"C:\\Users\\moritz\\Downloads\\dbTest"});
	}

}
