package org.confetti.rcp.constraints;

import java.util.HashMap;
import java.util.Map;

import org.confetti.core.ConstraintAttributes;
import org.confetti.rcp.extensions.ConstraintDescr;
import org.confetti.rcp.extensions.ConstraintField;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Gabor Bubla
 */
public class ConstraintDialog extends TrayDialog {

    private final ConstraintDescr descr;
	private final ConstraintAttributes attrs;
	private final Map<ConstraintField, Control> controls = new HashMap<>();

    public ConstraintDialog(final Shell parentShell, final ConstraintDescr descr, final ConstraintAttributes attrs) {
        super(parentShell);
        this.descr = descr;
		this.attrs = attrs;
        setHelpAvailable(true);	
    }
    
	@Override
	protected Point getInitialSize() {
		return new Point(800, 600);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(descr.getName());
	}
    
    @Override
    protected Control createDialogArea(Composite parent) {
    	Composite area = (Composite) super.createDialogArea(parent);
        area.setLayout(new GridLayout(2, false));
        for (ConstraintField field : descr.getFields()) {
            Label label = new Label(area, SWT.NONE);
            label.setText(field.getLabel());
            GridDataFactory.fillDefaults().align(SWT.LEFT, SWT.TOP).applyTo(label);
            Control ctrl = field.createControl(area);
            controls.put(field, ctrl);
        }
        return area;
    }

    @Override
	protected void okPressed() {
        for (Map.Entry<ConstraintField, Control> entry : controls.entrySet()) {
        	ConstraintField field = entry.getKey();
        	field.getType().putValue(field.getName(), entry.getValue(), attrs);
        }
        super.okPressed();
	}
    
    public void print(String path) {
		Control contents = getDialogArea();
    	final Image image = new Image(getShell().getDisplay(), contents.getBounds());
		GC gc = new GC(image);
		contents.print(gc);
		gc.dispose();

		ImageLoader loader = new ImageLoader();
		loader.data = new ImageData[] { image.getImageData() };
		loader.save(path, SWT.IMAGE_PNG);
    }
    
}
