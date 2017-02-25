package de.niklaskorz.setlx.spark.functions;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.functions.PreDefinedProcedure;
import org.randoom.setlx.parameters.ParameterDefinition;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by niklaskorz on 22.02.17.
 */
public class Wait extends PreDefinedProcedure {
    public Wait() {
        super();
    }

    @Override
    protected Value execute(final State state, HashMap<ParameterDefinition, Value> args) throws SetlException {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        try {
            for (Thread thread : threadSet) {
                thread.join();
            }
        } catch (InterruptedException ex) {
            // Pass
        }

        return Om.OM;
    }
}
