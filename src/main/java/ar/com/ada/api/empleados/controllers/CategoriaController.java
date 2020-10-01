package ar.com.ada.api.empleados.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.empleados.entities.Categoria;
import ar.com.ada.api.empleados.entities.Empleado;
import ar.com.ada.api.empleados.models.response.CategoriasNombresResponse;
import ar.com.ada.api.empleados.models.response.GenericResponse;
import ar.com.ada.api.empleados.services.CategoriaService;

@RestController
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;

    @PostMapping("/categorias")
    public ResponseEntity<?> crearCategoria(@RequestBody Categoria categoria) {
        categoriaService.crearCategoria(categoria);
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = categoria.getCategoriaId();
        gR.message = "Categoria creada con exito";
        return ResponseEntity.ok(gR);

    }

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> listarCategoria() {
        return ResponseEntity.ok(categoriaService.obtenerCategorias());

    }

    @GetMapping("/categorias/{categoriaId}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable Integer categoriaId) {
        Categoria categoria = categoriaService.obtenerPorId(categoriaId);
        if (categoria == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(categoria);

    }

    @GetMapping("/categorias/sueldos-nuevos")
    public ResponseEntity<List<Empleado>> getSueldosNuevos() {

        return ResponseEntity.ok(categoriaService.calcularProximosSueldos());

    }

    @GetMapping("/categorias/sueldos-actuales")
    public ResponseEntity<List<Empleado>> getSueldosActuales() {

        return ResponseEntity.ok(categoriaService.obtenerSueldosActuales());

    }

    @GetMapping("/categorias/vacias")
    public ResponseEntity<List<Categoria>> getCategoriasSinEmpleados() {

        return ResponseEntity.ok(categoriaService.obtenerCategoriasSinEmpleados());

    }

    @GetMapping("/categorias/nombres")
    public ResponseEntity<CategoriasNombresResponse> getCategoriasNombres() {

        CategoriasNombresResponse r = new CategoriasNombresResponse();

        r.nombres = categoriaService.obtenerNombresCategorias();
        return ResponseEntity.ok(r);

    }
}