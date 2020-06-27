package ar.com.ada.api.empleados.controllers;

import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.empleados.entities.Empleado;
import ar.com.ada.api.empleados.models.response.GenericResponse;
import ar.com.ada.api.empleados.models.request.InfoEmpleadaRequest;
import ar.com.ada.api.empleados.services.*;

@RestController
public class EmpleadoController {
    @Autowired
    EmpleadoService empleadoService;
    @Autowired
    CategoriaService categoriaService;

    @PostMapping("/empleadas")
    public ResponseEntity<?> crearEmpleado(@RequestBody InfoEmpleadaRequest info){
        Empleado empleado = new Empleado();
        empleado.setNombre(info.nombre);
        empleado.setEdad(info.edad);
        empleado.setSueldo(info.sueldo);
        empleado.setFechaAlta(new Date());
        empleado.setCategoria(categoriaService.obtenerPorId(info.categoriaId));
        empleado.setEstadoId(1);
        empleadoService.crearEmpleado(empleado);
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = empleado.getEmpleadoId();
        gR.message = "Empleada creada con exito";
        return ResponseEntity.ok(gR);
    }

    @GetMapping("/empleadas")
    public ResponseEntity<List<Empleado>> listarEmpleadas(){
        return ResponseEntity.ok(empleadoService.obtenerEmpleados());
    }
} 