package lab.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lab.math.EquationDefinition;
import lab.math.MethodResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class ResultJsonWriter {
    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public void writeReport(EquationDefinition equation,
                            double x0,
                            double y0,
                            double xn,
                            double h,
                            double epsilon,
                            List<MethodResult> results,
                            Path reportsDir) {
        try {
            Files.createDirectories(reportsDir);
            Path file = reportsDir.resolve("ode-report.json");
            objectMapper.writeValue(file.toFile(), new Report(equation.title(), x0, y0, xn, h, epsilon, results));
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to write JSON report", ex);
        }
    }

    public record Report(String equation,
                         double x0,
                         double y0,
                         double xn,
                         double h,
                         double epsilon,
                         List<MethodResult> results) {
    }
}
