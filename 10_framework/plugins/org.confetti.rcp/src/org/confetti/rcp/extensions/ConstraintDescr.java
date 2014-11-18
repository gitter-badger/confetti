package org.confetti.rcp.extensions;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;

/**
 * @author Gabor Bubla
 */
public class ConstraintDescr {

    private final String id;
    private final String name;
    private final String description;
    private final List<ConstraintField> fields = new LinkedList<>();

    public ConstraintDescr(IConfigurationElement element) {
        id = element.getAttribute("id");
        name = element.getAttribute("name");
        description = element.getAttribute("description");
        for (IConfigurationElement childrenElement : element.getChildren()) {
            fields.add(new ConstraintField(childrenElement));
        }
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<ConstraintField> getFields() { return fields; }
    
    @Override public String toString() { return getName(); }
}
