package org.confetti.xml.core.time.misc;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.confetti.core.ConstraintAttributes;
import org.confetti.xml.core.ConstraintXmlVisitor;
import org.confetti.xml.core.time.TimeConstraint;

/**
 * @author Bubla Gabor
 */
@XmlRootElement(name = "ConstraintBasicCompulsoryTime")
@XmlType(name = "basicCompulsoryTime_type", propOrder = {"weight", "active", "comment"})
public class ConstraintBasicCompulsoryTime extends TimeConstraint {
	
	ConstraintBasicCompulsoryTime() {
	}
	
	public ConstraintBasicCompulsoryTime(ConstraintAttributes attrs) {
		this(attrs.asDouble("weight-percentage"), attrs.asBoolean("active"));
	}

	public ConstraintBasicCompulsoryTime(double weight, boolean active) {
		setWeight(weight);
		setActive(active);
	}

	@Override
	public <R, P> R accept(ConstraintXmlVisitor<R, P> visitor, P param) {
		return visitor.visitTime(this, param);
	}
}
