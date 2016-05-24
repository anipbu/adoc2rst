package org.opendaylight.docs.adoc2rst.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SectionRule implements Rule {
    public static final String[] INPUT_TOKENS =  {"=", "==", "===", "====", "=====", "======", "=======", "========", "=========", "=========="};
    public static final String[] OUTPUT_TOKENS = {"=", "=",  "-",   "`",    "'",     ".",      "~",       "*",        "+",         "^"};
	public SectionRule(Properties properties) {
		
	}
    @Override
    public List<String> execute(List<String> lines) {
        List<String> output = new ArrayList<String>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            boolean isSection = false;
            for (int j = 0; j < INPUT_TOKENS.length; j++) {
                if (line.startsWith(INPUT_TOKENS[j] + " ")) {
                    String title = line.substring(String.valueOf(INPUT_TOKENS[j] + " ").length());
                    if (line.startsWith("= ")) {
                        output.add(repeat("=", title.length()));
                    }
                    output.add(title);
                    output.add(repeat(OUTPUT_TOKENS[j], title.length()));
                    isSection = true;
                    break;
                }
            }
            if (!isSection) {
                output.add(line);
            }
        }
        return output;
    }
    private String repeat(String c, int iterations) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            builder.append(c);
        }
        return builder.toString();
    }
}
