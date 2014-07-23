package org.confetti.xml.core.space;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Bubla Gabor
 */
@XmlRootElement(name = "ConstraintActivityPreferredRoom")
@XmlType(name = "preferredRoom_type", 
	propOrder = {"weight", "activityId", "room", "locked", "active", "comment"})
public class ConstraintActivityPreferredRoom extends SpaceConstraint {
	
	private long activityId;
	private String room;
	private boolean locked;
	
	@XmlElement(name = "Activity_Id")
	public long getActivityId() { return activityId; }
	public void setActivityId(long activityId) { this.activityId = activityId; }
	
	@XmlElement(name = "Room")
	public String getRoom() { return room; }
	public void setRoom(String room) { this.room = room; }
	
	@XmlElement(name = "Permanently_Locked")
	public boolean isLocked() { return locked; }
	public void setLocked(boolean locked) { this.locked = locked; }
	
}
