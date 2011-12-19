package com.nwalsh.saxon.gcd;

/**
 * Saxon extension to compute direction between two points
 *
 * <p>Copyright (C) 2011 Norman Walsh.</p>
 *
 * <p>This class provides a
 * <a href="http://saxon.sourceforge.net/">Saxon</a>
 * extension to return the direction between two points on the earth.
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
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.om.StructuredQName;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.tree.iter.SingletonIterator;
import net.sf.saxon.value.StringValue;
import net.sf.saxon.value.SequenceType;
import com.nwalsh.saxon.gcd.GCD;

public class Bearing extends ExtensionFunctionDefinition {
    private static final StructuredQName qName =
        new StructuredQName("", "http://nwalsh.com/xslt/ext/gcd", "bearing");

    @Override
    public StructuredQName getFunctionQName() {
        return qName;
    }

    @Override
    public int getMinimumNumberOfArguments() {
        return 4;
    }

    @Override
    public int getMaximumNumberOfArguments() {
        return 4;
    }

    @Override
    public SequenceType[] getArgumentTypes() {
        // If it takes no arguments, what's this for?
        return new SequenceType[]{SequenceType.SINGLE_DOUBLE};
    }

    @Override
    public SequenceType getResultType(SequenceType[] suppliedArgumentTypes) {
        return SequenceType.SINGLE_STRING;
    }

    public ExtensionFunctionCall makeCallExpression() {
        return new FuncCall();
    }

    private class FuncCall extends ExtensionFunctionCall {
        public SequenceIterator call(SequenceIterator[] arguments, XPathContext context) throws XPathException {
            double lat1, long1, lat2, long2;

            // My goodness this is hideously inefficient
            lat1   = Double.parseDouble(arguments[0].next().getStringValue());
            long1  = Double.parseDouble(arguments[1].next().getStringValue());
            lat2   = Double.parseDouble(arguments[2].next().getStringValue());
            long2  = Double.parseDouble(arguments[3].next().getStringValue());

            String bearing = GCD.bearing(lat1, long1, lat2, long2);

            return SingletonIterator.makeIterator(new StringValue(bearing));
        }
    }
}
