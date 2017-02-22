package de.niklaskorz.setlx.http.functions;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.randoom.setlx.exceptions.JVMIOException;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.functions.PreDefinedProcedure;
import org.randoom.setlx.parameters.ParameterDefinition;
import org.randoom.setlx.types.SetlString;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by niklaskorz on 22.02.17.
 */
public class Get extends PreDefinedProcedure {
    private final static ParameterDefinition URL = createParameter("url");

    OkHttpClient client = new OkHttpClient();

    public Get() {
        super();
        addParameter(URL);
    }

    @Override
    protected Value execute(State state, HashMap<ParameterDefinition, Value> args) throws SetlException {
        SetlString url = args.get(URL).str(state);

        Request request = new Request.Builder()
                .url(url.getUnquotedString(state))
                .build();

        try {
            Response response = client.newCall(request).execute();
            return new SetlString(response.body().string());
        } catch (IOException ex) {
            throw new JVMIOException(ex.getLocalizedMessage(), ex);
        }
    }
}
