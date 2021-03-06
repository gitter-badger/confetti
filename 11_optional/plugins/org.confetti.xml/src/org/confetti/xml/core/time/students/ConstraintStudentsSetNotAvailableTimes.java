package org.confetti.xml.core.time.students;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.confetti.xml.core.ConstraintXmlVisitor;
import org.confetti.xml.core.time.BreakTimeXml;
import org.confetti.xml.core.time.TimeConstraint;

/**
 * @author Bubla Gabor
 */
@XmlRootElement
@XmlType(propOrder = {
		"weight", 
		"studentsName", "nrOfNotAvailableTimes", "notAvailableTimes", 
		"active", "comment"})
public class ConstraintStudentsSetNotAvailableTimes extends TimeConstraint {
	@XmlElement(name = "Students") private String studentsName;
	@XmlElement(name = "Number_of_Not_Available_Times") private int nrOfNotAvailableTimes;
	@XmlElement(name = "Not_Available_Time") private List<BreakTimeXml> notAvailableTimes;

	@Override
	public <R, P> R accept(ConstraintXmlVisitor<R, P> visitor, P param) {
		return visitor.visitTime(this, param);
	}
}
