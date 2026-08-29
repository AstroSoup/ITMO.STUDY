package lab.console;

import lab.math.ComputationPoint;
import lab.math.EquationCatalog;
import lab.math.EquationDefinition;
import lab.math.MethodResult;
import lab.math.DifferentialEquationService;
import lab.chart.ChartService;

import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class ConsoleApp {

    private final Scanner scanner = new Scanner(System.in);
    private final ChartService chartService = new ChartService();
    private final DifferentialEquationService service = new DifferentialEquationService();

    public void run() {

        EquationDefinition equation = chooseEquation();
        double x0 = readDouble("Enter x0", equation.defaultX0());
        double y0 = readDouble("Enter y0", equation.defaultY0());
        double xn = readDouble("Enter xn", equation.defaultXn());
        double h = readDouble("Enter step h", equation.defaultStep());
        double epsilon = readDouble("Enter epsilon", equation.defaultEpsilon());

        try {
            validateInputs(x0, xn, h, epsilon);
        } catch (IllegalArgumentException e) {
            System.out.println("One of the inputs is not valid: " + e.getMessage());
            return;
        }


        List<MethodResult> results = new ArrayList<>();
        try {
            results.add(service.solveWithEnhancedEuler(equation, x0, y0, xn, h, epsilon));
        } catch (IllegalArgumentException e) {
            System.out.println();
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("Please try different input parameters. The step size might be too large");
            System.out.println("or the interval might be too wide for this equation.");
        }
        try {
            results.add(service.solveWithRungeKutta(equation, x0, y0, xn, h, epsilon));
        } catch (IllegalArgumentException e) {
            System.out.println();
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("Please try different input parameters. The step size might be too large");
            System.out.println("or the interval might be too wide for this equation.");
        }
        try {
            results.add(service.solveWithMilne(equation, x0, y0, xn, h, epsilon));
        } catch (IllegalArgumentException e) {
            System.out.println();
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("Please try different input parameters. The step size might be too large");
            System.out.println("or the interval might be too wide for this equation.");
        }

        System.out.println();
        System.out.println("Equation: " + equation.title());
        System.out.printf("Initial conditions: y(%.6f) = %.6f%n", x0, y0);
        System.out.printf("Interval: [%.6f, %.6f], step = %.6f, epsilon = %.6e%n", x0, xn, h, epsilon);
        System.out.println();

        if (results.isEmpty()) {
            System.out.println("We can not solve the equation with provided inputs. Please try again with different ones.");
            return;
        }
        for (MethodResult result : results) {
            printResult(result);
            System.out.println();
        }

        Path chartsDir = Path.of("build", "charts");
        
        try {
            Path chart = chartService.writeCombinedChart(equation, results, chartsDir);
            chartService.renderChart(chart);
            System.out.println("Charts were saved to build/charts/" + chart);
        } catch (IOException | PythonExecutionException e) {
            System.out.println("Exception while plotting: " + e.getMessage());
        }

    }

    private EquationDefinition chooseEquation() {
        System.out.println("Choose an equation:");
        List<EquationDefinition> equations = EquationCatalog.all();
        for (int i = 0; i < equations.size(); i++) {
            EquationDefinition equation = equations.get(i);
            System.out.printf("  %d) %s%n", i + 1, equation.title());
        }

        while (true) {
            System.out.print("Your choice: ");
            String line = readLine();
            if (line == null) {
                System.out.println();
                continue;
            }
            try {
                int choice = Integer.parseInt(line);
                if (choice >= 1 && choice <= equations.size()) {
                    return equations.get(choice - 1);
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Invalid selection. Try again.");
        }
    }

    private double readDouble(String prompt, double defaultValue) {
        while (true) {
            System.out.printf("%s [default %.6f]: ", prompt, defaultValue);
            String line = readLine();
            if (line == null) {
                System.out.println();
                return defaultValue;
            }
            if (line.isEmpty()) {
                return defaultValue;
            }
            try {
                return Double.parseDouble(line.replace(',', '.'));
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    private void validateInputs(double x0, double xn, double h, double epsilon) {
        if (!(xn > x0)) {
            throw new IllegalArgumentException("xn must be greater than x0");
        }
        if (!(h > 0.0)) {
            throw new IllegalArgumentException("h must be positive");
        }
        if (!(epsilon > 0.0)) {
            throw new IllegalArgumentException("epsilon must be positive");
        }
        double steps = (xn - x0) / h;
        if (Math.abs(Math.rint(steps) - steps) > 1e-9) {
            throw new IllegalArgumentException("(xn - x0) must be divisible by h for this lab program");
        }
    }

    private void printResult(MethodResult result) {
        System.out.printf("Method: %s%n", result.methodName());
        System.out.printf("Step size: %.6f%n", result.step());
        if (result.rungeEstimate() != null) {
            System.out.printf("Runge estimate: %.10e%n", result.rungeEstimate());
        }
        System.out.printf("Max absolute error: %.10e%n", result.maxAbsoluteError());
        System.out.println();
        System.out.println("      x               y(x) approx         y(x) exact          abs error");
        for (ComputationPoint point : result.points()) {
            System.out.printf("%10.6f   %18.10f   %18.10f   %14.10f%n",
                    point.x(), point.y(), point.exactValue(), point.absoluteError());
        }
    }

    private String readLine() {
        try {
            return scanner.nextLine().trim();
        } catch (NoSuchElementException ex) {
            return null;
        }
    }
}
