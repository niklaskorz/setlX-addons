package de.niklaskorz.setlx.spark.types;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.*;
import org.randoom.setlx.utilities.CodeFragment;
import org.randoom.setlx.utilities.SetlHashMap;
import org.randoom.setlx.utilities.State;
import org.randoom.setlx.utilities.TermUtilities;

/**
 * Created by niklaskorz on 25.02.17.
 */
public class SetlModule extends Value {
    private final static String FUNCTIONAL_CHARACTER = TermUtilities.generateFunctionalCharacter(SetlModule.class);

    private SetlHashMap<Value> members;

    public SetlModule(final SetlHashMap<Value> members) {
        this.members = members;
    }

    @Override
    public SetlModule clone() {
        // Cloning modules doesn't make much sense.
        // Just return a reference to this module instead.
        return this;
    }

    @Override
    public void appendString(State state, StringBuilder sb, int tabs) {
        sb.append("module<{");
        members.appendString(state, sb, tabs);
        sb.append("}>");
    }

    @Override
    public SetlBoolean isObject() {
        return SetlBoolean.TRUE;
    }

    @Override
    public Value getObjectMemberUnCloned(State state, String variable) throws SetlException {
        Value result = members.get(variable);
        if (result == null) {
            return Om.OM;
        }
        return result;
    }

    @Override
    public void setObjectMember(State state, String variable, Value value, String context) throws SetlException {
        members.put(variable, value);
        if (state.traceAssignments) {
            state.printTrace(variable, value, FUNCTIONAL_CHARACTER);
        }
    }

    @Override
    public int compareTo(CodeFragment other) {
        if (this == other) {
            return 0;
        }
        return (this.compareToOrdering() < other.compareToOrdering()) ? -1 : 1;
    }

    private final static long COMPARE_TO_ORDER_CONSTANT = generateCompareToOrderConstant(SetlModule.class);

    @Override
    public long compareToOrdering() {
        return COMPARE_TO_ORDER_CONSTANT;
    }

    @Override
    public boolean equalTo(Object other) {
        return this == other;
    }

    @Override
    public int hashCode() {
        return members.hashCode();
    }
}
