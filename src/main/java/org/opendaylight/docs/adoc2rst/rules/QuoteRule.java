package org.opendaylight.docs.adoc2rst.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class QuoteRule implements Rule {
	public QuoteRule(Properties properties) {
		
	}
    @Override
    public List<String> execute(List<String> lines) {
        List<String> output = new ArrayList<String>();
        boolean isQuoting = false;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.equals("----")) {
                if (!isQuoting) {
                    output.add("::");
                }
                isQuoting = !isQuoting;
                continue;
            } else if (isQuoting) {
                output.add("    " + line);
            } else {
                output.add(line);
            }
        }
        return output;
    }
}
