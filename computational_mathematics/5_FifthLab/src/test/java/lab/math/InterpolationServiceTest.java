package lab.math;

import lab.model.DataPoint;
import lab.model.DataSet;
import lab.model.FunctionType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InterpolationServiceTest {
    private final InterpolationService service = new InterpolationService();

    @Test
    void lagrangeReproducesQuadratic() {
        List<DataPoint> points = List.of(
                new DataPoint(-1.0, 1.0),
                new DataPoint(0.0, 0.0),
                new DataPoint(1.0, 1.0),
                new DataPoint(2.0, 4.0)
        );

        assertEquals(2.25, service.interpolateLagrange(points, 1.5), 1e-9);
    }

    @Test
    void newtonReproducesQuadraticOnEquallySpacedNodes() {
        DataSet dataSet = DataSet.generated("quadratic", x -> x * x, -1.0, 2.0, 4);
        assertEquals(2.25, service.interpolateNewton(dataSet, 1.5), 1e-9);
    }

    @Test
    void gaussReproducesQuadraticOnOddWindow() {
        DataSet dataSet = DataSet.generated("quadratic", x -> x * x, -2.0, 2.0, 5);
        assertEquals(0.25, service.interpolateGauss(dataSet, 0.5), 1e-9);
    }

    @Test
    void stirlingReproducesQuadraticOnCenteredWindow() {
        DataSet dataSet = DataSet.generated("quadratic", x -> x * x, -2.0, 2.0, 5);
        assertEquals(0.25, service.interpolateStirling(dataSet, 0.5), 1e-9);
    }

    @Test
    void besselReproducesQuadraticOnCentralPair() {
        DataSet dataSet = DataSet.generated("quadratic", x -> x * x, -2.0, 3.0, 6);
        assertEquals(0.25, service.interpolateBessel(dataSet, 0.5), 1e-9);
    }

    @Test
    void duplicateXValuesAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> DataSet.table("bad", List.of(
                new DataPoint(0.0, 1.0),
                new DataPoint(0.0, 2.0)
        )));
    }

    @Test
    void generatedFunctionUsesSourceFunction() {
        DataSet dataSet = DataSet.generated("sin", FunctionType.SIN, 0.0, Math.PI, 5);
        assertEquals(Math.sin(0.7), dataSet.sourceFunction().orElseThrow().apply(0.7), 1e-9);
    }
}
