package ar.com.ada.api.empleados.entities;

import java.math.BigDecimal;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

@Entity
@Table(name = "categoria")
public class Categoria {
    @Column(name = "categoria_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoriaId;
    private String nombre;
    @Column(name = "sueldo_base")
    private BigDecimal sueldoBase;
    // mappedBy busca el atributo 'categoria' en la clase empleado
    // cascadetypeALL trae todos los empleados de esa categoria 
    // fetch obliga a que traiga todos de una. el lazy solo los trae cuando se llama, no se cargan de una
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // Ignora ese atributo cuando front lo manda
    @JsonIgnore
    private List<Empleado> empleados = new ArrayList<>();

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

}