package ar.com.ada.api.empleados.controllers;

import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.empleados.entities.AltaEmpleado;
import ar.com.ada.api.empleados.entities.Empleado;
import ar.com.ada.api.empleados.entities.AltaEmpleado.AltaEmpleadoResultEnum;
import ar.com.ada.api.empleados.entities.Empleado.EmpleadoEstadoEnum;
import ar.com.ada.api.empleados.models.response.GenericResponse;
import ar.com.ada.api.empleados.models.request.InfoEmpleadaRequest;
import ar.com.ada.api.empleados.models.request.SueldoModifRequest;
import ar.com.ada.api.empleados.services.*;

@RestController
public class EmpleadoController {
    @Autowired
    EmpleadoService empleadoService;
    @Autowired
    CategoriaService categoriaService;

    @PostMapping("/empleadas")
    public ResponseEntity<?> crearEmpleado(@RequestBody InfoEmpleadaRequest info) {
        Empleado empleado = new Empleado();
        empleado.setNombre(info.nombre);
        empleado.setEdad(info.edad);
        empleado.setSueldo(info.sueldo);
        empleado.setDni(info.dni);
        empleado.setFechaAlta(new Date());
        empleado.setCategoria(categoriaService.obtenerPorId(info.categoria));
        empleado.setEstadoId(EmpleadoEstadoEnum.ACTIVO);
        AltaEmpleado alta = empleadoService.crearEmpleado(empleado);
        GenericResponse gR = new GenericResponse();
        if (alta.getResultado() == AltaEmpleadoResultEnum.REALIZADA) {
            gR.isOk = true;
            gR.id = empleado.getEmpleadoId();
            gR.message = "Empleada creada con exito";
        } else {
            gR.isOk = false;
            gR.message = "Error al crear empleada, motivo: " + alta.getMotivo();
        }
        return ResponseEntity.ok(gR);

    }

    @GetMapping("/empleadas")
    public ResponseEntity<List<Empleado>> listarEmpleadas() {
        return ResponseEntity.ok(empleadoService.obtenerEmpleados());
    }

    @GetMapping("/empleadas/{id}")
    public ResponseEntity<Empleado> obtenerEmpleada(@PathVariable int id) {
        Empleado empleada = empleadoService.obtenerPorId(id);
        if (empleada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(empleada);
    }

    @GetMapping("/empleadas/categorias/{categoriaId}")
    public ResponseEntity<List<Empleado>> listarPorCategoriaId(@PathVariable int categoriaId) {
        List<Empleado> listaEmpleadas = categoriaService.obtenerPorId(categoriaId).getEmpleados();
        return ResponseEntity.ok(listaEmpleadas);

    }

    @PutMapping("/empleadas/{id}/sueldos")
    public ResponseEntity<GenericResponse> actualizarSueldo(@PathVariable Integer id,
            @RequestBody SueldoModifRequest sueldoRequest) {
        Empleado empleada = empleadoService.obtenerPorId(id);
        if (empleada == null) {
            return ResponseEntity.notFound().build();
        }
        empleada.setSueldo(sueldoRequest.sueldoNuevo);
        empleadoService.grabar(empleada);
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = empleada.getEmpleadoId();
        gR.message = "Sueldo actualizado con exito";
        return ResponseEntity.ok(gR);
    }

    @DeleteMapping("/empleadas/{id}")
    public ResponseEntity<GenericResponse> bajaEmpleada(@PathVariable int id) {
        Empleado empleada = empleadoService.obtenerPorId(id);
        if (empleada == null) {
            return ResponseEntity.notFound().build();
        }
        empleada.setFechaBaja(new Date());
        empleada.setEstadoId(EmpleadoEstadoEnum.DESVINCULADO);
        empleadoService.grabar(empleada);
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = empleada.getEmpleadoId();
        gR.message = "Empleada borrada con exito";
        return ResponseEntity.ok(gR);
    }
}
