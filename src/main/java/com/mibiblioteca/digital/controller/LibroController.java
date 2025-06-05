package com.mibiblioteca.digital.controller;

import com.mibiblioteca.digital.entity.Libro;
import com.mibiblioteca.digital.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // <--- CAMBIAR @RestController a @Controller
import org.springframework.ui.Model; // Necesario para pasar datos a las vistas
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Para mensajes flash y redirecciones

import java.util.List;
import java.util.Optional;

@Controller // <--- Aquí el cambio principal
@RequestMapping("/libros") // <--- Cambiar el path base si quieres que sea /libros y no /api/libros
public class LibroController {

    @Autowired
    private LibroService libroService;

    // Endpoint para listar todos los libros
    @GetMapping // Mapea a /libros
    public String listarLibros(Model model) {
        List<Libro> libros = libroService.obtenerTodosLosLibros();
        model.addAttribute("libros", libros);
        return "libros/lista"; // Retorna el nombre de la plantilla
    }

    // Endpoint para mostrar el formulario de añadir nuevo libro
    @GetMapping("/nuevo") // Mapea a /libros/nuevo
    public String mostrarFormularioNuevoLibro(Model model) {
        model.addAttribute("libro", new Libro());
        return "libros/formulario"; // Plantilla para el formulario (creación y edición)
    }

    // Endpoint para guardar un nuevo libro (desde el formulario)
    @PostMapping("/guardar") // Mapea POST a /libros/guardar
    public String guardarLibro(@ModelAttribute Libro libro, RedirectAttributes redirectAttributes) {
        try {
            libroService.guardarLibro(libro);
            redirectAttributes.addFlashAttribute("mensajeExito", "Libro guardado exitosamente!");
            return "redirect:/libros"; // Redirige a la lista
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
            // Si hay un error, vuelve al formulario con el objeto libro para que el usuario no pierda los datos
            return "libros/formulario"; // Vuelve al formulario con el error
        }
    }

    // Endpoint para mostrar el formulario de edición de un libro existente
    @GetMapping("/editar/{id}") // Mapea a /libros/editar/{id}
    public String mostrarFormularioEditarLibro(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Libro> libro = libroService.obtenerLibroPorId(id);
        if (libro.isPresent()) {
            model.addAttribute("libro", libro.get());
            return "libros/formulario";
        } else {
            redirectAttributes.addFlashAttribute("mensajeError", "Libro no encontrado para editar.");
            return "redirect:/libros";
        }
    }

    // Endpoint para actualizar un libro existente (desde el formulario)
    @PostMapping("/actualizar/{id}") // Mapea POST a /libros/actualizar/{id}
    public String actualizarLibro(@PathVariable Long id, @ModelAttribute Libro libroDetalles, RedirectAttributes redirectAttributes) {
        try {
            libroService.actualizarLibro(id, libroDetalles);
            redirectAttributes.addFlashAttribute("mensajeExito", "Libro actualizado exitosamente!");
            return "redirect:/libros";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
            return "libros/formulario"; // Vuelve al formulario con el error
        }
    }

    // Endpoint para eliminar un libro
    // Para simplificar la demo, usamos GET, pero en una app real sería POST/DELETE para seguridad
    @GetMapping("/eliminar/{id}") // Mapea GET a /libros/eliminar/{id}
    public String eliminarLibro(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            libroService.eliminarLibro(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Libro eliminado exitosamente!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/libros"; // Redirige a la lista después de eliminar
    }

    // Métodos de búsqueda para el frontend HTML
    @GetMapping("/buscar") // Mapea a /libros/buscar
    public String buscarLibros(@RequestParam(name = "query", required = false) String query, Model model) {
        List<Libro> resultados;
        if (query != null && !query.trim().isEmpty()) {
            // Puedes buscar por título o autor, o combinar la búsqueda
            resultados = libroService.buscarLibrosPorTitulo(query);
            if (resultados.isEmpty()) {
                resultados = libroService.buscarLibrosPorAutor(query);
            }
        } else {
            resultados = libroService.obtenerTodosLosLibros();
        }
        model.addAttribute("libros", resultados);
        return "libros/lista"; // Reutiliza la misma plantilla para mostrar resultados
    }
}