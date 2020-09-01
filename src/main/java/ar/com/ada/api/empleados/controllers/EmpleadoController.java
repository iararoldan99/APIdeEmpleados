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

import ar.com.ada.api.empleados.entities.Empleado;
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

    @GetMapping("/empleadas/{id}")
    // pathvariable es una variable de ruta, la variable id de tipo int
    // va a estar en la ruta, se tiene que llavar igual a como esta declarado arriba
    public ResponseEntity<Empleado> obtenerEmpleada(@PathVariable int id){
        // siempre queremos que devuelva ok
        Empleado empleada = empleadoService.obtenerPorId(id);
        if(empleada == null) {
            return ResponseEntity.notFound().build();
            // not found devuleve un objeto a construir
            // return new ResponseEntity<>(HttpStatus.NOT_FOUND)
        }
         return ResponseEntity.ok(empleada);
    }

    @GetMapping("/empleadas/categorias/{categoriaId}")
    public ResponseEntity<List<Empleado>> listarPorCategoriaId(@PathVariable int categoriaId){
        List<Empleado> listaEmpleadas = categoriaService.obtenerPorId(categoriaId).getEmpleados();
        return ResponseEntity.ok(listaEmpleadas);

    }

    // actualiza el sueldo por id 
    @PutMapping("/empleadas/{id}/sueldos")
    public ResponseEntity<GenericResponse> actualizarSueldo(@PathVariable int id, @RequestBody SueldoModifRequest sueldoRequest){
        Empleado empleada = empleadoService.obtenerPorId(id);
        if(empleada == null) {
            return ResponseEntity.notFound().build();
            // not found devuleve un objeto a construir
            // return new ResponseEntity<>(HttpStatus.NOT_FOUND)
        }
        empleada.setSueldo(sueldoRequest.sueldoNuevo);
        empleadoService.grabar(empleada);
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = empleada.getEmpleadoId();
        gR.message = "Sueldo actualizado con exito";
        return ResponseEntity.ok(gR);
    } 

    // en el put se manda algo si o si, en el delete no se manda nada
    // es un borrado logico

    @DeleteMapping("/empleadas/{id}") // lo que esta entre las llaves son path variables
    public ResponseEntity<GenericResponse> bajaEmpleada(@PathVariable int id){
        Empleado empleada = empleadoService.obtenerPorId(id);
        if(empleada == null) {
            return ResponseEntity.notFound().build();
            // not found devuleve un objeto a construir
            // return new ResponseEntity<>(HttpStatus.NOT_FOUND)
        }
        empleada.setFechaBaja(new Date());
        empleada.setEstadoId(1); // 1 significa INACTIVO
        empleadoService.grabar(empleada);
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = empleada.getEmpleadoId();
        gR.message = "Empleada borrada con exito";
        return ResponseEntity.ok(gR);
    }
    }
    


    
