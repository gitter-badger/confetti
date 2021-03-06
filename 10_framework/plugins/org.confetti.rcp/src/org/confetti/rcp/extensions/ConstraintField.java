package org.confetti.rcp.extensions;

import static org.confetti.rcp.views.AssignmentsView.toStr;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.confetti.core.Assignment;
import org.confetti.core.ConstraintAttributes;
import org.confetti.core.DataProvider;
import org.confetti.core.Day;
import org.confetti.core.Entity;
import org.confetti.core.Hour;
import org.confetti.core.Nameable;
import org.confetti.core.Room;
import org.confetti.core.StudentGroup;
import org.confetti.core.Subject;
import org.confetti.core.Teacher;
import org.confetti.observable.ObservableList;
import org.confetti.rcp.ConfettiPlugin;
import org.confetti.rcp.constraints.ConstraintFieldWeekModel;
import org.confetti.rcp.views.AssignmentsView;
import org.confetti.util.Tuple;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Spinner;

import com.google.common.collect.Iterables;

import de.kupzog.ktable.KTable;
import de.kupzog.ktable.KTableNoScrollModel;

/**
 * @author Gabor Bubla
 */
public class ConstraintField {
    
    private final static Map<String, FieldType> fieldTypeMapping = new HashMap<>();
    
    static {
        fieldTypeMapping.put("assignment-field", FieldType.Assignment);
        fieldTypeMapping.put("assignments-set-field", FieldType.AssignmentsSet);
        fieldTypeMapping.put("boolean-field", FieldType.Boolean);
        fieldTypeMapping.put("day-field", FieldType.Day);
        fieldTypeMapping.put("hour-field", FieldType.Hour);
        fieldTypeMapping.put("number-field", FieldType.Number);
        fieldTypeMapping.put("period-field", FieldType.Period);
        fieldTypeMapping.put("period-number-field", FieldType.PeriodNumber);
        fieldTypeMapping.put("teacher-field", FieldType.Teacher);
        fieldTypeMapping.put("studentgroup-field", FieldType.StudentGroup);
        fieldTypeMapping.put("week-field", FieldType.Week);
        fieldTypeMapping.put("room-field", FieldType.Room);
        fieldTypeMapping.put("rooms-set-field", FieldType.RoomsSet);
        fieldTypeMapping.put("subject-field", FieldType.Subject);
    }
    
    public enum FieldType {
        Boolean {
            @Override
            public Control createControl(Composite parent) {
                return new Button(parent, SWT.CHECK);
            }
            @Override
            public void putValue(String key, Control ctrl, ConstraintAttributes attrs) { 
            	attrs.withBoolean(key, ((Button) ctrl).isEnabled());
            }
        },
        Number {
            @Override
            public Control createControl(Composite parent) {
                return createSpinnerField(parent, 1, 100, 98);
            }
            @Override
            public void putValue(String key, Control ctrl, ConstraintAttributes attrs) { 
            	attrs.withDouble(key, ((Spinner) ctrl).getSelection());
            }
        },
        Day {
            @Override
            public Control createControl(Composite parent) {
                Iterable<Day> days = ConfettiPlugin.getDefault().getDataProvider().getValue().getDays().getList();
                int daysCount = Iterables.size(days);
                return createSpinnerField(parent, 1, daysCount, daysCount);
            }
            @Override
            public void putValue(String key, Control ctrl, ConstraintAttributes attrs) { 
            	attrs.withInteger(key, ((Spinner) ctrl).getSelection());
            }
        },
        Hour {
            @Override
            public Control createControl(Composite parent) {
                Iterable<Hour> hours = ConfettiPlugin.getDefault().getDataProvider().getValue().getHours().getList();
                int hoursCount = Iterables.size(hours);
                return createSpinnerField(parent, 1, hoursCount, hoursCount);
            }
            @Override
            public void putValue(String key, Control ctrl, ConstraintAttributes attrs) { 
            	attrs.withInteger(key, ((Spinner) ctrl).getSelection());
            }
        },
        Week {
            @Override
            public Control createControl(Composite parent) {
        		final KTable ktable = new KTable(parent, SWT.NONE);
        		ktable.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
        		
        		DataProvider dp = ConfettiPlugin.getDefault().getDataProvider().getValue();
				KTableNoScrollModel model = new ConstraintFieldWeekModel(ktable, dp);
    			ktable.setModel(model);
    			model.initialize();
            	return ktable;
            }
            @Override
            public void applyLayout(Control ctrl) {
                GridDataFactory.fillDefaults().grab(true, true).applyTo(ctrl);
            }
        },
        Period {
            @Override
            public Control createControl(Composite parent) {
            	Button button = new Button(parent, SWT.PUSH);
				button.setText("Period Field NOT IMPLEMENTED");
            	return button;
            }
        },
        PeriodNumber {
            @Override
            public Control createControl(Composite parent) {
            	Button button = new Button(parent, SWT.PUSH);
				button.setText("PeriodNumber Field NOT IMPLEMENTED");
            	return button;
            }
        },
        Teacher {
            @Override
            public Control createControl(Composite parent) {
            	DataProvider dp = ConfettiPlugin.getDefault().getDataProvider().getValue();
                return createComboField(parent, dp.getTeachers().getList());
            }
            @Override
            public void putValue(String key, Control ctrl, ConstraintAttributes attrs) { 
            	attrs.withTeacher(key, FieldType.<Teacher>getItemFromCombo(ctrl));
            }
        }, 
        StudentGroup {
            @Override
            public Control createControl(Composite parent) {
            	DataProvider dp = ConfettiPlugin.getDefault().getDataProvider().getValue();
            	List<Tuple<StudentGroup, Integer>> groups = flatHierarchy(0, dp.getStudentGroups());
                return createComboField(parent, groups, new LabelProvider(){
                    @Override
                    public String getText(Object element) {
                    	@SuppressWarnings("unchecked")
						Tuple<StudentGroup, Integer> tuple = (Tuple<StudentGroup, Integer>) element;
                        return indent(tuple.getSecond()) + tuple.getFirst().getName().getValue();
                    }
                });
            }
            @Override
            public void putValue(String key, Control ctrl, ConstraintAttributes attrs) {
            	Tuple<StudentGroup, Integer> selected = FieldType.<Tuple<StudentGroup, Integer>> getItemFromCombo(ctrl);
				attrs.withStudentGroup(key, selected.getFirst());
            }

            private String indent(int count) {
            	StringBuilder sb = new StringBuilder();
            	for (int i = 0; i < count; i++) {
					sb.append("  ");
				}
            	return sb.toString();
            }
            
			private List<Tuple<StudentGroup, Integer>> flatHierarchy(int depth, ObservableList<StudentGroup> studentGroups) {
				List<Tuple<StudentGroup, Integer>> res = new LinkedList<>();
				for (StudentGroup sg : studentGroups.getList()) {
					res.add(new Tuple<>(sg, depth));
					res.addAll(flatHierarchy(depth + 1, sg.getChildren()));
				}
				return res;
			}
        },
        Assignment {
            @Override
            public Control createControl(Composite parent) {
                DataProvider dp = ConfettiPlugin.getDefault().getDataProvider().getValue();
            	return createComboField(parent, dp.getAssignments().getList(), new LabelProvider(){
                    @Override
                    public String getText(Object element) {
                    	Assignment ass = (Assignment) element;
                        return String.format("[%-30s][%-30s][%-30s][%-20s]"
                        		, safeGetName(ass.getSubject())
                        		, toStr(ass.getStudentGroups().getList())
                        		, toStr(ass.getTeachers().getList())
                        		, safeGetName(ass.getRoom())
                        		);
                    }
                });
            }
            @Override
            public void putValue(String key, Control ctrl, ConstraintAttributes attrs) {
            	attrs.withAssignment(key, FieldType.<Assignment>getItemFromCombo(ctrl));
            }

            private String safeGetName(Entity ent) {
            	String name = AssignmentsView.getName(ent);
				return name == null ? "" : name;
            }
        },
        AssignmentsSet {
            @Override
            public Control createControl(Composite parent) {
            	Button button = new Button(parent, SWT.PUSH);
				button.setText("AssignmentSet Field NOT IMPLEMENTED");
            	return button;
            }
        }, 
        Room {
            @Override
            public Control createControl(Composite parent) {
            	DataProvider dp = ConfettiPlugin.getDefault().getDataProvider().getValue();
                return createComboField(parent, dp.getRooms().getList());
            }
            @Override
            public void putValue(String key, Control ctrl, ConstraintAttributes attrs) {
            	attrs.withRoom(key, FieldType.<Room>getItemFromCombo(ctrl));
            }
        }, 
        RoomsSet {
            @Override
            public Control createControl(Composite parent) {
            	Button button = new Button(parent, SWT.PUSH);
				button.setText("RoomSet Field NOT IMPLEMENTED");
            	return button;
            }
        }, 
        Subject {
            @Override
            public Control createControl(Composite parent) {
            	DataProvider dp = ConfettiPlugin.getDefault().getDataProvider().getValue();
                return createComboField(parent, dp.getSubjects().getList());
            }
            @Override
            public void putValue(String key, Control ctrl, ConstraintAttributes attrs) {
            	attrs.withSubject(key, FieldType.<Subject>getItemFromCombo(ctrl));
            }
        };

        public abstract Control createControl(Composite area);
        public void putValue(String key, Control ctrl, ConstraintAttributes attrs) {}
        
        public void applyLayout(Control ctrl) {
            GridDataFactory.fillDefaults().grab(true, false).applyTo(ctrl);
        }

        private static Control createSpinnerField(Composite parent, int min, int max, int cur) {
			Spinner spinner = new Spinner(parent, SWT.BORDER);
            spinner.setMinimum(min);
            spinner.setMaximum(max);
            spinner.setIncrement(1);
            spinner.setPageIncrement(1);
            spinner.setSelection(cur);
            return spinner;
		}

        private static <T> T getItemFromCombo(Control ctrl) {
			Combo combo = (Combo) ctrl;
        	int selectionIndex = combo.getSelectionIndex();
        	@SuppressWarnings("unchecked")
			Iterable<T> list = (Iterable<T>) combo.getData();
        	return (T) Iterables.get(list, selectionIndex);
		}
        
		private static Control createComboField(Composite parent, Iterable<? extends Nameable> items) {
			return createComboField(parent, items, new LabelProvider(){
                @Override
                public String getText(Object element) {
                	return ((Nameable) element).getName().getValue();
                }
            });
		}
		
		private static Control createComboField(Composite parent, Iterable<?> items, 
				LabelProvider labelProvider) {
			ComboViewer combo = new ComboViewer(parent, SWT.READ_ONLY);
            combo.setContentProvider(ArrayContentProvider.getInstance());
            combo.setLabelProvider(labelProvider);
			combo.setInput(items);
            combo.getControl().setData(items);
            return combo.getControl();
		}
    }
    
    private final String name;
    private final String label;
    private final FieldType type;
    
    public ConstraintField(IConfigurationElement element) {
        this.name = element.getAttribute("name");
        this.label = element.getAttribute("label");
        this.type = fieldTypeMapping.get(element.getName());
    }

    public String getName() { return name; }
    public String getLabel() { return label; }
    public FieldType getType() { return type; }

    public Control createControl(Composite area) {
        Control ctrl = getType().createControl(area);
        getType().applyLayout(ctrl);
        return ctrl;
    }
    
}
