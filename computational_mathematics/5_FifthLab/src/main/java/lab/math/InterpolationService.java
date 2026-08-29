package lab.math;

import lab.model.DataPoint;
import lab.model.DataSet;

import java.util.ArrayList;
import java.util.List;

public final class InterpolationService {
    private static final double SPACING_TOLERANCE = 1e-9;

    public List<MethodComputation> computeAll(DataSet dataSet, double x) {
        List<MethodComputation> computations = new ArrayList<>();
        computations.add(new MethodComputation("Lagrange", interpolateLagrange(dataSet.points(), x), ""));

        if (canUseNewton(dataSet)) {
            boolean useForward = x <= midpoint(dataSet);
            computations.add(new MethodComputation(
                    useForward ? "Newton forward" : "Newton backward",
                    interpolateNewton(dataSet, x),
                    useForward ? "first formula" : "second formula"
            ));
        } else {
            computations.add(new MethodComputation("Newton", null, "nodes are not equally spaced"));
        }

        if (canUseGauss(dataSet)) {
            boolean useFirstFormula = x >= centralNode(dataSet).x();
            computations.add(new MethodComputation(
                    useFirstFormula ? "Gauss first" : "Gauss second",
                    interpolateGauss(dataSet, x),
                    useFirstFormula ? "x >= center" : "x < center"
            ));
        } else {
            computations.add(new MethodComputation("Gauss", null, "nodes are not equally spaced or there are fewer than 3 points"));
        }

        if (canUseStirling(dataSet)) {
            computations.add(new MethodComputation("Stirling", interpolateStirling(dataSet, x), "centered formula"));
        } else {
            computations.add(new MethodComputation("Stirling", null, "nodes are not equally spaced or there are fewer than 3 points"));
        }

        if (canUseBessel(dataSet)) {
            computations.add(new MethodComputation("Bessel", interpolateBessel(dataSet, x), "central pair formula"));
        } else {
            computations.add(new MethodComputation("Bessel", null, "nodes are not equally spaced or there are fewer than 4 points"));
        }

        return computations;
    }

    public double interpolateLagrange(List<DataPoint> points, double x) {
        double result = 0.0;
        for (int i = 0; i < points.size(); i++) {
            DataPoint current = points.get(i);
            double basis = 1.0;
            for (int j = 0; j < points.size(); j++) {
                if (i == j) {
                    continue;
                }
                basis *= (x - points.get(j).x()) / (current.x() - points.get(j).x());
            }
            result += current.y() * basis;
        }
        return result;
    }

    public double interpolateNewton(DataSet dataSet, double x) {
        List<DataPoint> points = dataSet.points();
        if (!canUseNewton(dataSet)) {
            throw new IllegalArgumentException("Newton interpolation requires equally spaced nodes");
        }

        FiniteDifferenceTable table = new FiniteDifferenceTable(points);
        double step = spacing(points);
        double origin = points.get(0).x();
        double target = points.get(points.size() - 1).x();
        boolean useForward = x <= midpoint(dataSet);

        if (useForward) {
            double t = (x - origin) / step;
            double result = points.get(0).y();
            double factor = 1.0;
            for (int order = 1; order < points.size(); order++) {
                factor *= t - (order - 1);
                result += factor * table.difference(0, order) / factorial(order);
            }
            return result;
        }

        double t = (x - target) / step;
        double result = points.get(points.size() - 1).y();
        double factor = 1.0;
        for (int order = 1; order < points.size(); order++) {
            factor *= t + (order - 1);
            result += factor * table.difference(points.size() - 1 - order, order) / factorial(order);
        }
        return result;
    }

    public double interpolateGauss(DataSet dataSet, double x) {
        DataSet selected = selectOddWindow(dataSet, x);
        List<DataPoint> points = selected.points();
        if (!canUseGauss(dataSet)) {
            throw new IllegalArgumentException("Gauss interpolation requires equally spaced nodes and at least 3 points");
        }

        FiniteDifferenceTable table = new FiniteDifferenceTable(points);
        double step = spacing(points);
        int centerIndex = points.size() / 2;
        double centerX = points.get(centerIndex).x();
        double t = (x - centerX) / step;
        boolean useFirstFormula = x >= centerX;

        double result = points.get(centerIndex).y();
        double factor = 1.0;
        int maxOrder = points.size() - 1;

        for (int order = 1; order <= maxOrder; order++) {
            factor *= useFirstFormula ? gaussFirstFactor(order, t) : gaussSecondFactor(order, t);
            int differenceIndex = useFirstFormula ? centerIndex - (order / 2) : centerIndex - ((order + 1) / 2);
            if (differenceIndex < 0 || differenceIndex + order >= points.size()) {
                break;
            }
            result += factor * table.difference(differenceIndex, order) / factorial(order);
        }
        return result;
    }

    public double interpolateStirling(DataSet dataSet, double x) {
        List<DataPoint> points = selectCenteredOddWindow(dataSet, x).points();
        if (!canUseStirling(dataSet)) {
            throw new IllegalArgumentException("Stirling interpolation requires equally spaced nodes and at least 3 points");
        }
        return interpolateStirling(points, x);
    }

    public double interpolateBessel(DataSet dataSet, double x) {
        List<DataPoint> points = selectCenteredEvenWindow(dataSet, x).points();
        if (!canUseBessel(dataSet)) {
            throw new IllegalArgumentException("Bessel interpolation requires equally spaced nodes and at least 4 points");
        }

        if (points.size() < 4) {
            throw new IllegalArgumentException("Bessel interpolation requires at least 4 points");
        }

        double left = interpolateStirling(points.subList(0, points.size() - 1), x);
        double right = interpolateStirling(points.subList(1, points.size()), x);
        return (left + right) / 2.0;
    }

    public boolean canUseNewton(DataSet dataSet) {
        return dataSet.points().size() >= 2 && isEquallySpaced(dataSet.points());
    }

    public boolean canUseGauss(DataSet dataSet) {
        return dataSet.points().size() >= 3 && isEquallySpaced(dataSet.points());
    }

    public boolean canUseStirling(DataSet dataSet) {
        return dataSet.points().size() >= 3 && isEquallySpaced(dataSet.points());
    }

    public boolean canUseBessel(DataSet dataSet) {
        return dataSet.points().size() >= 4 && isEquallySpaced(dataSet.points());
    }

    private boolean isEquallySpaced(List<DataPoint> points) {
        double step = spacing(points);
        for (int index = 2; index < points.size(); index++) {
            double currentStep = points.get(index).x() - points.get(index - 1).x();
            if (Math.abs(currentStep - step) > SPACING_TOLERANCE) {
                return false;
            }
        }
        return true;
    }

    private double spacing(List<DataPoint> points) {
        if (points.size() < 2) {
            throw new IllegalArgumentException("At least two points are required");
        }
        return points.get(1).x() - points.get(0).x();
    }

    private double midpoint(DataSet dataSet) {
        List<DataPoint> points = dataSet.points();
        return (points.get(0).x() + points.get(points.size() - 1).x()) / 2.0;
    }

    private DataPoint centralNode(DataSet dataSet) {
        List<DataPoint> points = selectOddWindow(dataSet, midpoint(dataSet)).points();
        return points.get(points.size() / 2);
    }

    private DataSet selectCenteredOddWindow(DataSet dataSet, double x) {
        List<DataPoint> points = dataSet.points();
        int windowSize = points.size() % 2 == 1 ? points.size() : points.size() - 1;
        if (windowSize < 3) {
            throw new IllegalArgumentException("Stirling interpolation requires at least 3 points");
        }
        return selectWindow(dataSet, x, windowSize);
    }

    private DataSet selectCenteredEvenWindow(DataSet dataSet, double x) {
        List<DataPoint> points = dataSet.points();
        int windowSize = points.size() % 2 == 0 ? points.size() : points.size() - 1;
        if (windowSize < 4) {
            throw new IllegalArgumentException("Bessel interpolation requires at least 4 points");
        }
        return selectWindow(dataSet, x, windowSize);
    }

    private DataSet selectOddWindow(DataSet dataSet, double x) {
        List<DataPoint> points = dataSet.points();
        if (points.size() % 2 == 1) {
            return dataSet;
        }

        int windowSize = points.size() - 1;
        if (windowSize < 3) {
            throw new IllegalArgumentException("Gauss interpolation requires at least 3 points");
        }

        return selectWindow(dataSet, x, windowSize);
    }

    private DataSet selectWindow(DataSet dataSet, double x, int windowSize) {
        List<DataPoint> points = dataSet.points();

        int closestIndex = 0;
        double closestDistance = Math.abs(points.get(0).x() - x);
        for (int index = 1; index < points.size(); index++) {
            double distance = Math.abs(points.get(index).x() - x);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestIndex = index;
            }
        }

        int start = Math.max(0, Math.min(closestIndex - windowSize / 2, points.size() - windowSize));
        return DataSet.table(dataSet.name() + " (window)", points.subList(start, start + windowSize));
    }

    private double interpolateStirling(List<DataPoint> points, double x) {
        FiniteDifferenceTable table = new FiniteDifferenceTable(points);
        double step = spacing(points);
        int centerIndex = points.size() / 2;
        double centerX = points.get(centerIndex).x();
        double t = (x - centerX) / step;

        double result = points.get(centerIndex).y();
        for (int order = 1; order < points.size(); order++) {
            if (order == 1) {
                result += t * averageDifference(table, centerIndex - 1, order) / factorial(order);
                continue;
            }

            if (order % 2 == 0) {
                double factor = stirlingEvenFactor(order, t);
                int differenceIndex = centerIndex - (order / 2);
                result += factor * table.difference(differenceIndex, order) / factorial(order);
            } else {
                double factor = stirlingOddFactor(order, t);
                int differenceIndex = centerIndex - ((order + 1) / 2);
                result += factor * averageDifference(table, differenceIndex, order) / factorial(order);
            }
        }

        return result;
    }

    private double interpolateBessel(List<DataPoint> points, double x) {
        FiniteDifferenceTable table = new FiniteDifferenceTable(points);
        double step = spacing(points);
        int leftCenterIndex = points.size() / 2 - 1;
        double centerX = points.get(leftCenterIndex).x();
        double t = (x - centerX) / step;

        double result = (points.get(leftCenterIndex).y() + points.get(leftCenterIndex + 1).y()) / 2.0;
        for (int order = 1; order < points.size(); order++) {
            if (order == 1) {
                result += (t - 0.5) * table.difference(leftCenterIndex, order) / factorial(order);
                continue;
            }

            if (order % 2 == 0) {
                double factor = besselEvenFactor(order, t);
                int differenceIndex = leftCenterIndex - (order / 2);
                result += factor * averageDifference(table, differenceIndex, order) / factorial(order);
            } else {
                double factor = besselOddFactor(order, t);
                int differenceIndex = leftCenterIndex - ((order - 1) / 2);
                result += factor * table.difference(differenceIndex, order) / factorial(order);
            }
        }

        return result;
    }

    private double averageDifference(FiniteDifferenceTable table, int index, int order) {
        return (table.difference(index, order) + table.difference(index + 1, order)) / 2.0;
    }

    private double stirlingOddFactor(int order, double t) {
        double factor = t;
        for (int i = 1; i <= (order - 1) / 2; i++) {
            factor *= t * t - i * i;
        }
        return factor;
    }

    private double stirlingEvenFactor(int order, double t) {
        double factor = t * t;
        for (int i = 1; i < order / 2; i++) {
            factor *= t * t - i * i;
        }
        return factor;
    }

    private double besselOddFactor(int order, double t) {
        double factor = t - 0.5;
        for (int i = 1; i <= (order - 1) / 2; i++) {
            factor *= (t + i - 1) * (t - i);
        }
        return factor;
    }

    private double besselEvenFactor(int order, double t) {
        double factor = t * (t - 1.0);
        for (int i = 1; i < order / 2; i++) {
            factor *= (t + i) * (t - i - 1.0);
        }
        return factor;
    }

    private double gaussFirstFactor(int order, double t) {
        if (order == 1) {
            return t;
        }
        return order % 2 == 0 ? t - (order / 2.0) : t + ((order - 1) / 2.0);
    }

    private double gaussSecondFactor(int order, double t) {
        if (order == 1) {
            return t;
        }
        return order % 2 == 0 ? t + (order / 2.0) : t - ((order - 1) / 2.0);
    }

    private double factorial(int value) {
        double result = 1.0;
        for (int index = 2; index <= value; index++) {
            result *= index;
        }
        return result;
    }
}
