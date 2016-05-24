/*
 * Copyright (c) 2016 Company and Others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.docs.adoc2rst;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.opendaylight.docs.adoc2rst.model.Adoc;
import org.opendaylight.docs.adoc2rst.rules.BoldRule;
import org.opendaylight.docs.adoc2rst.rules.ListRule;
import org.opendaylight.docs.adoc2rst.rules.QuoteRule;
import org.opendaylight.docs.adoc2rst.rules.Rule;
import org.opendaylight.docs.adoc2rst.rules.SectionRule;

public class Converter {
    public static final boolean DEBUG = true;
    public static final String DEFAULT_CONFIGURATION_FILEPATH = "conf/adoc2rst.conf";
    public static final String EXTENSIONS_ARRAY = "org.opendaylight.docs.adoc2rst.extensions";
    public static final String INPUT_LOCATION = "org.opendaylight.docs.adoc2rst.input";
    public static final String OUTPUT_LOCATION = "org.opendaylight.docs.adoc2rst.output";
    private Properties properties;
    private List<Adoc> adocs;
    private List<Rule> rules;
    public Converter(String[] args) {
    	InputStream propoertiesStream = null;
        try {
            // Load Properties
            propoertiesStream = new FileInputStream((args != null && args.length > 0 && args[0] != null) ? args[0] : DEFAULT_CONFIGURATION_FILEPATH);
            properties = new Properties();
            properties.load(propoertiesStream);
            // Load Extensions
            String[] extensions = properties.getProperty(EXTENSIONS_ARRAY).split(",");
            // Load Files
            adocs = new ArrayList<Adoc>();
            List<String> inputs = FileUtils.readLines(new File(properties.getProperty(INPUT_LOCATION)));
            for (String input : inputs) {
                for (File file : FileUtils.listFiles(new File(input), extensions, false)) {
                    adocs.add(new Adoc(input, file.getName(), file));
                }
            }
            // Load Rules
            rules = new ArrayList<Rule>();
            rules.add(new QuoteRule(properties));
            rules.add(new SectionRule(properties));
            rules.add(new ListRule(properties));
            rules.add(new BoldRule(properties));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (propoertiesStream != null) {
                try {
                    propoertiesStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public int execute() {
        int status = 0;
        try {
            for (Adoc adoc : adocs) {
                List<String> lines = FileUtils.readLines(adoc.getFile());
                for (Rule rule : rules) {
                    lines = rule.execute(lines);
                }
                String outpath = properties.getProperty(OUTPUT_LOCATION) + File.separator + adoc.getFilepath() + File.separator + adoc.getFilename() + ".rst";
                File outfile = new File(outpath);
                FileUtils.writeLines(outfile, lines);
            }
            if (DEBUG) for (File file : FileUtils.listFiles(new File(properties.getProperty(OUTPUT_LOCATION)), new String[] {"rst"}, true)) System.out.println(FileUtils.readFileToString(file));
        } catch (IOException e) {
            e.printStackTrace();
            status = 1;
        }
        return status;
    }
    public static void main(String[] args) {
    	Converter application = new Converter(args);
        int status = application.execute();
        System.out.println("Completed with status: " + status);
        System.exit(status);
    }
}
