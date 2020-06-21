package ar.com.ada.api.empleados.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.empleados.entities.Categoria;
import ar.com.ada.api.empleados.services.CategoriaService;
@RestController
public class CategoriaController {
    @Autowired
    CategoriaService categoriaService; 
    @PostMapping("/categorias")
    public ResponseEntity<?> crearCategoria(@RequestBody Categoria categoria){
        categoriaService.crearCategoria(categoria);
        return ResponseEntity.ok(categoria.getCategoriaId());

    }
}