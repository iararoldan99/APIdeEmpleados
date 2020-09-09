package ar.com.ada.api.empleados.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.empleados.entities.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer>{

    // en una interface todos los metodos son publicos
    // interface es un conjunto de metodos que pueden ser usados por cualquiera

    // al crear este metodo no tenemos que usar el Optional y son menos lineas de codigo

    Empleado findById(int id); 
    
    // no lleva codigo dentro

    Empleado findByNombre(String nombre);

    Empleado findByDni(int id);
} 