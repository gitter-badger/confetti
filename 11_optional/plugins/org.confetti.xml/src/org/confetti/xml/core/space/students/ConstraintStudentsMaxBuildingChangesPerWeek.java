package org.confetti.xml.core.space.students;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.confetti.xml.core.ConstraintXmlVisitor;
import org.confetti.xml.core.space.SpaceConstraint;

/**
 * @author Bubla Gabor
 */
@XmlRootElement
@XmlType(propOrder = {"weight", "maxBuildingChangesPerWeek", "active", "comment"})
public class ConstraintStudentsMaxBuildingChangesPerWeek extends SpaceConstraint {
	@XmlElement(name = "Max_Building_Changes_Per_Week") private int maxBuildingChangesPerWeek;

	@Override
	public <R, P> R accept(ConstraintXmlVisitor<R, P> visitor, P param) {
		return visitor.visitSpace(this, param);
	}
}
