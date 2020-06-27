package ar.com.ada.api.empleados.services;
import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.empleados.entities.Categoria;
import ar.com.ada.api.empleados.repos.CategoriaRepository;
@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repo;

    public void crearCategoria(Categoria categoria){
        repo.save(categoria);
    } 
  

    // en categoriaservice devolvemos una categoria
    // el crear las categorias lo delega en el repo, usamos los metodos que ya vienen preescritos

    public List<Categoria> obtenerCategorias(){
       return (repo.findAll()); // genera una lista de categorias y se hace un return 
    }

    public Categoria obtenerPorId(int categoriaId){
        // el findById devuelve un optional, si la encuentra devuelve un objeto, sino nulo
        Optional<Categoria> c = repo.findById(categoriaId);

        if (c.isPresent())
            return c.get();
        return null;

    }
}