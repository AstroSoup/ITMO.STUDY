package lab.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DifferentialEquationServiceTest {
    @Test
    void rungeKuttaComputationWorks() {
        EquationDefinition equation = EquationCatalog.all().get(0);
        DifferentialEquationService service = new DifferentialEquationService();
        
        MethodResult result = service.solveWithRungeKutta(
            equation,
            equation.defaultX0(),
            equation.defaultY0(),
            equation.defaultXn(),
            equation.defaultStep(),
            equation.defaultEpsilon()
        );
        
        assertNotNull(result);
        assertEquals("Runge-Kutta 4 method", result.methodName());
        assertTrue(result.points().size() > 0);
        for (ComputationPoint point : result.points()) {
            assertTrue(Double.isFinite(point.y()), "Found non-finite y value");
            assertTrue(Double.isFinite(point.exactValue()), "Found non-finite exact value");
        }
    }
    
    @Test
    void enhancedEulerComputationWorks() {
        EquationDefinition equation = EquationCatalog.all().get(0);
        DifferentialEquationService service = new DifferentialEquationService();
        
        MethodResult result = service.solveWithEnhancedEuler(
            equation,
            equation.defaultX0(),
            equation.defaultY0(),
            equation.defaultXn(),
            equation.defaultStep(),
            equation.defaultEpsilon()
        );
        
        assertNotNull(result);
        assertEquals("Enhanced Euler method", result.methodName());
        assertTrue(result.points().size() > 0);
        for (ComputationPoint point : result.points()) {
            assertTrue(Double.isFinite(point.y()));
            assertTrue(Double.isFinite(point.exactValue()));
        }
    }
    
    @Test
    void milneComputationWorks() {
        EquationDefinition equation = EquationCatalog.all().get(0);
        DifferentialEquationService service = new DifferentialEquationService();
        
        MethodResult result = service.solveWithMilne(
            equation,
            equation.defaultX0(),
            equation.defaultY0(),
            equation.defaultXn(),
            equation.defaultStep(),
            equation.defaultEpsilon()
        );
        
        assertNotNull(result);
        assertEquals("Milne method", result.methodName());
        assertTrue(result.points().size() > 0);
        for (ComputationPoint point : result.points()) {
            assertTrue(Double.isFinite(point.y()));
            assertTrue(Double.isFinite(point.exactValue()));
        }
    }
    
    @Test
    void allMethodsProduceFiniteResults() {
        for (EquationDefinition equation : EquationCatalog.all()) {
            DifferentialEquationService service = new DifferentialEquationService();
            
            MethodResult rk4 = service.solveWithRungeKutta(
                equation, equation.defaultX0(), equation.defaultY0(), 
                equation.defaultXn(), equation.defaultStep(), equation.defaultEpsilon()
            );
            
            MethodResult euler = service.solveWithEnhancedEuler(
                equation, equation.defaultX0(), equation.defaultY0(), 
                equation.defaultXn(), equation.defaultStep(), equation.defaultEpsilon()
            );
            
            MethodResult milne = service.solveWithMilne(
                equation, equation.defaultX0(), equation.defaultY0(), 
                equation.defaultXn(), equation.defaultStep(), equation.defaultEpsilon()
            );
            
            // Verify all results have finite values
            for (ComputationPoint p : rk4.points()) {
                assertTrue(Double.isFinite(p.y()), "RK4 non-finite for " + equation.title());
            }
            for (ComputationPoint p : euler.points()) {
                assertTrue(Double.isFinite(p.y()), "Euler non-finite for " + equation.title());
            }
            for (ComputationPoint p : milne.points()) {
                assertTrue(Double.isFinite(p.y()), "Milne non-finite for " + equation.title());
            }
        }
    }
}
