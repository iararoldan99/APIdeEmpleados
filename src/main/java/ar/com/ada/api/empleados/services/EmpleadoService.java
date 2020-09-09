package ar.com.ada.api.empleados.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.empleados.entities.AltaEmpleado;
import ar.com.ada.api.empleados.entities.Empleado;
import ar.com.ada.api.empleados.entities.AltaEmpleado.AltaEmpleadoResultEnum;
import ar.com.ada.api.empleados.entities.Empleado.EmpleadoEstadoEnum;
import ar.com.ada.api.empleados.repos.EmpleadoRepository;

@Service
public class EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    /**
     * Cambio el crear empleado, ahora devuelve otro objeto, que tiene el resultado
     * de la operacion de ALTA de un empleado, tambien tiene el empleado de
     * referencia(sirve para los casos donde se pasan todo por parametro y devolver
     * el objeto)
     * 
     * @param empleado
     * @return
     */
    public AltaEmpleado crearEmpleado(Empleado empleado) {
        AltaEmpleado alta = new AltaEmpleado();

        EmpleadoValidationType resultadoValidacion = grabar(empleado);

        // Aca en vez de usar IF, se usa el operador ternario "?"
        // Donde se expresa asi: variable = (condicion)?retorno verdadero:retorno falso;

        /**
         * if (resultadoValidacion == EmpleadoValidationType.EMPLEADO_OK)
            alta.setResultado(AltaEmpleadoResultEnum.REALIZADA);
        else
            alta.setResultado(AltaEmpleadoResultEnum.RECHAZADA);
         */
        alta.setResultado(resultadoValidacion == EmpleadoValidationType.EMPLEADO_OK ? AltaEmpleadoResultEnum.REALIZADA
                : AltaEmpleadoResultEnum.RECHAZADA);

        alta.setMotivo(resultadoValidacion);
        alta.setEmpleado(empleado);

        return alta;
    }

    public List<Empleado> obtenerEmpleados() {
        return empleadoRepository.findAll();
    }

    public Empleado obtenerPorId(int id) {
        return empleadoRepository.findById(id);
    }

    /***
     * Cambio el return del metodo grabar para que devuelva un enum de validacion Si
     * todo ok, graba.
     * 
     * @param empleada
     * @return
     */
    public EmpleadoValidationType grabar(Empleado empleada) {

        EmpleadoValidationType resultado = verificarEmpleado(empleada);
        if (resultado != EmpleadoValidationType.EMPLEADO_OK)
            return resultado;

        empleadoRepository.save(empleada);

        return EmpleadoValidationType.EMPLEADO_OK;

    }

    public enum EmpleadoValidationType {

        EMPLEADO_OK, EMPLEADO_DUPLICADO, EMPLEADO_INVALIDO, SUELDO_NULO, EDAD_INVALIDA, NOMBRE_INVALIDO, DNI_INVALIDO,
        EMPLEADO_DATOS_INVALIDOS

    }

    /**
     * Verifica que el nombre no est� nulo, La edad sea mayor a 0, El sueldo sea
     * mayor a 0, El estado, la fecha de alta y baja no est�n en nulo.
     * 
     * @param empleado
     * @return
     */

    public EmpleadoValidationType verificarEmpleado(Empleado empleado) {

        if (empleado.getNombre() == null)
            return EmpleadoValidationType.NOMBRE_INVALIDO;

        if (empleado.getEdad() <= 0)
            return EmpleadoValidationType.EDAD_INVALIDA;

        if (empleado.getSueldo().compareTo(new BigDecimal(0)) <= 0)
            return EmpleadoValidationType.SUELDO_NULO;
        if (empleado.getEstadoId() == EmpleadoEstadoEnum.DESCONOCIDO)
            return EmpleadoValidationType.EMPLEADO_DATOS_INVALIDOS; // ACA GENERICO
        if (empleado.getFechaAlta() == null)
            return EmpleadoValidationType.EMPLEADO_DATOS_INVALIDOS;
        if (empleado.getDni() <= 0)
            return EmpleadoValidationType.DNI_INVALIDO;

        Empleado e = empleadoRepository.findByDni(empleado.getDni());
        if (e != null) {
            if (empleado.getEmpleadoId() != 0) {
                if ((empleado.getEmpleadoId() != e.getEmpleadoId())) {
                    return EmpleadoValidationType.EMPLEADO_DUPLICADO;
                } else {
                    return EmpleadoValidationType.EMPLEADO_OK;
                }
            } else
                return EmpleadoValidationType.EMPLEADO_DUPLICADO;

        }
        return EmpleadoValidationType.EMPLEADO_OK;
    }
}