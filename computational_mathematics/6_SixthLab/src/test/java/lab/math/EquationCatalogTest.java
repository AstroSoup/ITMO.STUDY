package lab.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EquationCatalogTest {
    @Test
    void exactSolutionsMatchInitialConditions() {
        for (EquationDefinition equation : EquationCatalog.all()) {
            double expected = equation.defaultY0();
            double actual = equation.exactSolution(equation.defaultX0(), equation.defaultY0()).applyAsDouble(equation.defaultX0());
            assertEquals(expected, actual, 1e-12);
        }
    }
}
