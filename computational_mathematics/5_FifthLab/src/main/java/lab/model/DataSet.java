package lab.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class DataSet {
    private final String name;
    private final List<DataPoint> points;
    private final FunctionType sourceFunction;

    private DataSet(String name, List<DataPoint> points, FunctionType sourceFunction) {
        this.name = Objects.requireNonNull(name, "name").isBlank() ? "Data set" : name;
        this.points = normalize(points);
        this.sourceFunction = sourceFunction;
        if (this.points.size() < 2) {
            throw new IllegalArgumentException("At least two points are required");
        }
    }

    public static DataSet table(String name, List<DataPoint> points) {
        return new DataSet(name, points, null);
    }

    public static DataSet generated(String name, java.util.function.DoubleUnaryOperator function, double start, double end, int count) {
        if (count < 2) {
            throw new IllegalArgumentException("Point count must be at least 2");
        }
        if (end <= start) {
            throw new IllegalArgumentException("Interval end must be greater than interval start");
        }

        double step = (end - start) / (count - 1);
        List<DataPoint> points = new ArrayList<>();
        for (int index = 0; index < count; index++) {
            double x = start + step * index;
            points.add(new DataPoint(x, function.applyAsDouble(x)));
        }
        return new DataSet(name, points, null);
    }

    public static DataSet generated(String name, FunctionType functionType, double start, double end, int count) {
        if (count < 2) {
            throw new IllegalArgumentException("Point count must be at least 2");
        }
        if (end <= start) {
            throw new IllegalArgumentException("Interval end must be greater than interval start");
        }

        double step = (end - start) / (count - 1);
        List<DataPoint> points = new ArrayList<>();
        for (int index = 0; index < count; index++) {
            double x = start + step * index;
            points.add(new DataPoint(x, functionType.apply(x)));
        }
        return new DataSet(name, points, functionType);
    }

    public String name() {
        return name;
    }

    public List<DataPoint> points() {
        return points;
    }

    public Optional<FunctionType> sourceFunction() {
        return Optional.ofNullable(sourceFunction);
    }

    private List<DataPoint> normalize(List<DataPoint> rawPoints) {
        if (rawPoints == null || rawPoints.isEmpty()) {
            throw new IllegalArgumentException("Points list must not be empty");
        }

        List<DataPoint> sorted = new ArrayList<>(rawPoints);
        sorted.sort(Comparator.comparingDouble(DataPoint::x));

        for (int index = 0; index < sorted.size(); index++) {
            DataPoint point = sorted.get(index);
            if (!Double.isFinite(point.x()) || !Double.isFinite(point.y())) {
                throw new IllegalArgumentException("Points must contain finite numbers");
            }
            if (index > 0 && Double.compare(point.x(), sorted.get(index - 1).x()) == 0) {
                throw new IllegalArgumentException("x values must be distinct");
            }
        }

        return List.copyOf(sorted);
    }
}
