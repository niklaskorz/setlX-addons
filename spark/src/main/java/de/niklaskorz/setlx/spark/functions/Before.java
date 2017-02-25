package de.niklaskorz.setlx.spark.functions;

import de.niklaskorz.setlx.spark.utilities.SparkUtilities;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.functions.PreDefinedProcedure;
import org.randoom.setlx.parameters.ParameterDefinition;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.Procedure;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;
import spark.Filter;
import spark.Spark;

import java.util.HashMap;

/**
 * Created by niklaskorz on 22.02.17.
 */
public class Before extends PreDefinedProcedure {
    private final static ParameterDefinition CALLBACK = createParameter("callback");


    public Before() {
        super();
        addParameter(CALLBACK);
    }

    @Override
    protected Value execute(State state, HashMap<ParameterDefinition, Value> args) throws SetlException {
        Procedure callback = (Procedure) args.get(CALLBACK);
        Filter filter = (request, response) -> SparkUtilities.handle(state, callback, request, response);
        Spark.before(filter);
        return Om.OM;
    }
}
