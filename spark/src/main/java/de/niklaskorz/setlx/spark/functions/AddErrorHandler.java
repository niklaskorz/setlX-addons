package de.niklaskorz.setlx.spark.functions;

import de.niklaskorz.setlx.spark.types.ErrorType;
import de.niklaskorz.setlx.spark.utilities.SparkUtilities;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.functions.PreDefinedProcedure;
import org.randoom.setlx.parameters.ParameterDefinition;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.Procedure;
import org.randoom.setlx.types.SetlBoolean;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;
import spark.Route;
import spark.Spark;

import java.util.HashMap;

import static de.niklaskorz.setlx.spark.types.ErrorType.INTERNAL_SERVER_ERROR;
import static de.niklaskorz.setlx.spark.types.ErrorType.NOT_FOUND;

/**
 * Created by niklaskorz on 22.02.17.
 */
public class AddErrorHandler extends PreDefinedProcedure {
    private final static ParameterDefinition ARG = createParameter("callback");

    private final ErrorType type;

    public AddErrorHandler(ErrorType type) {
        super();
        addParameter(ARG);
        this.type = type;
    }

    @Override
    protected Value execute(State state, HashMap<ParameterDefinition, Value> args) throws SetlException {
        Value arg = args.get(ARG);

        switch (type) {
            case NOT_FOUND:
                if (arg.isProcedure() == SetlBoolean.TRUE) {
                    Route route = (request, response) -> SparkUtilities.handle(state, (Procedure) arg, request, response);
                    Spark.notFound(route);
                } else {
                    Spark.notFound(arg.getUnquotedString(state));
                }
                break;
            case INTERNAL_SERVER_ERROR:
                if (arg.isProcedure() == SetlBoolean.TRUE) {
                    Route route = (request, response) -> SparkUtilities.handle(state, (Procedure) arg, request, response);
                    Spark.internalServerError(route);
                } else {
                    Spark.internalServerError(arg.getUnquotedString(state));
                }
                break;
        }

        return Om.OM;
    }
}
