package de.niklaskorz.setlx.http.functions;

import de.niklaskorz.setlx.http.types.SetlRequest;
import de.niklaskorz.setlx.http.types.SetlResponse;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.randoom.setlx.exceptions.JVMIOException;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.functions.PreDefinedProcedure;
import org.randoom.setlx.parameters.ParameterDefinition;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by niklaskorz on 22.02.17.
 */
public class ExecuteRequest extends PreDefinedProcedure {
    private final static ParameterDefinition REQUEST = createParameter("request");

    OkHttpClient client = new OkHttpClient();

    public ExecuteRequest() {
        super();
        addParameter(REQUEST);
    }


    @Override
    protected Value execute(State state, HashMap<ParameterDefinition, Value> args) throws SetlException {
        SetlRequest request = (SetlRequest) args.get(REQUEST);
        try {
            Response response = client.newCall(request.request).execute();
            return new SetlResponse(response);
        } catch (IOException ex) {
            throw new JVMIOException(ex.getLocalizedMessage(), ex);
        }
    }
}
