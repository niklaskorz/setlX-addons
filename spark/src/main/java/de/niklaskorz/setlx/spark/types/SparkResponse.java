package de.niklaskorz.setlx.spark.types;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.functions.PreDefinedProcedure;
import org.randoom.setlx.parameters.ParameterDefinition;
import org.randoom.setlx.types.*;
import org.randoom.setlx.utilities.CodeFragment;
import org.randoom.setlx.utilities.State;
import spark.Response;

import java.util.HashMap;

/**
 * Created by niklaskorz on 25.02.17.
 */
public class SparkResponse extends Value {
    public final Response response;

    private final PreDefinedProcedure setHeaderProcedure;
    private final PreDefinedProcedure redirectProcedure;

    public SparkResponse(Response response) {
        this.response = response;
        setHeaderProcedure = new SetHeader(response);
        redirectProcedure = new Redirect(response);
    }

    @Override
    public Value clone() {
        return new SparkResponse(response);
    }

    @Override
    public void appendString(State state, StringBuilder sb, int tabs) {
        sb.append(response.toString());
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
        return this.response.equals(other.request);
    }

    @Override
    public int hashCode() {
        return response.hashCode();
    }

    @Override
    public SetlBoolean isObject() {
        return SetlBoolean.TRUE;
    }

    @Override
    public Value getObjectMemberUnCloned(State state, String variable) throws SetlException {
        switch (variable) {
            case "body":
                return new SetlString(response.body());
            case "status":
                return Rational.valueOf(response.status());
            case "type":
                return new SetlString(response.type());
            case "header":
                return setHeaderProcedure;
            case "redirect":
                return redirectProcedure;
            default:
                return Om.OM;
        }
    }

    @Override
    public void setObjectMember(State state, String variable, Value value, String context) throws SetlException {
        switch (variable) {
            case "body":
                response.body(value.getUnquotedString(state));
                break;
            case "status":
                response.status(value.toJIntValue(state));
                break;
            case "type":
                response.type(value.getUnquotedString(state));
                break;
            default:
                break;
        }
    }

    private static class SetHeader extends PreDefinedProcedure {
        private static final ParameterDefinition NAME = createParameter("name");
        private static final ParameterDefinition VALUE = createParameter("value");

        private final Response response;

        public SetHeader(Response response) {
            super();
            addParameter(NAME);
            addParameter(VALUE);
            this.response = response;
        }

        @Override
        protected Value execute(State state, HashMap<ParameterDefinition, Value> args) throws SetlException {
            response.header(args.get(NAME).getUnquotedString(state), args.get(VALUE).getUnquotedString(state));
            return Om.OM;
        }
    }

    private static class Redirect extends PreDefinedProcedure {
        private static final ParameterDefinition LOCATION = createParameter("location");
        private static final ParameterDefinition STATUS = createOptionalParameter("status", Om.OM);

        private final Response response;

        public Redirect(Response response) {
            super();
            addParameter(LOCATION);
            addParameter(STATUS);
            this.response = response;
        }

        @Override
        protected Value execute(State state, HashMap<ParameterDefinition, Value> args) throws SetlException {
            Value location = args.get(LOCATION);
            Value status = args.get(STATUS);
            if (status != Om.OM) {
                response.redirect(location.getUnquotedString(state), status.toJIntValue(state));
            } else {
                response.redirect(location.getUnquotedString(state));
            }
            return Om.OM;
        }
    }
}
