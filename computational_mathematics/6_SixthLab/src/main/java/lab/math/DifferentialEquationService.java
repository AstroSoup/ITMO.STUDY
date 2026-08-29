package lab.math;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class DifferentialEquationService {

    public MethodResult solveWithEnhancedEuler(EquationDefinition equation, double x0, double y0, double xn, double h, double epsilon) {
        validateGrid(x0, xn, h);
        double[] values = computeEnhancedEulerValues(equation, x0, y0, xn, h);

        List<ComputationPoint> points = buildPoints(equation, x0, y0, h, values);
        double[] fineValues = computeEnhancedEulerValues(equation, x0, y0, xn, h / 2.0);
        double rungeEstimate = 0.0;
        for (int i = 0; i < values.length; i++) {
            rungeEstimate = Math.max(rungeEstimate, rungeEstimate(values[i], fineValues[i * 2], 2));
        }
        return new MethodResult("Enhanced Euler method", 2, h, points, rungeEstimate, maxError(points));
    }

    public MethodResult solveWithRungeKutta(EquationDefinition equation, double x0, double y0, double xn, double h, double epsilon) {
        validateGrid(x0, xn, h);
        double[] values = computeRungeKuttaValues(equation, x0, y0, xn, h);

        List<ComputationPoint> points = buildPoints(equation, x0, y0, h, values);
        double[] fineValues = computeRungeKuttaValues(equation, x0, y0, xn, h / 2.0);
        double rungeEstimate = 0.0;
        for (int i = 0; i < values.length; i++) {
            rungeEstimate = Math.max(rungeEstimate, rungeEstimate(values[i], fineValues[i * 2], 4));
        }
        return new MethodResult("Runge-Kutta method", 4, h, points, rungeEstimate, maxError(points));
    }

    public MethodResult solveWithMilne(EquationDefinition equation, double x0, double y0, double xn, double h, double epsilon) {
        validateGrid(x0, xn, h);
        int steps = (int) Math.round((xn - x0) / h);
        if (steps < 3) {
            throw new IllegalArgumentException("Milne method requires at least three steps");
        }

        double[] values = new double[steps + 1];
        values[0] = y0;
        bootstrapWithRungeKutta(equation, x0, h, values, Math.min(3, steps));

        for (int i = 3; i < steps; i++) {
            if (!isValidValue(values[i])) {
                throw new IllegalArgumentException("Milne method diverged at step " + i);
            }
            double xNext = x0 + (i + 1) * h;
            double fIm2 = equation.derivative().applyAsDouble(x0 + (i - 2) * h, values[i - 2]);
            double fIm1 = equation.derivative().applyAsDouble(x0 + (i - 1) * h, values[i - 1]);
            double fI = equation.derivative().applyAsDouble(x0 + i * h, values[i]);

            if (!isValidValue(fIm2) || !isValidValue(fIm1) || !isValidValue(fI)) {
                throw new IllegalArgumentException("Milne method encountered invalid derivative value at step " + i);
            }

            double predictor = values[i - 3] + (4.0 * h / 3.0) * (2.0 * fIm2 - fIm1 + 2.0 * fI);
            double corrected = values[i - 1] + (h / 3.0) * (fIm1 + 4.0 * fI + equation.derivative().applyAsDouble(xNext, predictor));

            if (!isValidValue(predictor) || !isValidValue(corrected)) {
                throw new IllegalArgumentException("Milne method diverged at step " + i + " during prediction/correction");
            }

            double previous;
            int iterations = 0;
            do {
                previous = corrected;
                corrected = values[i - 1] + (h / 3.0) * (fIm1 + 4.0 * fI + equation.derivative().applyAsDouble(xNext, previous));
                iterations++;
            } while (Math.abs(corrected - previous) > epsilon && iterations < 20);

            values[i + 1] = corrected;
        }

        List<ComputationPoint> points = buildPoints(equation, x0, y0, h, values);
        return new MethodResult("Milne method", 4, h, points, null, maxError(points));
    }

    private double[] computeEnhancedEulerValues(EquationDefinition equation, double x0, double y0, double xn, double h) {
        int steps = (int) Math.round((xn - x0) / h);
        double[] values = new double[steps + 1];
        values[0] = y0;
        for (int i = 0; i < steps; i++) {
            double x = x0 + i * h;
            double y = values[i];
            if (!isValidValue(y)) {
                throw new IllegalArgumentException("Enhanced Euler method diverged at step " + i + ", x=" + x + ": y=" + y);
            }
            double k1 = h * equation.derivative().applyAsDouble(x, y);
            double k2 = h * equation.derivative().applyAsDouble(x + h, y + k1);
            if (!isValidValue(k1) || !isValidValue(k2)) {
                throw new IllegalArgumentException("Enhanced Euler method encountered invalid derivative value at step " + i);
            }
            values[i + 1] = y + 0.5 * (k1 + k2);
        }
        return values;
    }

    private double[] computeRungeKuttaValues(EquationDefinition equation, double x0, double y0, double xn, double h) {
        int steps = (int) Math.round((xn - x0) / h);
        double[] values = new double[steps + 1];
        values[0] = y0;
        for (int i = 0; i < steps; i++) {
            double x = x0 + i * h;
            double y = values[i];
            if (!isValidValue(y)) {
                throw new IllegalArgumentException("Runge-Kutta method diverged at step " + i + ", x=" + x + ": y=" + y);
            }
            double k1 = h * equation.derivative().applyAsDouble(x, y);
            double k2 = h * equation.derivative().applyAsDouble(x + h / 2.0, y + k1 / 2.0);
            double k3 = h * equation.derivative().applyAsDouble(x + h / 2.0, y + k2 / 2.0);
            double k4 = h * equation.derivative().applyAsDouble(x + h, y + k3);
            if (!isValidValue(k1) || !isValidValue(k2) || !isValidValue(k3) || !isValidValue(k4)) {
                throw new IllegalArgumentException("Runge-Kutta method encountered invalid derivative value at step " + i);
            }
            values[i + 1] = y + (k1 + 2.0 * k2 + 2.0 * k3 + k4) / 6.0;
        }
        return values;
    }

    private void bootstrapWithRungeKutta(EquationDefinition equation, double x0, double h, double[] values, int stepsToBootstrap) {
        double currentX = x0;
        for (int i = 0; i < stepsToBootstrap; i++) {
            double y = values[i];
            if (!isValidValue(y)) {
                throw new IllegalArgumentException("Bootstrap failed at step " + i + ": y=" + y);
            }
            double k1 = h * equation.derivative().applyAsDouble(currentX, y);
            double k2 = h * equation.derivative().applyAsDouble(currentX + h / 2.0, y + k1 / 2.0);
            double k3 = h * equation.derivative().applyAsDouble(currentX + h / 2.0, y + k2 / 2.0);
            double k4 = h * equation.derivative().applyAsDouble(currentX + h, y + k3);
            if (!isValidValue(k1) || !isValidValue(k2) || !isValidValue(k3) || !isValidValue(k4)) {
                throw new IllegalArgumentException("Bootstrap encountered invalid derivative value at step " + i);
            }
            values[i + 1] = y + (k1 + 2.0 * k2 + 2.0 * k3 + k4) / 6.0;
            currentX += h;
        }
    }

    private List<ComputationPoint> buildPoints(EquationDefinition equation,
                                               double x0,
                                               double y0,
                                               double h,
                                               double[] values) {
        DoubleUnaryOperator exact = equation.exactSolution(x0, y0);
        List<ComputationPoint> points = new ArrayList<>(values.length);
        for (int i = 0; i < values.length; i++) {
            double x = x0 + i * h;
            double y = values[i];
            double exactValue = exact.applyAsDouble(x);
            points.add(new ComputationPoint(x, y, exactValue, Math.abs(exactValue - y)));
        }
        return points;
    }

    private double maxError(List<ComputationPoint> points) {
        double max = 0.0;
        for (ComputationPoint point : points) {
            max = Math.max(max, point.absoluteError());
        }
        return max;
    }

    private void validateGrid(double x0, double xn, double h) {
        double steps = (xn - x0) / h;
        if (Math.abs(Math.rint(steps) - steps) > 1e-9) {
            throw new IllegalArgumentException("The interval length must be divisible by the step size");
        }
    }

    private double rungeEstimate(double coarseValue, double fineValue, int order) {
        return Math.abs(fineValue - coarseValue) / (Math.pow(2.0, order) - 1.0);
    }

    private boolean isValidValue(double value) {
        return !Double.isNaN(value) && !Double.isInfinite(value);
    }
}
