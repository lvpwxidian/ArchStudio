package org.archstudio.prolog.op.iso;

import static com.google.common.base.Preconditions.checkArgument;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.archstudio.prolog.engine.ProofContext;
import org.archstudio.prolog.engine.UnificationEngine;
import org.archstudio.prolog.engine.Utils;
import org.archstudio.prolog.op.Operation;
import org.archstudio.prolog.term.ComplexTerm;
import org.archstudio.prolog.term.ConstantTerm;
import org.archstudio.prolog.term.Term;
import org.archstudio.prolog.term.VariableTerm;

public class IsFloat extends ComplexTerm implements Operation {

	public IsFloat(String name, List<? extends Term> terms) {
		super(name, terms);
		checkArgument(terms.size() == 1);
	}

	@Override
	public Iterable<Map<VariableTerm, Term>> execute(ProofContext proofContext, UnificationEngine unificationEngine,
			Term source, Map<VariableTerm, Term> variables) {
		Term t = getTerm(0);
		if (t instanceof VariableTerm) {
			t = variables.get(t);
		}
		if (t instanceof ConstantTerm && ((ConstantTerm) t).getValue() instanceof BigDecimal) {
			return Utils.asList(variables);
		}
		return Collections.emptyList();
	}
}