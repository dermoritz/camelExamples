package com.prodyna.camelExamples.provider;

import com.prodyna.camelExamples.Main;
import org.jboss.weld.environment.se.beans.ParametersFactory;

import javax.enterprise.inject.Produces;
import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import static java.util.Collections.*;

/**
 * Created by moritz on 11.12.2015.
 */
public class ParameterProvider extends ParametersFactory{

    private String[] args;
    private List<String> argsList;

    public ParameterProvider() {
        this.setArgs(Main.getArgs());
    }

    /**
     * Producer method for the injectable command line args.
     *
     * @return The command line arguments.
     */
    @Produces
    @Parameters
    public List<String> getArgs() {
        return argsList;
    }

    /**
     * Producer method for the injectable command line args.
     *
     * @return The command line arguments.
     */
    @Produces
    @Parameters
    public String[] getArgsAsArray() {
        return this.args;
    }

    /**
     * StartMain passes in the command line args here.
     *
     * @param args The command line arguments. If null is given then an empty
     *             array will be used instead.
     */
    public void setArgs(String[] args) {
        if (args == null) {
            args = new String[]{};
        }
        this.args = args;
        this.argsList = unmodifiableList(new ArrayList<String>(Arrays.asList(args)));
    }

    @Qualifier
    @Retention(RUNTIME)
    @Target({PARAMETER, METHOD, FIELD, TYPE})
    public @interface Parameters {
    }
}
