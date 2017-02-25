package de.niklaskorz.setlx.spark.types;

import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.CodeFragment;
import org.randoom.setlx.utilities.State;
import spark.Response;

/**
 * Created by niklaskorz on 25.02.17.
 */
public class SparkResponse extends Value {
    public final Response response;

    public SparkResponse(Response response) {
        this.response = response;
    }

    @Override
    public Value clone() {
        return new SparkResponse(response);
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
        SparkResponse other = (SparkResponse) o;
        return this.response.equals(other.response);
    }

    @Override
    public int hashCode() {
        return response.hashCode();
    }
}
