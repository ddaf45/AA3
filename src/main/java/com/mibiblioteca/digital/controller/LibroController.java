package com.mibiblioteca.digital.controller;

import com.mibiblioteca.digital.entity.Libro;
import com.mibiblioteca.digital.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Importar HttpStatus
import org.springframework.http.ResponseEntity; // Importar ResponseEntity
import org.springframework.web.bind.annotation.*; // Importar todas las anotaciones de RestController

import java.util.List;

@RestController // Anotación clave: Combina @Controller y @ResponseBody. Indica que es un controlador REST.
@RequestMapping("/api/libros") // Anotación clave: Define el path base para todos los endpoints en esta clase
public class LibroController {

    @Autowired // Inyección de dependencias del servicio
    private LibroService libroService;

    // Endpoint para añadir un nuevo libro
    @PostMapping // Anotación clave: Mapea solicitudes HTTP POST a este método
    public ResponseEntity<Libro> crearLibro(@RequestBody Libro libro) { // @RequestBody: Convierte el JSON del cuerpo de la solicitud a un objeto Libro
        try {
            Libro nuevoLibro = libroService.guardarLibro(libro);
            return new ResponseEntity<>(nuevoLibro, HttpStatus.CREATED); // Retorna el libro creado y un estado 201 CREATED
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Retorna 400 BAD REQUEST en caso de error de validación
        }
    }

    // Endpoint para obtener todos los libros
    @GetMapping // Anotación clave: Mapea solicitudes HTTP GET a este método
    public List<Libro> obtenerTodosLosLibros() {
        return libroService.obtenerTodosLosLibros();
    }

    // Endpoint para obtener un libro por su ID
    @GetMapping("/{id}") // {id} es una variable de ruta
    public ResponseEntity<Libro> obtenerLibroPorId(@PathVariable Long id) { // @PathVariable: Extrae el valor de la variable de ruta
        return libroService.obtenerLibroPorId(id)
                .map(ResponseEntity::ok) // Si el libro existe, retorna 200 OK con el libro
                .orElseGet(() -> ResponseEntity.notFound().build()); // Si no, retorna 404 NOT FOUND
    }

    // Endpoint para actualizar un libro existente
    @PutMapping("/{id}") // Mapea solicitudes HTTP PUT
    public ResponseEntity<Libro> actualizarLibro(@PathVariable Long id, @RequestBody Libro libroDetalles) {
        try {
            Libro libroActualizado = libroService.actualizarLibro(id, libroDetalles);
            return ResponseEntity.ok(libroActualizado); // Retorna 200 OK con el libro actualizado
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // Si el libro no se encuentra, retorna 404 NOT FOUND
        }
    }

    // Endpoint para eliminar un libro
    @DeleteMapping("/{id}") // Mapea solicitudes HTTP DELETE
    public ResponseEntity<Void> eliminarLibro(@PathVariable Long id) {
        try {
            libroService.eliminarLibro(id);
            return ResponseEntity.noContent().build(); // Retorna 204 NO CONTENT (éxito sin contenido de respuesta)
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // Si el libro no se encuentra, retorna 404 NOT FOUND
        }
    }

    // Endpoint para buscar libros por autor
    @GetMapping("/autor/{autor}")
    public List<Libro> buscarLibrosPorAutor(@PathVariable String autor) {
        return libroService.buscarLibrosPorAutor(autor);
    }

    // Endpoint para buscar libros por título
    @GetMapping("/titulo/{titulo}")
    public List<Libro> buscarLibrosPorTitulo(@PathVariable String titulo) {
        return libroService.buscarLibrosPorTitulo(titulo);
    }
}