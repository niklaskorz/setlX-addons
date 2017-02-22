package org.randoom.setlx.functions;

import de.niklaskorz.setlx.http.utilities.JavaToSetlX;
import org.json.JSONObject;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.parameters.ParameterDefinition;
import org.randoom.setlx.types.SetlString;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;

import java.util.HashMap;

/**
 * Created by niklaskorz on 22.02.17.
 */
public class PD_json_parse extends PreDefinedProcedure {
    private final static ParameterDefinition SOURCE = createParameter("source");
    public final static PreDefinedProcedure DEFINITION = new PD_json_parse();

    private PD_json_parse() {
        super();
        addParameter(SOURCE);
    }


    @Override
    protected Value execute(State state, HashMap<ParameterDefinition, Value> args) throws SetlException {
        SetlString source = (SetlString) args.get(SOURCE);
        JSONObject json = new JSONObject(source.getUnquotedString(state));

        return JavaToSetlX.convertJSONObject(state, json);
    }
}
