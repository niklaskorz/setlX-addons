package de.niklaskorz.setlx.spark.types;

import de.niklaskorz.setlx.spark.utilities.JavaToSetlX;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.*;
import org.randoom.setlx.utilities.CodeFragment;
import org.randoom.setlx.utilities.SetlHashMap;
import org.randoom.setlx.utilities.State;
import spark.Request;

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
        if (request.attribute(variable) != null) {
            return JavaToSetlX.convert(state, request.attribute(variable));
        }

        switch (variable) {
            case "attributes":
                return JavaToSetlX.convertSet(state, request.attributes());
            case "body":
                return new SetlString(request.body());
            case "contentLength":
                return Rational.valueOf(request.contentLength());
            case "contentType":
                return new SetlString(request.contentType());
            case "contextPath":
                return new SetlString(request.contextPath());
            case "cookies":
                return JavaToSetlX.convertMap(state, request.cookies());
            case "headers":
                return JavaToSetlX.convertSet(state, request.headers());
            case "host":
                return new SetlString(request.host());
            case "ip":
                return new SetlString(request.ip());
            case "params":
                return JavaToSetlX.convertMap(state, request.params());
            case "pathInfo":
                return new SetlString(request.pathInfo());
            case "port":
                return Rational.valueOf(request.port());
            case "protocol":
                return new SetlString(request.protocol());
            case "queryMap":
                return JavaToSetlX.convertMap(state, request.queryMap().toMap());
            case "queryParams":
                return JavaToSetlX.convertSet(state, request.queryParams());
            case "requestMethod":
                return new SetlString(request.requestMethod());
            case "scheme":
                return new SetlString(request.scheme());
            case "servletPath":
                return new SetlString(request.servletPath());
            case "uri":
                return new SetlString(request.uri());
            case "url":
                return new SetlString(request.url());
            case "userAgent":
                return new SetlString(request.userAgent());
            default:
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
