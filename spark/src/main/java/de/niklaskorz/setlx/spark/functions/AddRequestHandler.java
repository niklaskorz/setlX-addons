package de.niklaskorz.setlx.spark.functions;

import de.niklaskorz.setlx.spark.types.RequestMethod;
import de.niklaskorz.setlx.spark.utilities.SparkUtilities;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.functions.PreDefinedProcedure;
import org.randoom.setlx.parameters.ParameterDefinition;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.Procedure;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;
import spark.Route;
import spark.Spark;

import java.util.HashMap;

/**
 * Created by niklaskorz on 22.02.17.
 */
public class AddRequestHandler extends PreDefinedProcedure {
    private final static ParameterDefinition PATH = createParameter("path");
    private final static ParameterDefinition CALLBACK = createParameter("callback");

    private final RequestMethod method;

    public AddRequestHandler(RequestMethod method) {
        super();
        addParameter(PATH);
        addParameter(CALLBACK);
        this.method = method;
    }

    @Override
    protected Value execute(State state, HashMap<ParameterDefinition, Value> args) throws SetlException {
        String path = args.get(PATH).getUnquotedString(state);
        Procedure callback = (Procedure) args.get(CALLBACK);
        Route route = (request, response) -> SparkUtilities.handle(state, callback, request, response);

        switch (method) {
            case GET:
                Spark.get(path, route);
                break;
            case POST:
                Spark.post(path, route);
                break;
            case PUT:
                Spark.put(path, route);
                break;
            case DELETE:
                Spark.delete(path, route);
                break;
            case OPTIONS:
                Spark.options(path, route);
                break;
            case HEAD:
                Spark.head(path, route);
                break;
            case PATCH:
                Spark.patch(path, route);
                break;
            case TRACE:
                Spark.trace(path, route);
                break;
        }

        return Om.OM;
    }
}
