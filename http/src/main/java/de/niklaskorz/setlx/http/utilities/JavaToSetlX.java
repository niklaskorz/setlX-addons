package de.niklaskorz.setlx.http.utilities;

import org.json.JSONArray;
import org.json.JSONObject;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.*;
import org.randoom.setlx.utilities.State;

/**
 * Created by niklaskorz on 22.02.17.
 */
public class JavaToSetlX {
    public static Value convertJSONArray(State state, JSONArray obj) throws SetlException {
        SetlList list = new SetlList(obj.length());
        for (int i = 0; i < obj.length(); i++) {
            list.setMember(state, i + 1, convert(state, obj.get(i)));
        }
        return list;
    }

    public static SetlSet convertJSONObject(State state, JSONObject obj) throws SetlException {
        SetlSet set = new SetlSet();
        for (String key : obj.keySet()) {
            set.setMember(state, new SetlString(key), convert(state, obj.get(key)));
        }
        return set;
    }

    public static Value convert(State state, Object obj) throws SetlException {
        if (obj instanceof JSONArray) {
            return convertJSONArray(state, (JSONArray) obj);
        }
        if (obj instanceof JSONObject) {
            return convertJSONObject(state, (JSONObject) obj);
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
        return Om.OM;
    }
}
