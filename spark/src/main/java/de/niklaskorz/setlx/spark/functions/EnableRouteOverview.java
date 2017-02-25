package de.niklaskorz.setlx.spark.functions;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.functions.PreDefinedProcedure;
import org.randoom.setlx.parameters.ParameterDefinition;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.SetlString;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;
import spark.route.RouteOverview;

import java.util.HashMap;

/**
 * Created by niklaskorz on 25.02.17.
 */
public class EnableRouteOverview extends PreDefinedProcedure {
    private static final ParameterDefinition PATH = createOptionalParameter("path", Om.OM);

    public EnableRouteOverview() {
        super();
        addParameter(PATH);
    }

    @Override
    protected Value execute(State state, HashMap<ParameterDefinition, Value> args) throws SetlException {
        Value path = args.get(PATH);
        if (path != Om.OM) {
            RouteOverview.enableRouteOverview(path.getUnquotedString(state));
        } else {
            RouteOverview.enableRouteOverview();
        }
        return Om.OM;
    }
}
