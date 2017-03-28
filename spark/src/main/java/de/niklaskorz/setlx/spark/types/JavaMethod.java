package de.niklaskorz.setlx.spark.types;

import de.niklaskorz.setlx.spark.utilities.JavaToSetlX;
import org.randoom.setlx.exceptions.JVMException;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.operatorUtilities.OperatorExpression;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.SetlBoolean;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.CodeFragment;
import org.randoom.setlx.utilities.FragmentList;
import org.randoom.setlx.utilities.State;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by niklaskorz on 28.03.17.
 */
public class JavaMethod extends Value {
    public final Object target;
    public final Method method;

    public JavaMethod(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    @Override
    public Value clone() {
        return new JavaMethod(target, method);
    }

    @Override
    public void appendString(State state, StringBuilder sb, int tabs) {
        sb.append(method.toString());
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
        return method.equals(o.method);
    }

    @Override
    public int hashCode() {
        return method.hashCode();
    }

    @Override
    public SetlBoolean isProcedure() {
        return SetlBoolean.TRUE;
    }

    @Override
    public Value call(State state, List<Value> argumentValues, FragmentList<OperatorExpression> arguments, Value listValue, OperatorExpression listArg) throws SetlException {
        try {
            return JavaToSetlX.convert(state, method.invoke(target));
        } catch (Exception e) {
            throw new JVMException(e.getLocalizedMessage(), e);
        }
    }
}
