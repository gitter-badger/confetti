package org.confetti.xml.core.time.activities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.confetti.xml.core.ConstraintXmlVisitor;
import org.confetti.xml.core.time.TimeConstraint;

/**
 * @author Bubla Gabor
 */
@XmlRootElement
@XmlType(propOrder = {
		"weight", 
		"teacherName", "studentsName", "subjectName", "activityTagName",
		"active", "comment"})
public class ConstraintActivitiesEndStudentsDay extends TimeConstraint {
	@XmlElement(name = "Teacher_Name") private String teacherName;
	@XmlElement(name = "Students_Name") private String studentsName;
	@XmlElement(name = "Subject_Name") private String subjectName;
	@XmlElement(name = "Activity_Tag_Name") private String activityTagName;
	
	@Override
	public <R, P> R accept(ConstraintXmlVisitor<R, P> visitor, P param) {
		return visitor.visitTime(this, param);
	}
}