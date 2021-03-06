package org.confetti.rcp.commands;

import java.util.List;

import org.confetti.core.ConstraintAttributes;
import org.confetti.core.DataProvider;
import org.confetti.rcp.ConfettiPlugin;
import org.confetti.rcp.constraints.ConstraintDialog;
import org.confetti.rcp.extensions.ConstraintDescr;
import org.confetti.rcp.extensions.ConstraintRegistry;
import org.confetti.rcp.extensions.IConstraintElement;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

/**
 * @author Gabor Bubla
 */
public class NewConstraintCommand extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        List<IConstraintElement> constraintsDescr = ConstraintRegistry.INSTANCE.getExtensions();
        Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        LabelProvider labelProvider = new LabelProvider() {
        	@Override public String getText(Object element) { return ((IConstraintElement) element).getName(); }
        };
        
        ElementTreeSelectionDialog dlg = new ElementTreeSelectionDialog(shell, labelProvider, 
        		ConstraintElementContentProvider.INSTANCE);
        dlg.setTitle("Open");
        dlg.setMessage("Choose a constraint");
        dlg.setValidator(new ISelectionStatusValidator() {
			@Override
			public IStatus validate(Object[] selection) {
				boolean isValid = selection.length > 0 && selection[0] instanceof ConstraintDescr;
				return isValid ? Status.OK_STATUS : Status.CANCEL_STATUS;
			}
		});
        dlg.setInput(constraintsDescr); 
        if (Window.OK != dlg.open()) {
            return null;
        }
//        traverse(shell, "c:\\12_work\\30_confetti\\confetti_dialogs\\", constraintsDescr.toArray(new IConstraintElement[constraintsDescr.size()]));
//        MessageDialog.openInformation(shell, "Generate screensots", "Capturing screenshots done.");
        Object[] selected = dlg.getResult();
        if (selected == null || selected.length == 0 || !(selected[0] instanceof ConstraintDescr)) {
            return null;
        }
        ConstraintDescr selectedDescr = (ConstraintDescr) selected[0];
        ConstraintAttributes attrs = new ConstraintAttributes();
        ConstraintDialog constraintDialog = new ConstraintDialog(shell, selectedDescr, attrs);
        if (Window.OK != constraintDialog.open()) {
        	return null;
        }
		final DataProvider dp = ConfettiPlugin.getDefault().getDataProvider().getValue();
		dp.addConstraint(selectedDescr.getId(), attrs);
        return null;
    }

	private void traverse(Shell shell, String path, IConstraintElement[] constrElems) {
		int i = 1;
		for (IConstraintElement constrElem : constrElems) {
			if (constrElem instanceof ConstraintDescr) {
				ConstraintDescr constraintDescr = (ConstraintDescr) constrElem;
		        ConstraintDialog constraintDialog = new ConstraintDialog(shell, constraintDescr, null);
		        constraintDialog.create();
		        constraintDialog.print(path + String.format("%02d", i++) + "_" + constrElem.getName() + ".png");
			}
			
			if (constrElem.hasChildren()) {
				traverse(shell, path + String.format("%02d", i++) + "_" + constrElem.getName() + "_", constrElem.getChildren());
			}
		}
	}

	@Override public boolean isEnabled() { return ConfettiPlugin.getDefault().getDataProvider().getValue() == null ? false : true; }

    private static enum ConstraintElementContentProvider implements IStructuredContentProvider, ITreeContentProvider {
		INSTANCE;

		@Override public void dispose() { } 
		@Override public void inputChanged(Viewer viewer, Object oldInput, Object newInput) { }

		@Override public Object getParent(Object e) 		{ return null; }
		@Override public Object[] getChildren(Object e) { return ((IConstraintElement) e).getChildren(); }
		@Override public boolean hasChildren(Object e) { return ((IConstraintElement) e).hasChildren(); }
		@Override
		public Object[] getElements(Object e) {
			List<IConstraintElement> list = (List<IConstraintElement>) e;
			return list.toArray(new IConstraintElement[list.size()]);
		}
    }
}
