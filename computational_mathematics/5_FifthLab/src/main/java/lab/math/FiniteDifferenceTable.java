package lab.math;

import lab.model.DataPoint;

import java.util.List;

public final class FiniteDifferenceTable {
    private final double[][] differences;

    public FiniteDifferenceTable(List<DataPoint> points) {
        int size = points.size();
        this.differences = new double[size][size];
        for (int row = 0; row < size; row++) {
            differences[row][0] = points.get(row).y();
        }
        for (int order = 1; order < size; order++) {
            for (int row = 0; row < size - order; row++) {
                differences[row][order] = differences[row + 1][order - 1] - differences[row][order - 1];
            }
        }
    }

    public double difference(int index, int order) {
        return differences[index][order];
    }
}
