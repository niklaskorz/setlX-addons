package de.niklaskorz.setlx.spark.functions;

import de.niklaskorz.setlx.spark.types.SparkRequest;
import de.niklaskorz.setlx.spark.utilities.JavaToSetlX;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.functions.PreDefinedProcedure;
import org.randoom.setlx.parameters.ParameterDefinition;
import org.randoom.setlx.types.*;
import org.randoom.setlx.utilities.State;
import spark.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by niklaskorz on 22.02.17.
 */
public class RequestParams extends PreDefinedProcedure {
    private final static ParameterDefinition REQUEST = createParameter("response");
    private final static ParameterDefinition PARAM = createOptionalParameter("param", Om.OM);

    public RequestParams() {
        super();
        addParameter(REQUEST);
        addParameter(PARAM);
    }

    @Override
    protected Value execute(final State state, HashMap<ParameterDefinition, Value> args) throws SetlException {
        Request request = ((SparkRequest) args.get(REQUEST)).request;
        if (args.get(PARAM) != Om.OM) {
            String param = args.get(PARAM).getUnquotedString(state);
            return new SetlString(request.params(param));
        }

        Map<String, String> params = request.params();
        return JavaToSetlX.convertMap(state, params);
    }
}
