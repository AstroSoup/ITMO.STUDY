package lab.console;

import lab.chart.ChartExporter;
import lab.io.DataSetLoader;
import lab.math.DifferenceTableFormatter;
import lab.math.InterpolationService;
import lab.math.MethodComputation;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import lab.model.DataPoint;
import lab.model.DataSet;
import lab.model.FunctionType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

public final class ConsoleApp {
    private final Scanner scanner = new Scanner(System.in);
    private final DataSetLoader dataSetLoader = new DataSetLoader();
    private final InterpolationService interpolationService = new InterpolationService();
    private final ChartExporter chartExporter = new ChartExporter();

    public void run() {
        while (true) {
            System.out.println();
            System.out.println("1 - enter table manually");
            System.out.println("2 - load table from file");
            System.out.println("3 - generate table from function");
            System.out.println("0 - exit");
            int choice = readInt("Choose an option: ");
            try {
                if (choice == 0) {
                    return;
                }
                DataSet dataSet = switch (choice) {
                    case 1 -> readManualDataSet();
                    case 2 -> readFileDataSet();
                    case 3 -> readFunctionDataSet();
                    default -> throw new IllegalArgumentException("Unknown menu option");
                };

                double queryX = readDouble("Enter interpolation argument x: ");
                processDataSet(dataSet, queryX);
            } catch (RuntimeException | IOException | PythonExecutionException exception) {
                System.out.println("Error: " + exception.getMessage());
            }
        }
    }

    private void processDataSet(DataSet dataSet, double queryX) throws IOException, PythonExecutionException {
        System.out.println();
        System.out.println("Data set: " + dataSet.name());
        System.out.println(DifferenceTableFormatter.format(dataSet));

        List<MethodComputation> computations = interpolationService.computeAll(dataSet, queryX);
        for (MethodComputation computation : computations) {
            if (computation.value() == null) {
                System.out.println(computation.method() + ": unavailable - " + computation.note());
            } else {
                System.out.printf(Locale.US, "%s: %.10f", computation.method(), computation.value());
                if (!computation.note().isBlank()) {
                    System.out.print("  [" + computation.note() + "]");
                }
                System.out.println();
            }
        }

        Optional<Double> exactValue = dataSet.sourceFunction().map(functionType -> functionType.apply(queryX));
        exactValue.ifPresent(value -> System.out.printf(Locale.US, "Exact value: %.10f%n", value));
        
        Path chartPath = chartExporter.exportChart(dataSet, Path.of("build", "charts"));
        chartExporter.renderChart(chartPath);
        System.out.println("Chart saved to: " + chartPath.toAbsolutePath());
    }

    private DataSet readManualDataSet() {
        String name = readLine("Enter data set name: ");
        int count = readIntAtLeast("Enter number of points (at least 2): ", 2);
        List<DataPoint> points = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            double x = readDouble("x[" + i + "] = ");
            double y = readDouble("y[" + i + "] = ");
            points.add(new DataPoint(x, y));
        }
        return DataSet.table(name.isBlank() ? "Manual data set" : name, points);
    }

    private DataSet readFileDataSet() throws IOException {
        Path dataDir = Path.of("data");
        if (!Files.exists(dataDir)) {
            throw new IllegalStateException("data directory is missing");
        }

        List<Path> files;
        try (var stream = Files.list(dataDir)) {
            files = stream
                    .filter(path -> path.getFileName().toString().endsWith(".json"))
                    .sorted(Comparator.naturalOrder())
                    .toList();
        }

        if (files.isEmpty()) {
            throw new IllegalStateException("No JSON data sets found in data/");
        }

        System.out.println("Available files:");
        for (int i = 0; i < files.size(); i++) {
            System.out.println((i + 1) + " - " + files.get(i).getFileName());
        }
        int choice = readIntAtLeast("Choose a file: ", 1);
        if (choice > files.size()) {
            throw new IllegalArgumentException("File choice is out of range");
        }
        return dataSetLoader.loadJson(files.get(choice - 1));
    }

    private DataSet readFunctionDataSet() {
        FunctionType functionType = chooseFunctionType();
        double start = readDouble("Interval start: ");
        double end = readDouble("Interval end: ");
        if (end <= start) {
            throw new IllegalArgumentException("Interval end must be greater than interval start");
        }
        int count = readIntAtLeast("Number of points (at least 2): ", 2);
        return DataSet.generated(functionType.displayName() + " on [" + start + ", " + end + "]", functionType, start, end, count);
    }

    private FunctionType chooseFunctionType() {
        System.out.println("Available functions:");
        FunctionType[] values = FunctionType.values();
        for (int i = 0; i < values.length; i++) {
            System.out.println((i + 1) + " - " + values[i].displayName());
        }
        int choice = readIntAtLeast("Choose a function: ", 1);
        if (choice > values.length) {
            throw new IllegalArgumentException("Function choice is out of range");
        }
        return values[choice - 1];
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt).trim());
            } catch (NumberFormatException exception) {
                System.out.println("Invalid integer value");
            }
        }
    }

    private int readIntAtLeast(String prompt, int minimum) {
        while (true) {
            int value = readInt(prompt);
            if (value >= minimum) {
                return value;
            }
            System.out.println("Value must be at least " + minimum);
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(readLine(prompt).trim().replace(',', '.'));
            } catch (NumberFormatException exception) {
                System.out.println("Invalid number value");
            }
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
