package de.niklaskorz.setlx.spark.types;

import de.niklaskorz.setlx.spark.utilities.JavaToSetlX;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.SetlBoolean;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.CodeFragment;
import org.randoom.setlx.utilities.State;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by niklaskorz on 28.03.17.
 */
public class JavaObject extends Value {
    public final Object wrappedObject;

    public JavaObject(Object object) {
        wrappedObject = object;
    }

    @Override
    public Value clone() {
        return new JavaObject(wrappedObject);
    }

    @Override
    public void appendString(State state, StringBuilder sb, int tabs) {
        sb.append(wrappedObject.toString());
    }

    @Override
    public int compareTo(CodeFragment other) {
        if (this.equalTo(other)) {
            return 0;
        }
        return (this.compareToOrdering() < other.compareToOrdering()) ? -1 : 1;
    }

    private final static long COMPARE_TO_ORDER_CONSTANT = generateCompareToOrderConstant(JavaObject.class);

    @Override
    public long compareToOrdering() {
        return COMPARE_TO_ORDER_CONSTANT;
    }

    @Override
    public boolean equalTo(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        JavaObject o = (JavaObject) other;
        return wrappedObject.equals(o.wrappedObject);
    }

    @Override
    public int hashCode() {
        return wrappedObject.hashCode();
    }

    @Override
    public SetlBoolean isObject() {
        return SetlBoolean.TRUE;
    }

    @Override
    public Value getObjectMemberUnCloned(State state, final String variable) throws SetlException {
        Class<?> c = wrappedObject.getClass();
        try {
            for (Method m : c.getMethods()) {
                if (m.getName().equals(variable)) {
                    return new JavaMethod(wrappedObject, variable);
                }
            }
        } catch (Exception e) {
            // Pass
        }
        try {
            Field f = c.getField(variable);
            return JavaToSetlX.convert(state, f.get(wrappedObject));
        } catch (Exception e) {
            // Pass
        }
        return Om.OM;
    }
}
