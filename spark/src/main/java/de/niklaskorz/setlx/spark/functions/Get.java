package de.niklaskorz.setlx.spark.functions;

import de.niklaskorz.setlx.spark.types.SparkRequest;
import de.niklaskorz.setlx.spark.types.SparkResponse;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.functions.PreDefinedProcedure;
import org.randoom.setlx.operatorUtilities.OperatorExpression;
import org.randoom.setlx.parameters.ParameterDefinition;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.Procedure;
import org.randoom.setlx.types.SetlString;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.FragmentList;
import org.randoom.setlx.utilities.State;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by niklaskorz on 22.02.17.
 */
public class Get extends PreDefinedProcedure {
    private final static ParameterDefinition PATH = createParameter("path");
    private final static ParameterDefinition CALLBACK = createParameter("callback");

    public Get() {
        super();
        addParameter(PATH);
        addParameter(CALLBACK);
    }

    @Override
    protected Value execute(final State state, HashMap<ParameterDefinition, Value> args) throws SetlException {
        SetlString path = (SetlString) args.get(PATH);
        final Procedure callback = (Procedure) args.get(CALLBACK);

        Spark.get(path.getUnquotedString(state), new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                List<Value> argumentValues = Arrays.asList(new SparkRequest(request), new SparkResponse(response));
                FragmentList<OperatorExpression> arguments = new FragmentList<>();
                Value result = callback.call(state, argumentValues, arguments, null, null);
                return result.getUnquotedString(state);
            }
        });

        return Om.OM;
    }
}
