package lab.math;

import java.util.List;

public record MethodResult(String methodName,
                           int order,
                           double step,
                           List<ComputationPoint> points,
                           Double rungeEstimate,
                           double maxAbsoluteError) {
}
