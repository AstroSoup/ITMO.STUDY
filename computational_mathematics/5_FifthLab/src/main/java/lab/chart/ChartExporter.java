package lab.chart;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import com.github.sh0nk.matplotlib4j.PythonConfig;
import com.github.sh0nk.matplotlib4j.builder.SaveFigBuilder;

import lab.math.InterpolationService;
import lab.model.DataPoint;
import lab.model.DataSet;
import lab.model.FunctionType;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class ChartExporter {
    private final InterpolationService interpolationService = new InterpolationService();

    public Path exportChart(DataSet dataSet, Path outputDirectory) throws IOException, PythonExecutionException {
        Files.createDirectories(outputDirectory);
        List<DataPoint> points = dataSet.points();
        List<Double> nodeXValues = points.stream().map(DataPoint::x).toList();
        List<Double> nodeYValues = points.stream().map(DataPoint::y).toList();

        Plot plt = Plot.create(PythonConfig.pythonBinPathConfig(".venv/bin/python"));
        plt.figure(dataSet.name());

        if (dataSet.sourceFunction().isPresent()) {
            SampledCurve exactCurve = sampleExactFunction(dataSet.sourceFunction().get(), points);
            plt.plot().add(exactCurve.xValues(), exactCurve.yValues()).label("Exact").linestyle("-").linewidth(2.0);
        }

        SampledCurve lagrangeCurve = sampleLagrange(dataSet);
        plt.plot().add(lagrangeCurve.xValues(), lagrangeCurve.yValues()).label("Lagrange").linestyle("--").linewidth(2.0);

        if (interpolationService.canUseNewton(dataSet)) {
            SampledCurve newtonCurve = sampleNewton(dataSet);
            plt.plot().add(newtonCurve.xValues(), newtonCurve.yValues()).label("Newton").linestyle("-").linewidth(2.0);
        }

        if (interpolationService.canUseGauss(dataSet)) {
            SampledCurve gaussCurve = sampleGauss(dataSet);
            plt.plot().add(gaussCurve.xValues(), gaussCurve.yValues()).label("Gauss").linestyle("-").linewidth(2.0);
        }

        plt.plot().add(nodeXValues, nodeYValues, "o").label("Nodes").linestyle("none");

        double minX = points.stream().mapToDouble(DataPoint::x).min().orElseThrow();
        double maxX = points.stream().mapToDouble(DataPoint::x).max().orElseThrow();
        double minY = minOfSeries(dataSet);
        double maxY = maxOfSeries(dataSet);
        plt.xlim(minX, maxX);
        plt.ylim(minY, maxY);
        plt.xlabel("x");
        plt.ylabel("y");
        plt.title(dataSet.name());
        plt.legend().loc("upper right");

        Path chartFile = outputDirectory.resolve(safeFileName(dataSet.name()) + ".png");
        SaveFigBuilder saveFigBuilder = plt.savefig(chartFile.toAbsolutePath().toString()).dpi(200);
        saveFigBuilder.format("png");
        plt.executeSilently();
        return chartFile;
    }

    public void renderChart(Path chartFile) throws IOException {
        Desktop.getDesktop().open(chartFile.toFile());
    }

    private SampledCurve sampleExactFunction(FunctionType functionType, List<DataPoint> points) {
        return sample(points, functionType::apply);
    }

    private SampledCurve sampleLagrange(DataSet dataSet) {
        return sample(dataSet.points(), x -> interpolationService.interpolateLagrange(dataSet.points(), x));
    }

    private SampledCurve sampleNewton(DataSet dataSet) {
        return sample(dataSet.points(), x -> interpolationService.interpolateNewton(dataSet, x));
    }

    private SampledCurve sampleGauss(DataSet dataSet) {
        return sample(dataSet.points(), x -> interpolationService.interpolateGauss(dataSet, x));
    }

    private List<Double> pointValues(List<DataPoint> points) {
        return points.stream().map(DataPoint::y).toList();
    }

    private SampledCurve sample(List<DataPoint> points, java.util.function.DoubleUnaryOperator sampler) {
        double minX = points.stream().mapToDouble(DataPoint::x).min().orElseThrow();
        double maxX = points.stream().mapToDouble(DataPoint::x).max().orElseThrow();
        int samples = Math.max(200, points.size() * 40);
        double step = (maxX - minX) / (samples - 1);
        List<Double> xValues = new ArrayList<>(samples);
        List<Double> values = new ArrayList<>(samples);
        for (int i = 0; i < samples; i++) {
            double x = minX + step * i;
            xValues.add(x);
            values.add(sampler.applyAsDouble(x));
        }
        return new SampledCurve(xValues, values);
    }

    private double minOfSeries(DataSet dataSet) {
        double min = dataSet.points().stream().mapToDouble(DataPoint::y).min().orElseThrow();
        if (dataSet.sourceFunction().isPresent()) {
            min = Math.min(min, sampleExactFunction(dataSet.sourceFunction().get(), dataSet.points()).yValues().stream().mapToDouble(Double::doubleValue).min().orElse(min));
        }
        min = Math.min(min, sampleLagrange(dataSet).yValues().stream().mapToDouble(Double::doubleValue).min().orElse(min));
        if (interpolationService.canUseNewton(dataSet)) {
            min = Math.min(min, sampleNewton(dataSet).yValues().stream().mapToDouble(Double::doubleValue).min().orElse(min));
        }
        if (interpolationService.canUseGauss(dataSet)) {
            min = Math.min(min, sampleGauss(dataSet).yValues().stream().mapToDouble(Double::doubleValue).min().orElse(min));
        }
        return min;
    }

    private double maxOfSeries(DataSet dataSet) {
        double max = dataSet.points().stream().mapToDouble(DataPoint::y).max().orElseThrow();
        if (dataSet.sourceFunction().isPresent()) {
            max = Math.max(max, sampleExactFunction(dataSet.sourceFunction().get(), dataSet.points()).yValues().stream().mapToDouble(Double::doubleValue).max().orElse(max));
        }
        max = Math.max(max, sampleLagrange(dataSet).yValues().stream().mapToDouble(Double::doubleValue).max().orElse(max));
        if (interpolationService.canUseNewton(dataSet)) {
            max = Math.max(max, sampleNewton(dataSet).yValues().stream().mapToDouble(Double::doubleValue).max().orElse(max));
        }
        if (interpolationService.canUseGauss(dataSet)) {
            max = Math.max(max, sampleGauss(dataSet).yValues().stream().mapToDouble(Double::doubleValue).max().orElse(max));
        }
        return max;
    }

    private record SampledCurve(List<Double> xValues, List<Double> yValues) {
    }

    private String safeFileName(String name) {
        return name.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "_").replaceAll("^_|_$", "");
    }
}
