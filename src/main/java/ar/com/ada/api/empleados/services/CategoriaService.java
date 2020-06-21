package ar.com.ada.api.empleados.services;

import ar.com.ada.api.empleados.entities.Categoria;
import ar.com.ada.api.empleados.repos.CategoriaRepository;

public class CategoriaService {
    private CategoriaRepository repo;

    public void crearCategoria(Categoria categoria){
        repo.save(categoria);
    }
}