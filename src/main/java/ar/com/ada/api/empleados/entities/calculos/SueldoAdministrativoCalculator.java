package ar.com.ada.api.empleados.entities.calculos;

import java.math.BigDecimal;

import ar.com.ada.api.empleados.entities.*;

/**
 * SueldoAdministrativoCalculartor En este caso el resultado es el sueldo actual
 * de la categoria actual Si el sueldo actual es menor al de la categoria se
 * reajusta ej cuando hay inflacion y estan en el minimo
 */
public class SueldoAdministrativoCalculator implements SueldoCalculator {

    @Override
    public BigDecimal calcularSueldo(Empleado empleado) {

        BigDecimal sueldoActual = empleado.getSueldo();
        if (sueldoActual.compareTo(empleado.getCategoria().getSueldoBase()) < 0)
            return empleado.getCategoria().getSueldoBase();

        return sueldoActual;
    }
}