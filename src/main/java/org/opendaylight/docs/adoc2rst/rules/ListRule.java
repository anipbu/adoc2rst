package org.opendaylight.docs.adoc2rst.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ListRule implements Rule {
	public ListRule(Properties properties) {
		
	}
    @Override
    public List<String> execute(List<String> lines) {
        List<String> output = new ArrayList<String>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.startsWith("* ")) {
                output.add("- " + line.substring(2));
            } else if (line.startsWith("** ")) {
                output.add("    - " + line.substring(2));
            } else {
                output.add(line);
            }
        }
        return output;
    }
}
