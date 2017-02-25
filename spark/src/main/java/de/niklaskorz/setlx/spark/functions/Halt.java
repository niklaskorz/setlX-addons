package de.niklaskorz.setlx.spark.functions;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.functions.PreDefinedProcedure;
import org.randoom.setlx.parameters.ParameterDefinition;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.SetlBoolean;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;
import spark.Spark;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by niklaskorz on 22.02.17.
 */
public class Halt extends PreDefinedProcedure {
    private final static ParameterDefinition STATUS = createOptionalParameter("status", Om.OM);
    private final static ParameterDefinition BODY = createOptionalParameter("body", Om.OM);

    public Halt() {
        super();
        addParameter(STATUS);
        addParameter(BODY);
    }

    @Override
    protected Value execute(final State state, HashMap<ParameterDefinition, Value> args) throws SetlException {
        Value status = args.get(STATUS);
        Value body = args.get(BODY);
        if (body != Om.OM) {
            Spark.halt(status.toJIntValue(state), body.getUnquotedString(state));
        } else if (status != Om.OM) {
            if (status.isRational() == SetlBoolean.TRUE) {
                Spark.halt(status.toJIntValue(state));
            } else {
                Spark.halt(status.getUnquotedString(state));
            }
        } else {
            Spark.halt();
        }
        return Om.OM;
    }
}
