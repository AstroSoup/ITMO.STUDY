package lab.chart;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonConfig;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import lab.math.ComputationPoint;
import lab.math.EquationDefinition;
import lab.math.MethodResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import java.awt.*;


public final class ChartService {
    private static final Logger log = LoggerFactory.getLogger(ChartService.class);

    public Path writeCombinedChart(EquationDefinition equation, List<MethodResult> results, Path chartsDir) throws IOException, PythonExecutionException {
        if (results.isEmpty()) {
            throw new IllegalArgumentException("The results must include at least 1 computational method result.");
        }
        try {
            Files.createDirectories(chartsDir);
            Path file = chartsDir.resolve(safeFileName(equation.title()) + ".png");

            Plot plot = Plot.create(PythonConfig.pythonBinPathConfig(".venv/bin/python"));
            List<ComputationPoint> referencePoints = results.get(0).points();
            plot.plot().add(extractXs(referencePoints), extractExact(referencePoints)).label("Exact solution");
            Double maxX = null;
            Double maxY = null;
            Double minX = null;
            Double minY = null;

            for (MethodResult result : results) {
                var xs = extractXs(result.points());
                var ys = extractYs(result.points());
                maxX = Math.max(xs.stream().max(Double::compare).orElseThrow(), maxX == null ? Double.MIN_VALUE : maxX);
                maxY = Math.max(ys.stream().max(Double::compare).orElseThrow(), maxY == null ? Double.MIN_VALUE : maxY);

                minX = Math.min(xs.stream().min(Double::compare).orElseThrow(), minX == null ? Double.MAX_VALUE : minX);
                minY = Math.min(ys.stream().min(Double::compare).orElseThrow(), minY == null ? Double.MAX_VALUE : minY);

                plot.plot().add(xs, ys).label(result.methodName());
            }

            plot.xlim(minX, maxX);
            plot.ylim(minY, maxY);
            plot.title(equation.title());
            plot.xlabel("x");
            plot.ylabel("y");
            plot.legend();
            plot.savefig(file.toString()).dpi(200);
            plot.executeSilently();
            return file;
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to save chart", ex);
        }
    }

    public void renderChart(Path chart) throws IOException {
        Desktop.getDesktop().open(chart.toFile());
    }

    private List<Double> extractXs(List<ComputationPoint> points) {
        List<Double> xs = new ArrayList<>(points.size());
        for (ComputationPoint point : points) {
            xs.add(point.x());
        }
        return xs;
    }

    private List<Double> extractYs(List<ComputationPoint> points) {
        List<Double> ys = new ArrayList<>(points.size());
        for (ComputationPoint point : points) {
            ys.add(point.y());
        }
        return ys;
    }

    private List<Double> extractExact(List<ComputationPoint> points) {
        List<Double> ys = new ArrayList<>(points.size());
        for (ComputationPoint point : points) {
            ys.add(point.exactValue());
        }
        return ys;
    }

    private String safeFileName(String title) {
        return title.toLowerCase().replaceAll("[^a-z0-9]+", "_").replaceAll("_+", "_").replaceAll("^_|_$", "");
    }
}
