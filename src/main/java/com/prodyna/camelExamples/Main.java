package com.prodyna.camelExamples;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 * Created by moritz on 11.12.2015.
 */
public class Main {

    private static String[] args;

    public static void main( String... args ) {
        Main.args = args;
        WeldContainer container = new Weld().initialize();
        Boot boot = container.select( Boot.class ).get();
        boot.start();

    }

    public static String[] getArgs() {
        return args;
    }
}
