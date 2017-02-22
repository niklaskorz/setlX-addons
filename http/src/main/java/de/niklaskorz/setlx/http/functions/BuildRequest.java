package de.niklaskorz.setlx.http.functions;

import de.niklaskorz.setlx.http.types.SetlRequest;
import okhttp3.Request;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.functions.PreDefinedProcedure;
import org.randoom.setlx.parameters.ParameterDefinition;
import org.randoom.setlx.types.SetlString;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;

import java.util.HashMap;

/**
 * Created by niklaskorz on 22.02.17.
 */
public class BuildRequest extends PreDefinedProcedure {
    private final static ParameterDefinition URL = createParameter("url");

    public BuildRequest() {
        super();
        addParameter(URL);
    }

    @Override
    protected Value execute(State state, HashMap<ParameterDefinition, Value> args) throws SetlException {
        SetlString url = (SetlString) args.get(URL);
        Request request = new Request.Builder()
                .url(url.getUnquotedString(state))
                .build();
        return new SetlRequest(request);
    }
}
