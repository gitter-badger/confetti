package org.confetti.xml.core;

import org.confetti.xml.core.space.activities.ConstraintActivitiesOccupyMaxDifferentRooms;
import org.confetti.xml.core.space.activities.ConstraintActivitiesSameRoomIfConsecutive;
import org.confetti.xml.core.space.activities.ConstraintActivityPreferredRoom;
import org.confetti.xml.core.space.activities.ConstraintActivityPreferredRooms;
import org.confetti.xml.core.space.activitytags.ConstraintActivityTagPreferredRoom;
import org.confetti.xml.core.space.activitytags.ConstraintActivityTagPreferredRooms;
import org.confetti.xml.core.space.misc.ConstraintBasicCompulsorySpace;
import org.confetti.xml.core.space.rooms.ConstraintRoomNotAvailableTimes;
import org.confetti.xml.core.space.students.ConstraintStudentsMaxBuildingChangesPerDay;
import org.confetti.xml.core.space.students.ConstraintStudentsMaxBuildingChangesPerWeek;
import org.confetti.xml.core.space.students.ConstraintStudentsMinGapsBetweenBuildingChanges;
import org.confetti.xml.core.space.students.ConstraintStudentsSetHomeRoom;
import org.confetti.xml.core.space.students.ConstraintStudentsSetHomeRooms;
import org.confetti.xml.core.space.students.ConstraintStudentsSetMaxBuildingChangesPerDay;
import org.confetti.xml.core.space.students.ConstraintStudentsSetMaxBuildingChangesPerWeek;
import org.confetti.xml.core.space.students.ConstraintStudentsSetMinGapsBetweenBuildingChanges;
import org.confetti.xml.core.space.subjects.ConstraintSubjectPreferredRoom;
import org.confetti.xml.core.space.subjects.ConstraintSubjectPreferredRooms;
import org.confetti.xml.core.space.subjects_activitytags.ConstraintSubjectActivityTagPreferredRoom;
import org.confetti.xml.core.space.subjects_activitytags.ConstraintSubjectActivityTagPreferredRooms;
import org.confetti.xml.core.space.teachers.ConstraintTeacherHomeRoom;
import org.confetti.xml.core.space.teachers.ConstraintTeacherHomeRooms;
import org.confetti.xml.core.space.teachers.ConstraintTeacherMaxBuildingChangesPerDay;
import org.confetti.xml.core.space.teachers.ConstraintTeacherMaxBuildingChangesPerWeek;
import org.confetti.xml.core.space.teachers.ConstraintTeacherMinGapsBetweenBuildingChanges;
import org.confetti.xml.core.space.teachers.ConstraintTeachersMaxBuildingChangesPerDay;
import org.confetti.xml.core.space.teachers.ConstraintTeachersMaxBuildingChangesPerWeek;
import org.confetti.xml.core.space.teachers.ConstraintTeachersMinGapsBetweenBuildingChanges;
import org.confetti.xml.core.time.activities.ConstraintActivitiesEndStudentsDay;
import org.confetti.xml.core.time.activities.ConstraintActivitiesMaxSimultaneousInSelectedTimeSlots;
import org.confetti.xml.core.time.activities.ConstraintActivitiesNotOverlapping;
import org.confetti.xml.core.time.activities.ConstraintActivitiesOccupyMaxTimeSlotsFromSelection;
import org.confetti.xml.core.time.activities.ConstraintActivitiesPreferredStartingTimes;
import org.confetti.xml.core.time.activities.ConstraintActivitiesPreferredTimeSlots;
import org.confetti.xml.core.time.activities.ConstraintActivitiesSameStartingDay;
import org.confetti.xml.core.time.activities.ConstraintActivitiesSameStartingHour;
import org.confetti.xml.core.time.activities.ConstraintActivitiesSameStartingTime;
import org.confetti.xml.core.time.activities.ConstraintActivityEndsStudentsDay;
import org.confetti.xml.core.time.activities.ConstraintActivityPreferredStartingTime;
import org.confetti.xml.core.time.activities.ConstraintActivityPreferredStartingTimes;
import org.confetti.xml.core.time.activities.ConstraintActivityPreferredTimeSlots;
import org.confetti.xml.core.time.activities.ConstraintMaxDaysBetweenActivities;
import org.confetti.xml.core.time.activities.ConstraintMinDaysBetweenActivities;
import org.confetti.xml.core.time.activities.ConstraintMinGapsBetweenActivities;
import org.confetti.xml.core.time.activities.ConstraintSubactivitiesPreferredStartingTimes;
import org.confetti.xml.core.time.activities.ConstraintSubactivitiesPreferredTimeSlots;
import org.confetti.xml.core.time.activities.ConstraintThreeActivitiesGrouped;
import org.confetti.xml.core.time.activities.ConstraintTwoActivitiesConsecutive;
import org.confetti.xml.core.time.activities.ConstraintTwoActivitiesGrouped;
import org.confetti.xml.core.time.activities.ConstraintTwoActivitiesOrdered;
import org.confetti.xml.core.time.misc.ConstraintBasicCompulsoryTime;
import org.confetti.xml.core.time.misc.ConstraintBreakTimes;
import org.confetti.xml.core.time.students.ConstraintStudentsActivityTagMaxHoursContinuously;
import org.confetti.xml.core.time.students.ConstraintStudentsActivityTagMaxHoursDaily;
import org.confetti.xml.core.time.students.ConstraintStudentsEarlyMaxBeginningsAtSecondHour;
import org.confetti.xml.core.time.students.ConstraintStudentsIntervalMaxDaysPerWeek;
import org.confetti.xml.core.time.students.ConstraintStudentsMaxGapsPerDay;
import org.confetti.xml.core.time.students.ConstraintStudentsMaxGapsPerWeek;
import org.confetti.xml.core.time.students.ConstraintStudentsMaxHoursContinuously;
import org.confetti.xml.core.time.students.ConstraintStudentsMaxHoursDaily;
import org.confetti.xml.core.time.students.ConstraintStudentsMinHoursDaily;
import org.confetti.xml.core.time.students.ConstraintStudentsSetActivityTagMaxHoursContinuously;
import org.confetti.xml.core.time.students.ConstraintStudentsSetActivityTagMaxHoursDaily;
import org.confetti.xml.core.time.students.ConstraintStudentsSetEarlyMaxBeginningsAtSecondHour;
import org.confetti.xml.core.time.students.ConstraintStudentsSetIntervalMaxDaysPerWeek;
import org.confetti.xml.core.time.students.ConstraintStudentsSetMaxGapsPerDay;
import org.confetti.xml.core.time.students.ConstraintStudentsSetMaxGapsPerWeek;
import org.confetti.xml.core.time.students.ConstraintStudentsSetMaxHoursContinuously;
import org.confetti.xml.core.time.students.ConstraintStudentsSetMaxHoursDaily;
import org.confetti.xml.core.time.students.ConstraintStudentsSetMinHoursDaily;
import org.confetti.xml.core.time.students.ConstraintStudentsSetNotAvailableTimes;
import org.confetti.xml.core.time.teachers.ConstraintTeacherActivityTagMaxHoursContinuously;
import org.confetti.xml.core.time.teachers.ConstraintTeacherActivityTagMaxHoursDaily;
import org.confetti.xml.core.time.teachers.ConstraintTeacherIntervalMaxDaysPerWeek;
import org.confetti.xml.core.time.teachers.ConstraintTeacherMaxDaysPerWeek;
import org.confetti.xml.core.time.teachers.ConstraintTeacherMaxGapsPerDay;
import org.confetti.xml.core.time.teachers.ConstraintTeacherMaxGapsPerWeek;
import org.confetti.xml.core.time.teachers.ConstraintTeacherMaxHoursContinuously;
import org.confetti.xml.core.time.teachers.ConstraintTeacherMaxHoursDaily;
import org.confetti.xml.core.time.teachers.ConstraintTeacherMinDaysPerWeek;
import org.confetti.xml.core.time.teachers.ConstraintTeacherMinHoursDaily;
import org.confetti.xml.core.time.teachers.ConstraintTeacherNotAvailableTimes;
import org.confetti.xml.core.time.teachers.ConstraintTeachersActivityTagMaxHoursContinuously;
import org.confetti.xml.core.time.teachers.ConstraintTeachersActivityTagMaxHoursDaily;
import org.confetti.xml.core.time.teachers.ConstraintTeachersIntervalMaxDaysPerWeek;
import org.confetti.xml.core.time.teachers.ConstraintTeachersMaxDaysPerWeek;
import org.confetti.xml.core.time.teachers.ConstraintTeachersMaxGapsPerDay;
import org.confetti.xml.core.time.teachers.ConstraintTeachersMaxGapsPerWeek;
import org.confetti.xml.core.time.teachers.ConstraintTeachersMaxHoursContinuously;
import org.confetti.xml.core.time.teachers.ConstraintTeachersMaxHoursDaily;
import org.confetti.xml.core.time.teachers.ConstraintTeachersMinDaysPerWeek;
import org.confetti.xml.core.time.teachers.ConstraintTeachersMinHoursDaily;

public interface ConstraintXmlVisitor<R, P> {
	R visitSpace(ConstraintActivitiesOccupyMaxDifferentRooms c, P p);
	R visitSpace(ConstraintActivitiesSameRoomIfConsecutive c, P p);
	R visitSpace(ConstraintActivityPreferredRoom c, P p);
	R visitSpace(ConstraintActivityPreferredRooms c, P p);
	R visitSpace(ConstraintActivityTagPreferredRoom c, P p);
	R visitSpace(ConstraintActivityTagPreferredRooms c, P p);
	R visitSpace(ConstraintBasicCompulsorySpace c, P p);
	R visitSpace(ConstraintRoomNotAvailableTimes c, P p);
	R visitSpace(ConstraintStudentsMaxBuildingChangesPerDay c, P p);
	R visitSpace(ConstraintStudentsMaxBuildingChangesPerWeek c, P p);
	R visitSpace(ConstraintStudentsMinGapsBetweenBuildingChanges c, P p);
	R visitSpace(ConstraintStudentsSetHomeRoom c, P p);
	R visitSpace(ConstraintStudentsSetHomeRooms c, P p);
	R visitSpace(ConstraintStudentsSetMaxBuildingChangesPerDay c, P p);
	R visitSpace(ConstraintStudentsSetMaxBuildingChangesPerWeek c, P p);
	R visitSpace(ConstraintStudentsSetMinGapsBetweenBuildingChanges c, P p);
	R visitSpace(ConstraintSubjectActivityTagPreferredRoom c, P p);
	R visitSpace(ConstraintSubjectActivityTagPreferredRooms c, P p);
	R visitSpace(ConstraintSubjectPreferredRoom c, P p);
	R visitSpace(ConstraintSubjectPreferredRooms c, P p);
	R visitSpace(ConstraintTeacherHomeRoom c, P p);
	R visitSpace(ConstraintTeacherHomeRooms c, P p);
	R visitSpace(ConstraintTeacherMaxBuildingChangesPerDay c, P p);
	R visitSpace(ConstraintTeacherMaxBuildingChangesPerWeek c, P p);
	R visitSpace(ConstraintTeacherMinGapsBetweenBuildingChanges c, P p);
	R visitSpace(ConstraintTeachersMaxBuildingChangesPerDay c, P p);
	R visitSpace(ConstraintTeachersMaxBuildingChangesPerWeek c, P p);
	R visitSpace(ConstraintTeachersMinGapsBetweenBuildingChanges c, P p);
	R visitTime(ConstraintActivitiesEndStudentsDay c, P p);
	R visitTime(ConstraintActivitiesMaxSimultaneousInSelectedTimeSlots c, P p);
	R visitTime(ConstraintActivitiesNotOverlapping c, P p);
	R visitTime(ConstraintActivitiesOccupyMaxTimeSlotsFromSelection c, P p);
	R visitTime(ConstraintActivitiesPreferredStartingTimes c, P p);
	R visitTime(ConstraintActivitiesPreferredTimeSlots c, P p);
	R visitTime(ConstraintActivitiesSameStartingDay c, P p);
	R visitTime(ConstraintActivitiesSameStartingHour c, P p);
	R visitTime(ConstraintActivitiesSameStartingTime c, P p);
	R visitTime(ConstraintActivityEndsStudentsDay c, P p);
	R visitTime(ConstraintActivityPreferredStartingTime c, P p);
	R visitTime(ConstraintActivityPreferredStartingTimes c, P p);
	R visitTime(ConstraintActivityPreferredTimeSlots c, P p);
	R visitTime(ConstraintBasicCompulsoryTime c, P p);
	R visitTime(ConstraintBreakTimes c, P p);
	R visitTime(ConstraintMaxDaysBetweenActivities c, P p);
	R visitTime(ConstraintMinDaysBetweenActivities c, P p);
	R visitTime(ConstraintMinGapsBetweenActivities c, P p);
	R visitTime(ConstraintStudentsActivityTagMaxHoursContinuously c, P p);
	R visitTime(ConstraintStudentsActivityTagMaxHoursDaily c, P p);
	R visitTime(ConstraintStudentsEarlyMaxBeginningsAtSecondHour c, P p);
	R visitTime(ConstraintStudentsIntervalMaxDaysPerWeek c, P p);
	R visitTime(ConstraintStudentsMaxGapsPerDay c, P p);
	R visitTime(ConstraintStudentsMaxGapsPerWeek c, P p);
	R visitTime(ConstraintStudentsMaxHoursContinuously c, P p);
	R visitTime(ConstraintStudentsMaxHoursDaily c, P p);
	R visitTime(ConstraintStudentsMinHoursDaily c, P p);
	R visitTime(ConstraintStudentsSetActivityTagMaxHoursContinuously c, P p);
	R visitTime(ConstraintStudentsSetActivityTagMaxHoursDaily c, P p);
	R visitTime(ConstraintStudentsSetEarlyMaxBeginningsAtSecondHour c, P p);
	R visitTime(ConstraintStudentsSetIntervalMaxDaysPerWeek c, P p);
	R visitTime(ConstraintStudentsSetMaxGapsPerDay c, P p);
	R visitTime(ConstraintStudentsSetMaxGapsPerWeek c, P p);
	R visitTime(ConstraintStudentsSetMaxHoursContinuously c, P p);
	R visitTime(ConstraintStudentsSetMaxHoursDaily c, P p);
	R visitTime(ConstraintStudentsSetMinHoursDaily c, P p);
	R visitTime(ConstraintStudentsSetNotAvailableTimes c, P p);
	R visitTime(ConstraintSubactivitiesPreferredStartingTimes c, P p);
	R visitTime(ConstraintSubactivitiesPreferredTimeSlots c, P p);
	R visitTime(ConstraintTeacherActivityTagMaxHoursContinuously c, P p);
	R visitTime(ConstraintTeacherActivityTagMaxHoursDaily c, P p);
	R visitTime(ConstraintTeacherIntervalMaxDaysPerWeek c, P p);
	R visitTime(ConstraintTeacherMaxDaysPerWeek c, P p);
	R visitTime(ConstraintTeacherMaxGapsPerDay c, P p);
	R visitTime(ConstraintTeacherMaxGapsPerWeek c, P p);
	R visitTime(ConstraintTeacherMaxHoursContinuously c, P p);
	R visitTime(ConstraintTeacherMaxHoursDaily c, P p);
	R visitTime(ConstraintTeacherMinDaysPerWeek c, P p);
	R visitTime(ConstraintTeacherMinHoursDaily c, P p);
	R visitTime(ConstraintTeacherNotAvailableTimes c, P p);
	R visitTime(ConstraintTeachersActivityTagMaxHoursContinuously c, P p);
	R visitTime(ConstraintTeachersActivityTagMaxHoursDaily c, P p);
	R visitTime(ConstraintTeachersIntervalMaxDaysPerWeek c, P p);
	R visitTime(ConstraintTeachersMaxDaysPerWeek c, P p);
	R visitTime(ConstraintTeachersMaxGapsPerDay c, P p);
	R visitTime(ConstraintTeachersMaxGapsPerWeek c, P p);
	R visitTime(ConstraintTeachersMaxHoursContinuously c, P p);
	R visitTime(ConstraintTeachersMaxHoursDaily c, P p);
	R visitTime(ConstraintTeachersMinDaysPerWeek c, P p);
	R visitTime(ConstraintTeachersMinHoursDaily c, P p);
	R visitTime(ConstraintThreeActivitiesGrouped c, P p);
	R visitTime(ConstraintTwoActivitiesConsecutive c, P p);
	R visitTime(ConstraintTwoActivitiesGrouped c, P p);
	R visitTime(ConstraintTwoActivitiesOrdered c, P p);

}
