package de.niklaskorz.setlx.spark.types;

import de.niklaskorz.setlx.spark.utilities.JavaToSetlX;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.*;
import org.randoom.setlx.utilities.CodeFragment;
import org.randoom.setlx.utilities.SetlHashMap;
import org.randoom.setlx.utilities.State;
import spark.Request;

import java.lang.reflect.Method;

/**
 * Created by niklaskorz on 25.02.17.
 */
public class SparkRequest extends Value {
    public final Request request;

    public SparkRequest(Request request) {
        this.request = request;
    }

    @Override
    public Value clone() {
        return new SparkRequest(request);
    }

    @Override
    public void appendString(State state, StringBuilder sb, int tabs) {
        sb.append(request.toString());
    }

    @Override
    public int compareTo(CodeFragment other) {
        if (this.equalTo(other)) {
            return 0;
        }
        return (this.compareToOrdering() < other.compareToOrdering()) ? -1 : 1;
    }

    private final static long COMPARE_TO_ORDER_CONSTANT = generateCompareToOrderConstant(SparkRequest.class);

    @Override
    public long compareToOrdering() {
        return COMPARE_TO_ORDER_CONSTANT;
    }

    @Override
    public boolean equalTo(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        SparkRequest other = (SparkRequest) o;
        return this.request.equals(other.request);
    }

    @Override
    public int hashCode() {
        return request.hashCode();
    }

    @Override
    public SetlBoolean isObject() {
        return SetlBoolean.TRUE;
    }

    @Override
    public Value getObjectMemberUnCloned(State state, String variable) throws SetlException {
        // Custom attributes shadow predefined properties
        Object attr = request.attribute(variable);
        if (attr != null) {
            return JavaToSetlX.convert(state, attr);
        }

        try {
            Method m = Request.class.getMethod(variable);
            // TODO: Handle request.queryMap properly
            return JavaToSetlX.convert(state, m.invoke(request));
        } catch (Exception e) {
            return Om.OM;
        }
    }

    @Override
    public void setObjectMember(State state, String variable, Value value, String context) throws SetlException {
        if (value == Om.OM) {
            request.attribute(variable, null);
            return;
        }
        // Allow definition of custom attributes.
        // Useful for middleware, for example in the before-hook.
        request.attribute(variable, value);
    }
}
