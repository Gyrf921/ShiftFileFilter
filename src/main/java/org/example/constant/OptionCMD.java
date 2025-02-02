package org.example.constant;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

public class OptionCMD {
    public static final Options options = new Options();

    static {
        options.addOption("o", "output", true, "output file path");
        options.addOption("p", "prefix", true, "prefix in file name");
        options.addOption("a", "additional", false, "additional existing files");
        options.addOption("s", "small-statistic", false, "small-statistic");
        options.addOption("f", "full-statistic", false, "full-statistic");
        options.addOption("h", "help", false, "display help information");
    }

    public static final String INT_FILE_NAME = "integers.txt";
    public static final String FLOAT_FILE_NAME = "floats.txt";
    public static final String STR_FILE_NAME = "strings.txt";
}
