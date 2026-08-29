package lab.math;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public record EquationDefinition(
        String title,
        double defaultX0,
        double defaultY0,
        double defaultXn,
        double defaultStep,
        double defaultEpsilon,
        DoubleBinaryOperator derivative,
        ExactSolutionFactory exactSolutionFactory
) {
    public DoubleUnaryOperator exactSolution(double x0, double y0) {
        return exactSolutionFactory.create(x0, y0);
    }

    @FunctionalInterface
    public interface ExactSolutionFactory {
        DoubleUnaryOperator create(double x0, double y0);
    }
}
