package com.nwalsh.saxon.gcd;

/**
 * Saxon extension to compute great circle distance.
 *
 * <p>Copyright (C) 2011 Norman Walsh.</p>
 *
 * <p>This class provides a
 * <a href="http://saxon.sourceforge.net/">Saxon</a>
 * extension to return the great circle distance between two points on the earth.
 *
 * <p><b>Change Log:</b></p>
 * <dl>
 * <dt>2.0</dt>
 * <dd><p>Ported from old XSLT/Saxon 1.0 code.</p></dd>
 * </dl>
 *
 * @author Norman Walsh
 * <a href="mailto:ndw@nwalsh.com">ndw@nwalsh.com</a>
 */

import net.sf.saxon.expr.XPathContext;
import net.sf.saxon.lib.ExtensionFunctionCall;
import net.sf.saxon.lib.ExtensionFunctionDefinition;
import net.sf.saxon.om.Sequence;
import net.sf.saxon.om.StructuredQName;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.tree.iter.SingletonIterator;
import net.sf.saxon.value.DoubleValue;
import net.sf.saxon.value.SequenceType;

public class Distance extends ExtensionFunctionDefinition {
    private static final StructuredQName qName =
        new StructuredQName("", "http://nwalsh.com/xslt/ext/gcd", "distance");

    @Override
    public StructuredQName getFunctionQName() {
        return qName;
    }

    @Override
    public int getMinimumNumberOfArguments() {
        return 5;
    }

    @Override
    public int getMaximumNumberOfArguments() {
        return 5;
    }

    @Override
    public SequenceType[] getArgumentTypes() {
        // If it takes no arguments, what's this for?
        return new SequenceType[]{SequenceType.OPTIONAL_NUMERIC};
    }

    @Override
    public SequenceType getResultType(SequenceType[] suppliedArgumentTypes) {
        return SequenceType.SINGLE_DOUBLE;
    }

    public ExtensionFunctionCall makeCallExpression() {
        return new FuncCall();
    }

    private class FuncCall extends ExtensionFunctionCall {
        @Override
        public Sequence call(XPathContext xPathContext, Sequence[] sequences) throws XPathException {
            double lat1, long1, lat2, long2, radius;

            // My goodness this is hideously inefficient
            lat1   = Double.parseDouble(sequences[0].head().getStringValue());
            long1  = Double.parseDouble(sequences[1].head().getStringValue());
            lat2   = Double.parseDouble(sequences[2].head().getStringValue());
            long2  = Double.parseDouble(sequences[3].head().getStringValue());
            radius = Double.parseDouble(sequences[4].head().getStringValue());

            double dist = GCD.distance(lat1, long1, lat2, long2, radius);

            return new DoubleValue(dist);
        }
    }
}
