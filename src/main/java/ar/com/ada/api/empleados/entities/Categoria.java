package ar.com.ada.api.empleados.entities;

import java.math.BigDecimal;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import ar.com.ada.api.empleados.entities.calculos.SueldoAdministrativoCalculator;
import ar.com.ada.api.empleados.entities.calculos.SueldoAuxiliarCalculator;
import ar.com.ada.api.empleados.entities.calculos.SueldoCalculator;
import ar.com.ada.api.empleados.entities.calculos.SueldoVentasCalculator;

import javax.persistence.*;

@Entity
@Table(name = "categoria")
public class Categoria {
    @Column(name = "categoria_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoriaId;
    private String nombre;
    @Column(name = "sueldo_base")
    private BigDecimal sueldoBase;
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Empleado> empleados = new ArrayList<>();
    @JsonIgnore
    @Transient // No meterlo a la base de datos. Es transitorio
    private SueldoCalculator sueldoStrategy;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        switch (this.nombre) {

            case "Ventas":
                this.setSueldoStrategy(new SueldoVentasCalculator());
                break;
            case "Administrativa":
            case "Administrativo":
                this.setSueldoStrategy(new SueldoAdministrativoCalculator());
                break;
            case "Auxiliar":
                this.setSueldoStrategy(new SueldoAuxiliarCalculator());
                break;

            default:

                this.setSueldoStrategy(new SueldoAdministrativoCalculator());
                break;
        }
    }

    public BigDecimal getSueldoBase() {
        return sueldoBase;
    }

    public void setSueldoBase(BigDecimal sueldoBase) {
        this.sueldoBase = sueldoBase;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    /**
     * @return the sueldoStrategy
     */
    public SueldoCalculator getSueldoStrategy() {

        // si esta en nulo, le genero la estrategy a traves del nombre
        // Cuando vienen desde la db este valor esta en nulo
        // por lo cual lo reasigno.
        if (this.sueldoStrategy == null)
            this.setNombre(this.getNombre());
        return sueldoStrategy;
    }

    /**
     * @param sueldoStrategy the sueldoStrategy to set
     */
    public void setSueldoStrategy(SueldoCalculator sueldoStrategy) {
        this.sueldoStrategy = sueldoStrategy;
    }

    public BigDecimal calcularSueldo(Empleado empleado) {
        return this.getSueldoStrategy().calcularSueldo(empleado);
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

}