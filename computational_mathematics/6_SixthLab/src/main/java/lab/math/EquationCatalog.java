package lab.math;

import java.util.List;

public final class EquationCatalog {
    private static final List<EquationDefinition> EQUATIONS = List.of(
            new EquationDefinition(
                    "y' = y + (1 + x) y^2",
                    1.0,
                    -1.0,
                    1.5,
                    0.1,
                    1e-6,
                    (x, y) -> y + (1.0 + x) * y * y,
                    (x0, y0) -> x -> 1.0 / ((1.0 / y0 + x0) * Math.exp(x0 - x) - x)
            ),
            new EquationDefinition(
                    "y' = x + y",
                    0.0,
                    1.0,
                    1.0,
                    0.1,
                    1e-6,
                    (x, y) -> x + y,
                    (x0, y0) -> x -> (y0 + x0 + 1.0) * Math.exp(x - x0) - x - 1.0
            ),
            new EquationDefinition(
                    "y' = y - x^2 + 1",
                    0.0,
                    0.5,
                    2.0,
                    0.1,
                    1e-6,
                    (x, y) -> y - x * x + 1.0,
                    (x0, y0) -> x -> (y0 - x0 * x0 - 2.0 * x0 - 1.0) * Math.exp(x - x0) + x * x + 2.0 * x + 1.0
            )
    );

    private EquationCatalog() {
    }

    public static List<EquationDefinition> all() {
        return EQUATIONS;
    }
}
