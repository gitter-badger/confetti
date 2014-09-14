package org.confetti.fet.engine;

import org.confetti.core.DataProvider;
import org.confetti.rcp.extensions.EngineWizardFactory;
import org.eclipse.jface.wizard.IWizard;

/**
 * @author Gabor Bubla
 */
public class FETEngineWizardFactory implements EngineWizardFactory {
	
	@Override
	public IWizard createWizard(DataProvider dp) {
		return new FETEngineWizard(dp);
	}

}
