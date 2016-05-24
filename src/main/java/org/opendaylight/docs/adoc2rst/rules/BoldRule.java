package org.opendaylight.docs.adoc2rst.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BoldRule implements Rule {
	public BoldRule(Properties properties) {
		
	}
    @Override
    public List<String> execute(List<String> lines) {
        List<String> output = new ArrayList<String>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            Pattern pattern = Pattern.compile("\\*([^\\*]*)\\*");
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                line = line.replace("*"+matcher.group(1)+"*", "**"+matcher.group(1)+"**");
            }
            output.add(line);
        }
        return output;
    }
    public static void main(String[] args) {
        System.out.println(new BoldRule(null).execute(new ArrayList<String>(Arrays.asList(new String[] {"Test *bold text* here"}))));
    }
}
