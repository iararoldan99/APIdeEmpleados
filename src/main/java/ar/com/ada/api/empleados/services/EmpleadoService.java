package ar.com.ada.api.empleados.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.empleados.entities.Empleado;
import ar.com.ada.api.empleados.repos.EmpleadoRepository;

@Service
public class EmpleadoService {
    @Autowired
    private EmpleadoRepository repo;

    public void grabar(Empleado empleada) {
        repo.save(empleada);
    }

    public void crearEmpleado(Empleado empleado) {
        grabar(empleado);
    }

    public List<Empleado> obtenerEmpleados() {
        return repo.findAll();
    }

    public Empleado obtenerPorId(int id) {
        return repo.findById(id);
    }

}