package lab.math;

import lab.model.DataPoint;
import lab.model.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class DifferenceTableFormatter {
    private DifferenceTableFormatter() {
    }

    public static String format(DataSet dataSet) {
        List<DataPoint> points = dataSet.points();
        int size = points.size();
        List<List<Double>> table = buildDifferences(points);

        StringBuilder builder = new StringBuilder();
        builder.append("Finite differences table\n");
        builder.append(String.format(Locale.US, "%12s %14s", "x", "y"));
        for (int order = 1; order < size; order++) {
            builder.append(String.format(Locale.US, " %14s", "Δ^" + order + "y"));
        }
        builder.append('\n');

        for (int row = 0; row < size; row++) {
            builder.append(String.format(Locale.US, "%12.6f %14.6f", points.get(row).x(), points.get(row).y()));
            for (int order = 1; order < size - row; order++) {
                builder.append(String.format(Locale.US, " %14.6f", table.get(row).get(order - 1)));
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    public static List<List<Double>> buildDifferences(List<DataPoint> points) {
        int size = points.size();
        List<List<Double>> table = new ArrayList<>();
        double[] current = points.stream().mapToDouble(DataPoint::y).toArray();
        for (int order = 1; order < size; order++) {
            List<Double> row = new ArrayList<>();
            double[] next = new double[size - order];
            for (int index = 0; index < next.length; index++) {
                next[index] = current[index + 1] - current[index];
                row.add(next[index]);
            }
            table.add(row);
            current = next;
        }
        return table;
    }
}
