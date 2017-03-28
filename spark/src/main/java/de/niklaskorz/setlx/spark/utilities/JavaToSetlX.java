package de.niklaskorz.setlx.spark.utilities;

import de.niklaskorz.setlx.spark.types.JavaObject;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.*;
import org.randoom.setlx.utilities.State;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by niklaskorz on 22.02.17.
 */
public class JavaToSetlX {
    public static Value convertList(State state, List<?> obj) throws SetlException {
        SetlList list = new SetlList(obj.size());
        for (int i = 0; i < obj.size(); i++) {
            list.setMember(state, i + 1, convert(state, obj.get(i)));
        }
        return list;
    }

    public static SetlSet convertMap(State state, Map<?, ?> obj) throws SetlException {
        SetlSet set = new SetlSet();
        for (Object key : obj.keySet()) {
            set.setMember(state, convert(state, key), convert(state, obj.get(key)));
        }
        return set;
    }

    public static SetlSet convertSet(State state, Set<?> obj) throws SetlException {
        SetlSet set = new SetlSet();
        for (Object o : obj) {
            set.addMember(state, convert(state, o));
        }
        return set;
    }

    public static Value convert(State state, Object obj) throws SetlException {
        if (obj instanceof Value) {
            return (Value) obj;
        }
        if (obj instanceof List) {
            return convertList(state, (List) obj);
        }
        if (obj instanceof Map) {
            return convertMap(state, (Map) obj);
        }
        if (obj instanceof Set) {
            return convertSet(state, (Set) obj);
        }
        if (obj instanceof Boolean) {
            return SetlBoolean.valueOf((Boolean) obj);
        }
        if (obj instanceof Double) {
            return SetlDouble.valueOf((Double) obj);
        }
        if (obj instanceof Long) {
            return Rational.valueOf((Long) obj);
        }
        if (obj instanceof Integer) {
            return Rational.valueOf((Integer) obj);
        }
        if (obj instanceof String) {
            return new SetlString((String) obj);
        }
        return new JavaObject(obj);
    }
}
