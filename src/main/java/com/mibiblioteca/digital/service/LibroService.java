package com.mibiblioteca.digital.service;

import com.mibiblioteca.digital.entity.Libro;
import com.mibiblioteca.digital.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Anotación clave: Marca esta clase como un componente de servicio de Spring
public class LibroService {

    @Autowired // Anotación clave: Inyección de dependencias (Spring inyecta una instancia de LibroRepository aquí)
    private LibroRepository libroRepository;

    public Libro guardarLibro(Libro libro) {
        // Aquí podrías añadir lógica de negocio, por ejemplo, validar datos
        if (libro.getTitulo() == null || libro.getTitulo().isEmpty()) {
            throw new IllegalArgumentException("El título del libro no puede estar vacío.");
        }
        if (libro.getIsbn() != null && libroRepository.existsByIsbn(libro.getIsbn())) {
            throw new IllegalArgumentException("Ya existe un libro con el ISBN: " + libro.getIsbn());
        }
        return libroRepository.save(libro); // Utiliza el método save de JpaRepository
    }

    public List<Libro> obtenerTodosLosLibros() {
        return libroRepository.findAll(); // Utiliza el método findAll de JpaRepository
    }

    public Optional<Libro> obtenerLibroPorId(Long id) {
        return libroRepository.findById(id); // Utiliza el método findById de JpaRepository
    }

    public Libro actualizarLibro(Long id, Libro libroActualizado) {
        Optional<Libro> libroExistente = libroRepository.findById(id);
        if (libroExistente.isPresent()) {
            Libro libro = libroExistente.get();
            libro.setTitulo(libroActualizado.getTitulo());
            libro.setAutor(libroActualizado.getAutor());
            libro.setIsbn(libroActualizado.getIsbn());
            libro.setAnioPublicacion(libroActualizado.getAnioPublicacion());
            libro.setGenero(libroActualizado.getGenero());
            // Podrías añadir más lógica de validación o transformación aquí
            return libroRepository.save(libro);
        } else {
            throw new IllegalArgumentException("Libro con ID " + id + " no encontrado para actualizar.");
        }
    }

    public void eliminarLibro(Long id) {
        // Aquí puedes añadir una verificación antes de eliminar
        if (!libroRepository.existsById(id)) {
            throw new IllegalArgumentException("Libro con ID " + id + " no encontrado para eliminar.");
        }
        libroRepository.deleteById(id); // Utiliza el método deleteById de JpaRepository
    }

    public List<Libro> buscarLibrosPorAutor(String autor) {
        return libroRepository.findByAutor(autor);
    }

    public List<Libro> buscarLibrosPorTitulo(String titulo) {
        return libroRepository.findByTituloContainingIgnoreCase(titulo);
    }
}