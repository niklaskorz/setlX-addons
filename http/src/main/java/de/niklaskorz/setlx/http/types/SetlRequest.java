package de.niklaskorz.setlx.http.types;

import okhttp3.Request;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.CodeFragment;
import org.randoom.setlx.utilities.State;

/**
 * Created by niklaskorz on 22.02.17.
 */
public class SetlRequest extends Value {
    public final Request request;

    public SetlRequest(Request request) {
        this.request = request;
    }

    @Override
    public Value clone() {
        return new SetlRequest(request);
    }

    @Override
    public void appendString(State state, StringBuilder sb, int tabs) {
        sb.append(request.toString());
    }

    @Override
    public int compareTo(CodeFragment other) {
        return 0;
    }

    @Override
    public long compareToOrdering() {
        return 0;
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
        SetlRequest other = (SetlRequest) o;
        return this.request.equals(other.request);
    }

    @Override
    public int hashCode() {
        return request.hashCode();
    }
}
