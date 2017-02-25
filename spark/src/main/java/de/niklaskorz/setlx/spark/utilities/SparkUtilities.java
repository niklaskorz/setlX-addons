package de.niklaskorz.setlx.spark.utilities;

import de.niklaskorz.setlx.spark.types.SparkRequest;
import de.niklaskorz.setlx.spark.types.SparkResponse;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.operatorUtilities.OperatorExpression;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.Procedure;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.FragmentList;
import org.randoom.setlx.utilities.State;
import spark.Request;
import spark.Response;

import java.util.Arrays;
import java.util.List;

/**
 * Created by niklaskorz on 25.02.17.
 */
public class SparkUtilities {
    public static String handle(State state, Procedure callback, Request request, Response response) throws SetlException {
        List<Value> argumentValues = Arrays.asList(new SparkRequest(request), new SparkResponse(response));
        FragmentList<OperatorExpression> arguments = new FragmentList<>();
        Value result = callback.call(state, argumentValues, arguments, null, null);
        if (result == Om.OM) {
            return null;
        }
        return result.getUnquotedString(state);
    }
}
