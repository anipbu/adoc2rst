package org.opendaylight.docs.adoc2rst.model;

import java.io.File;

public class Adoc {
    private String filepath;
    private String filename;
    private File file;
    public Adoc(String filepath, String filename, File file) {
        this.filepath = filepath;
        this.filename = filename;
        this.file = file;
    }
    public String getFilepath() {
        return filepath;
    }
    public String getFilename() {
        return filename;
    }
    public File getFile() {
        return file;
    }
}
