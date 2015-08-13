package org.confetti.dataprovider.xml;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.confetti.core.Assignable;
import org.confetti.core.Assignment;
import org.confetti.core.Constraint;
import org.confetti.core.ConstraintAttributes;
import org.confetti.core.DataProvider;
import org.confetti.core.Day;
import org.confetti.core.Entity;
import org.confetti.core.EntityVisitor;
import org.confetti.core.Hour;
import org.confetti.core.Room;
import org.confetti.core.SolutionSlot;
import org.confetti.core.StudentGroup;
import org.confetti.core.Subject;
import org.confetti.core.Teacher;
import org.confetti.observable.ListMutator;
import org.confetti.observable.ObservableList;
import org.confetti.observable.ObservableValue;
import org.confetti.observable.ValueMutator;
import org.confetti.xml.FAOException;
import org.confetti.xml.InstituteFAO;
import org.confetti.xml.core.ActivityXml;
import org.confetti.xml.core.BaseConstraintXml;
import org.confetti.xml.core.DayXml;
import org.confetti.xml.core.GroupXml;
import org.confetti.xml.core.HourXml;
import org.confetti.xml.core.INameBean;
import org.confetti.xml.core.InstituteXml;
import org.confetti.xml.core.RoomXml;
import org.confetti.xml.core.SubgroupXml;
import org.confetti.xml.core.SubjectXml;
import org.confetti.xml.core.TeacherRef;
import org.confetti.xml.core.TeacherXml;
import org.confetti.xml.core.YearXml;
import org.confetti.xml.core.time.ConstraintBasicCompulsoryTime;
import org.confetti.xml.core.time.TimeConstraint;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

/**
 * @author Bubla Gabor
 */
public class XmlDataProvider implements DataProvider {
	
	//----------------------------- inner classes ----------------------------------------------------------------------
	private static class AssignmentImpl implements Assignment {

	    private final Long id;
		private final Subject subj;
		private final ListMutator<Teacher> teachers = new ListMutator<>();
		private final ListMutator<StudentGroup> stGroups = new ListMutator<>();
		
		public AssignmentImpl(Long id, Subject subj) {
            this.id = id;
            this.subj = subj;
			subj.addAssignment(this);
		}

		public void addTeacher(Teacher teacher) 			{ teachers.addItem(teacher); teacher.addAssignment(this);} 
		public void addStudentGroup(StudentGroup group) 	{ stGroups.addItem(group); group.addAssignment(this);} 
		
		public Long getId() { return id; }
		@Override public Subject getSubject() 								{ return subj; }
		@Override public ObservableList<Teacher> getTeachers() 				{ return teachers.getObservableList(); }
		@Override public ObservableList<StudentGroup> getStudentGroups() 	{ return stGroups.getObservableList(); }
		@Override public Room getRoom() 									{ return null; }

	}
	
	private static class ConstraintImpl implements Constraint {

		private final String type;
		private final ConstraintAttributes attrs;
		
		public ConstraintImpl(final String type, final ConstraintAttributes attrs) {
			this.type = type;
			this.attrs = attrs;
		}
		
		@Override public String getConstraintType() { return type; }
		@Override public ConstraintAttributes getAttributes() { return attrs; }
		
	}
	
	private static abstract class EntityImpl implements Entity, Assignable {

		private final ValueMutator<String> name;
		private final ListMutator<Assignment> assignments = new ListMutator<>();
		
		public EntityImpl(String name) {
			this.name = new ValueMutator<>(this, name);
		}
		
		@Override public ObservableValue<String> getName() 			  { return name.getObservableValue(); }
		@Override public void addAssignment(Assignment assignment) 	  { assignments.addItem(assignment);}
		@Override public void removeAssignment(Assignment assignment) { assignments.removeItem(assignment); }
		@Override public ObservableList<Assignment> getAssignments()  { return assignments.getObservableList(); }
		
		public ValueMutator<String> getNameMutator() { return name; }

	}
	
	private static class TeacherImpl extends EntityImpl implements Teacher {
		public TeacherImpl(String name) { super(name); }

		@Override
		public <R, P> R accept(EntityVisitor<R, P> visitor, P param) {
			return visitor.visitTeacher(this, param);
		}
	}

	private static class SubjectImpl extends EntityImpl implements Subject {
		public SubjectImpl(String name) { super(name); }

		@Override
		public <R, P> R accept(EntityVisitor<R, P> visitor, P param) {
			return visitor.visitSubject(this, param);
		}
	}

	private static class RoomImpl extends EntityImpl implements Room {
		public RoomImpl(String name) { super(name); }

		@Override
		public <R, P> R accept(EntityVisitor<R, P> visitor, P param) {
			return visitor.visitRoom(this, param);
		}
	}

	private static class StudentGroupImpl extends EntityImpl implements StudentGroup {
		
		private final ListMutator<StudentGroup> children = new ListMutator<>();
		
		public StudentGroupImpl(String name) {
			super(name);
		}

		public void addChild(StudentGroup child) 			{ children.addItem(child); }
		@Override public ObservableList<StudentGroup> getChildren() 	{ return children.getObservableList(); }
		@Override public StudentGroup getParent() 			{ return null; }

		@Override
		public <R, P> R accept(EntityVisitor<R, P> visitor, P param) {
			return visitor.visitStudentGroup(this, param);
		}
	}
	
	private static class DayImpl implements Day {

		private final ValueMutator<String> name;
		public DayImpl(String name) {
			this.name = new ValueMutator<>(this, name);
		}
		
		@Override public ObservableValue<String> getName() 			{ return name.getObservableValue(); }

	}

	private static class HourImpl implements Hour {

		private final ValueMutator<String> name;
		public HourImpl(String name) {
			this.name = new ValueMutator<>(this, name);
		}
		
		@Override public ObservableValue<String> getName() 			{ return name.getObservableValue(); }

	}

	//----------------------------- fields for UI client----------------------------------------------------------------
	private ValueMutator<String> instName = new ValueMutator<>();
	private ListMutator<Teacher> teachers = new ListMutator<>();
	private ListMutator<Subject> subjects = new ListMutator<>();
	private ListMutator<StudentGroup> stdGroups = new ListMutator<>();
	private ListMutator<Room> rooms = new ListMutator<>();
	private ListMutator<Day> days = new ListMutator<>();
	private ListMutator<Hour> hours = new ListMutator<>();
	private ListMutator<Assignment> assignments = new ListMutator<>();
	private ListMutator<Constraint> constraints = new ListMutator<>();
	private ValueMutator<Iterable<SolutionSlot>> solution = new ValueMutator<>();

	//----------------------------- fields for xml persistence ---------------------------------------------------------
    private final InstituteXml instXml;
    private File file;
    private long currentMaxId = 0;

	//----------------------------- constructors -----------------------------------------------------------------------
    public XmlDataProvider(InstituteXml inst, File file) throws FAOException {
        this(inst);
        this.file = file;
    }
    
    public XmlDataProvider(File file) throws FAOException {
		this(new InstituteFAO().importFrom(file));
        this.file = file;
	}
	
	public XmlDataProvider(InstituteXml inst) {
			this.instXml = inst;
            for (SubjectXml subj : inst.getSubjects()) {
				subjects.addItem(new SubjectImpl(subj.getName()));
			}
			for (TeacherXml teacher : inst.getTeachers()) {
				teachers.addItem(new TeacherImpl(teacher.getName()));
			}
			for (RoomXml room : inst.getRooms()) {
				rooms.addItem(new RoomImpl(room.getName()));
			}
			for (YearXml year : inst.getYears()) {
				StudentGroupImpl studentGroup1 = new StudentGroupImpl(year.getName());
				stdGroups.addItem(studentGroup1);
				for (GroupXml group : year.getGroups()) {
					StudentGroupImpl studentGroup2 = new StudentGroupImpl(group.getName());
					studentGroup1.addChild(studentGroup2);
					for (SubgroupXml subgroup : group.getSubgroups()) {
						StudentGroupImpl studentGroup3 = new StudentGroupImpl(subgroup.getName());
						studentGroup2.addChild(studentGroup3);
					}
				}
			}
			for (DayXml day : inst.getDays().getDays()) {
				days.addItem(new DayImpl(day.getName()));
			}
			for (HourXml hour : inst.getHours().getHours()) {
				hours.addItem(new HourImpl(hour.getName()));
			}

			Iterable<Subject> allSubjects = subjects.getObservableList().getList();
			Iterable<Teacher> allTeachers = teachers.getObservableList().getList();
			Map<String, StudentGroup> allStdGroups = collectStudentGroups(stdGroups.getObservableList().getList());
			for (ActivityXml act : inst.getActivities()) {
			    if (act.getId() > currentMaxId) {
                    currentMaxId = act.getId();
                }
				AssignmentImpl ass = new AssignmentImpl(act.getId(), findByName(allSubjects, act.getSubject().getName()));
				if (act.getStudents() != null) {
					for (String stGroupName : act.getStudents()) {
						ass.addStudentGroup(allStdGroups.get(stGroupName));
					}
				}
				if (act.getTeachers() != null) {
					for (TeacherRef teacherRef : act.getTeachers()) {
						ass.addTeacher(findByName(allTeachers, teacherRef.getName()));
					}
				}
				assignments.addItem(ass);
			}
	}
	
	private Map<String, StudentGroup> collectStudentGroups(Iterable<StudentGroup> list) {
		Map<String, StudentGroup> res = new HashMap<>();
		for (StudentGroup sg : list) {
			res.put(sg.getName().getValue(), sg);
			res.putAll(collectStudentGroups(sg.getChildren().getList()));
		}
		return res;
	}

	//----------------------------- DataProvider's API -----------------------------------------------------------------
	@Override public String getInformation()                               { return file.getAbsolutePath(); }
	@Override public ObservableValue<String> getName() 					   { return instName.getObservableValue(); }
	@Override public ObservableList<Subject> getSubjects() 				   { return subjects.getObservableList(); }
	@Override public ObservableList<Teacher> getTeachers() 				   { return teachers.getObservableList(); }
	@Override public ObservableList<StudentGroup> getStudentGroups() 	   { return stdGroups.getObservableList(); }
	@Override public ObservableList<Room> getRooms() 					   { return rooms.getObservableList(); }
	@Override public ObservableList<Day> getDays() 						   { return days.getObservableList(); }
	@Override public ObservableList<Hour> getHours() 				       { return hours.getObservableList(); }
	@Override public ObservableList<Assignment> getAssignments() 		   { return assignments.getObservableList(); }
	@Override public ObservableList<Constraint> getConstraints() 		   { return constraints.getObservableList(); }
	@Override public ObservableValue<Iterable<SolutionSlot>> getSolution() { return solution.getObservableValue(); }
	
	@Override
	public void addSubjects(List<String> names) {
	    for (String name : names) {
	        instXml.getSubjects().add(new SubjectXml(name));
        }
	    save();
	    
	    for (String name : names) {
	        SubjectImpl subjectImpl = new SubjectImpl(name);
	        subjects.addItem(subjectImpl);
        }
	}
	
    @Override
	public void addTeachers(List<String> names) {
        for (String name : names) {
            instXml.getTeachers().add(new TeacherXml(name));
        }
        save();
        
        for (String name : names) {
            TeacherImpl teacherImpl = new TeacherImpl(name);
            teachers.addItem(teacherImpl);
        }
	}
	
	@Override
	public void addStudentGroups(StudentGroup parent, List<String> names) {
	    if (parent == null) {
	        List<StudentGroupImpl> groups = Lists.transform(names, new Function<String, StudentGroupImpl>() {
                @Override public StudentGroupImpl apply(String name) { return  new StudentGroupImpl(name); }
	        });
            for (StudentGroupImpl group : groups) {
                instXml.getYears().add(new YearXml(group));
            }
	        save();
	        
	        for (StudentGroupImpl group : groups) {
                stdGroups.addItem(group);
            }
        } else { //TODO implement if has parent
        }
	}
	
	@Override
	public void addRooms(List<String> names) {
        for (String name : names) {
            instXml.getRooms().add(new RoomXml(name));
        }
        save();
        for (String name : names) {
    		RoomImpl roomImpl = new RoomImpl(name);
    		rooms.addItem(roomImpl);
        }
	}
	
	@Override
	public Assignment addAssignment(Subject subject, Iterable<Teacher> teachers, Iterable<StudentGroup> studentGroups) {
	    currentMaxId++;
	    instXml.getActivities().add(new ActivityXml(currentMaxId, subject, teachers, studentGroups));
	    save();
	    
	    AssignmentImpl assignment = new AssignmentImpl(currentMaxId, subject);
	    for (Teacher teacher : teachers) {
	        assignment.addTeacher(teacher);
	    }
	    for (StudentGroup studentGroup : studentGroups) {
	        assignment.addStudentGroup(studentGroup);
	    }
        assignments.addItem(assignment);
	    return assignment;
	}

	@Override
	public Constraint addConstraint(final String type, ConstraintAttributes attrs) {
		addXmlConstraint(type, attrs); 
		save();
		
		ConstraintImpl constraint = new ConstraintImpl(type, attrs);
		constraints.addItem(constraint);
		return constraint;
	}
	
	private BaseConstraintXml addXmlConstraint(final String type, final ConstraintAttributes attrs) {
		String shortType = type.substring("org.confetti.fet.constraints.".length());
		
		switch (shortType) {
			case "time.BasicCompulsoryTime": return addTimeXmlConstraint(new ConstraintBasicCompulsoryTime(attrs));
			default: return null;
		}
	}

	private TimeConstraint addTimeXmlConstraint(final TimeConstraint xmlTimeConstraint) {
		instXml.getTimeConstraints().add(xmlTimeConstraint);
		return xmlTimeConstraint;
	}

	@Override
	public void setSolution(Iterable<SolutionSlot> solution) {
	    this.solution.setValue(this, solution);
	}
	
    @Override public void removeSubjects(List<Subject> toRemove) { removeEntities(toRemove, subjects, instXml.getSubjects()); }
    @Override public void removeTeachers(List<Teacher> toRemove) { removeEntities(toRemove, teachers, instXml.getTeachers()); }
    @Override public void removeStudentGroups(List<StudentGroup> toRemove) { removeEntities(toRemove, stdGroups, instXml.getYears()); }
    @Override public void removeRooms(List<Room> toRemove) { removeEntities(toRemove, rooms, instXml.getRooms()); }
	
	@Override
	public void removeAssignment(Assignment assignment) {
	    ActivityXml foundActivity = findActivityById(((AssignmentImpl) assignment).getId());
	    instXml.getActivities().remove(foundActivity);
	    save();
	    
	    assignment.getSubject().removeAssignment(assignment);
        for (Teacher teacher : assignment.getTeachers().getList()) {
            teacher.removeAssignment(assignment);
        }
        for (StudentGroup studentGroup : assignment.getStudentGroups().getList()) {
            studentGroup.removeAssignment(assignment);
        }
        assignments.removeItem(assignment);
	}
	
	@Override
	public void rename(Entity entity, String newName) {
	    entity.accept(new RenameVisitor(instXml), newName);
	    save();
		((EntityImpl) entity).getNameMutator().setValue(entity, newName);
	}
	
	//----------------------------- helpers ----------------------------------------------------------------------------
	public void save() {
        try {
            new InstituteFAO().exportTo(instXml, file);
        } catch (FAOException e) {
            throw new RuntimeException(e);
        }
	 }
    
	private static <T extends Entity> T findByName(Iterable<T> items, String name) {
		for (T item : items) {
			if (item.getName().getValue().equals(name)) {
				return item;
			}
		}
		return null;
	}

	static <T extends INameBean> T findXmlByName(Iterable<T> items, String name) {
        for (T item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }
	
	private <ET extends Entity, XT extends INameBean> void removeEntities(List<ET> entitiesToRemove, ListMutator<ET> allEntities, List<XT> xmlEntities) {
        for (ET entityToRemove : entitiesToRemove) {
            XT foundXmlEntity = findXmlByName(xmlEntities, entityToRemove.getName().getValue());
            if (foundXmlEntity != null) {
                xmlEntities.remove(foundXmlEntity);
            }
        }
        save();
        
        for (ET entityToRemove : entitiesToRemove) {
            allEntities.removeItem(entityToRemove);
        }
    }
	
	private ActivityXml findActivityById(Long id) {
	    for (ActivityXml activity : instXml.getActivities()) {
	        if (activity.getId().equals(id)) {
	            return activity;
	        }
	    }
	    return null;
	}

}
