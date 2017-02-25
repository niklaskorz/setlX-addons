package de.niklaskorz.setlx.spark.types;

import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.CodeFragment;
import org.randoom.setlx.utilities.State;
import spark.Request;

/**
 * Created by niklaskorz on 25.02.17.
 */
public class SparkRequest extends Value {
    public final Request request;

    public SparkRequest(Request request) {
        this.request = request;
    }

    @Override
    public Value clone() {
        return new SparkRequest(request);
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
        SparkRequest other = (SparkRequest) o;
        return this.request.equals(other.request);
    }

    @Override
    public int hashCode() {
        return request.hashCode();
    }
}
