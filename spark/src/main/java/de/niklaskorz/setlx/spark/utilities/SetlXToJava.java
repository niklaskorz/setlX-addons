package de.niklaskorz.setlx.spark.utilities;

import de.niklaskorz.setlx.spark.types.JavaObject;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.*;
import org.randoom.setlx.utilities.State;

import java.util.*;

/**
 * Created by niklaskorz on 22.02.17.
 */
public class SetlXToJava {
    public static List convertList(State state, SetlList val) throws SetlException {
        ArrayList<Object> list = new ArrayList<>(val.size());
        for (int i = 0; i < val.size(); i++) {
            list.set(i, convert(state, val.getMember(i + 1)));
        }
        return list;
    }

    public static Map<?, ?> convertMap(State state, SetlSet val) throws SetlException {
        HashMap<Object, Object> map = new HashMap<>(val.size());
        for (Value v : val) {
            SetlList pair = (SetlList) v;
            map.put(convert(state, pair.getMember(1)), convert(state, pair.getMember(2)));
        }
        return map;
    }

    public static Set convertSet(State state, SetlSet val) throws SetlException {
        HashSet<Object> set = new HashSet<>(val.size());
        for (Value v : val) {
            set.add(convert(state, v));
        }
        return set;
    }

    public static Object convert(State state, Value val) throws SetlException {
        if (val instanceof JavaObject) {
            return ((JavaObject) val).wrappedObject;
        }
        if (val.isList() == SetlBoolean.TRUE) {
            return convertList(state, (SetlList) val);
        }
        if (val.isMap() == SetlBoolean.TRUE) {
            return convertMap(state, (SetlSet) val);
        }
        if (val.isSet() == SetlBoolean.TRUE) {
            return convertSet(state, (SetlSet) val);
        }
        if (val.isBoolean() == SetlBoolean.TRUE) {
            return val == SetlBoolean.TRUE;
        }
        if (val.isDouble() == SetlBoolean.TRUE) {
            return val.toJDoubleValue(state);
        }
        if (val.isInteger() == SetlBoolean.TRUE) {
            return val.toJIntValue(state);
        }
        if (val.isString() == SetlBoolean.TRUE) {
            return val.getUnquotedString(state);
        }
        return val;
    }
}
