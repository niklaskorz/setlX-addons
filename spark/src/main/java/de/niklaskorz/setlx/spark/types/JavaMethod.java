package de.niklaskorz.setlx.spark.types;

import de.niklaskorz.setlx.spark.utilities.JavaToSetlX;
import de.niklaskorz.setlx.spark.utilities.SetlXToJava;
import org.randoom.setlx.exceptions.JVMException;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.operatorUtilities.OperatorExpression;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.SetlBoolean;
import org.randoom.setlx.types.SetlError;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.CodeFragment;
import org.randoom.setlx.utilities.FragmentList;
import org.randoom.setlx.utilities.State;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Created by niklaskorz on 28.03.17.
 */
public class JavaMethod extends Value {
    public final Object target;
    public final String methodName;

    public JavaMethod(Object target, String methodName) {
        this.target = target;
        this.methodName = methodName;
    }

    @Override
    public Value clone() {
        return new JavaMethod(target, methodName);
    }

    @Override
    public void appendString(State state, StringBuilder sb, int tabs) {
        sb.append(String.format("JavaMethod(%s, \"%s\")", target.toString(), methodName));
    }

    @Override
    public int compareTo(CodeFragment other) {
        if (this.equalTo(other)) {
            return 0;
        }
        return (this.compareToOrdering() < other.compareToOrdering()) ? -1 : 1;
    }

    private final static long COMPARE_TO_ORDER_CONSTANT = generateCompareToOrderConstant(JavaMethod.class);

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
        JavaMethod o = (JavaMethod) other;
        return target.equals(o.target) && methodName.equals(o.methodName);
    }

    @Override
    public int hashCode() {
        return target.hashCode() + methodName.hashCode();
    }

    @Override
    public SetlBoolean isProcedure() {
        return SetlBoolean.TRUE;
    }

    private Method getMethod(Object params[]) {
        for (Method m : target.getClass().getMethods()) {
            if (!m.getName().equals(methodName)) {
                continue;
            }

            Class[] paramTypes = m.getParameterTypes();
            if (paramTypes.length != params.length) {
                continue;
            }

            boolean matchingTypes = true;
            for (int i = 0; i < paramTypes.length; i++) {
                if (!paramTypes[i].isAssignableFrom(params[i].getClass())) {
                    matchingTypes = false;
                    break;
                }
            }

            if (matchingTypes) {
                return m;
            }
        }
        return null;
    }

    @Override
    public Value call(State state, List<Value> argumentValues, FragmentList<OperatorExpression> arguments, Value listValue, OperatorExpression listArg) throws SetlException {
        try {
            Object params[] = new Object[argumentValues.size()];
            for (int i = 0; i < argumentValues.size(); i++) {
                params[i] = SetlXToJava.convert(state, argumentValues.get(i));
            }
            Method method = getMethod(params);
            if (method == null) {
                throw new Exception("Invalid parameter types");
            }
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            return JavaToSetlX.convert(state, method.invoke(target, params));
        } catch (Exception e) {
            throw new JVMException(e.getLocalizedMessage(), e);
        }
    }
}
