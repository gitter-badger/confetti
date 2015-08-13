package org.confetti.dataprovider.xml;

import org.confetti.core.Constraint;
import org.confetti.core.ConstraintAttributes;

/**
 * @author K�r�ndi Tam�s
 */
class ConstraintImpl implements Constraint {

	private final String type;
	private final ConstraintAttributes attrs;
	
	public ConstraintImpl(final String type, final ConstraintAttributes attrs) {
		this.type = type;
		this.attrs = attrs;
	}
	
	@Override public String getConstraintType() { return type; }
	@Override public ConstraintAttributes getAttributes() { return attrs; }
	
}
