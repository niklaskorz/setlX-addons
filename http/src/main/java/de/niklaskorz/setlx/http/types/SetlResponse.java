package de.niklaskorz.setlx.http.types;

import okhttp3.Response;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.CodeFragment;
import org.randoom.setlx.utilities.State;

/**
 * Created by niklaskorz on 22.02.17.
 */
public class SetlResponse extends Value {
    public final Response response;

    public SetlResponse(Response response) {
        this.response = response;
    }

    @Override
    public Value clone() {
        return new SetlResponse(response);
    }

    @Override
    public void appendString(State state, StringBuilder sb, int tabs) {
        sb.append(response.toString());
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
        SetlResponse other = (SetlResponse) o;
        return this.response.equals(other.response);
    }

    @Override
    public int hashCode() {
        return response.hashCode();
    }
}
