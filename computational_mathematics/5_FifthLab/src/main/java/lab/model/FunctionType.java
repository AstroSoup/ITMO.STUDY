package lab.model;

import java.util.Locale;
import java.util.function.DoubleUnaryOperator;

public enum FunctionType {
    SIN("sin(x)", Math::sin),
    COS("cos(x)", Math::cos),
    EXP("exp(x)", Math::exp);

    private final String displayName;
    private final DoubleUnaryOperator operator;

    FunctionType(String displayName, DoubleUnaryOperator operator) {
        this.displayName = displayName;
        this.operator = operator;
    }

    public String displayName() {
        return displayName;
    }

    public double apply(double x) {
        return operator.applyAsDouble(x);
    }

    @Override
    public String toString() {
        return displayName.toLowerCase(Locale.ROOT);
    }
}
