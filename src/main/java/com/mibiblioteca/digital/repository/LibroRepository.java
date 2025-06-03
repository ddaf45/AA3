package com.mibiblioteca.digital.repository;

import com.mibiblioteca.digital.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Anotación clave: Marca esta interfaz como un componente de repositorio de Spring
public interface LibroRepository extends JpaRepository<Libro, Long> {
    // Al extender JpaRepository, automáticamente tienes métodos CRUD como:
    // save(Libro entity), findById(ID id), findAll(), deleteById(ID id), etc.

    // Puedes añadir métodos personalizados, y Spring Data JPA los implementará basados en el nombre:
    List<Libro> findByAutor(String autor); // Busca libros por autor
    List<Libro> findByTituloContainingIgnoreCase(String titulo); // Busca libros por título (insensible a mayúsculas/minúsculas)
    boolean existsByIsbn(String isbn); // Verifica si un libro con un ISBN dado ya existe
}