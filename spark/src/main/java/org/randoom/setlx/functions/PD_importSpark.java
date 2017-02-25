package org.randoom.setlx.functions;

import de.niklaskorz.setlx.spark.functions.*;
import de.niklaskorz.setlx.spark.types.ErrorType;
import de.niklaskorz.setlx.spark.types.HookType;
import de.niklaskorz.setlx.spark.types.RequestMethod;
import de.niklaskorz.setlx.spark.types.SetlModule;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.parameters.ParameterDefinition;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.SetlHashMap;
import org.randoom.setlx.utilities.State;

import java.util.HashMap;

/**
 * Created by niklaskorz on 25.02.17.
 */
public class PD_importSpark extends PreDefinedProcedure {
    public final static PreDefinedProcedure DEFINITION = new PD_importSpark();

    private SetlModule module;

    private PD_importSpark() {
        super();
    }

    @Override
    protected Value execute(State state, HashMap<ParameterDefinition, Value> args) throws SetlException {
        if (module != null) {
            return module;
        }

        SetlHashMap<Value> members = new SetlHashMap<>();
        members.put("wait", new Wait());
        members.put("enableRouteOverview", new EnableRouteOverview());
        members.put("halt", new Halt());

        // Request handlers
        members.put("get", new AddRequestHandler(RequestMethod.GET));
        members.put("post", new AddRequestHandler(RequestMethod.POST));
        members.put("put", new AddRequestHandler(RequestMethod.PUT));
        members.put("delete", new AddRequestHandler(RequestMethod.DELETE));
        members.put("options", new AddRequestHandler(RequestMethod.OPTIONS));
        members.put("head", new AddRequestHandler(RequestMethod.HEAD));
        members.put("patch", new AddRequestHandler(RequestMethod.PATCH));
        members.put("trace", new AddRequestHandler(RequestMethod.TRACE));

        // Error handlers
        members.put("notFound", new AddErrorHandler(ErrorType.NOT_FOUND));
        members.put("internalServerError", new AddErrorHandler(ErrorType.INTERNAL_SERVER_ERROR));

        // Hook handlers
        members.put("before", new AddHookHandler(HookType.BEFORE));
        members.put("after", new AddHookHandler(HookType.AFTER));
        members.put("afterAfter", new AddHookHandler(HookType.AFTER_AFTER));

        module = new SetlModule(members);
        return module;
    }
}
