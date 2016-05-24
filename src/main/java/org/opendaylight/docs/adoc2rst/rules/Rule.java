package org.opendaylight.docs.adoc2rst.rules;

import java.util.List;

public interface Rule {
    List<String> execute(List<String> lines);
}
