package org.confetti.xml.core.space;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.confetti.xml.core.ConstraintXmlVisitor;

/**
 * @author Bubla Gabor
 */
@XmlRootElement
@XmlType(propOrder = {"weight", "students", "maxBuildingChangesPerWeek", "active", "comment"})
public class ConstraintStudentsSetMaxBuildingChangesPerWeek extends SpaceConstraint {
	@XmlElement(name = "Students") private String students;
	@XmlElement(name = "Max_Building_Changes_Per_Week") private int maxBuildingChangesPerWeek;
	
	@Override
	public <R, P> R accept(ConstraintXmlVisitor<R, P> visitor, P param) {
		return visitor.visitSpace(this, param);
	}
}