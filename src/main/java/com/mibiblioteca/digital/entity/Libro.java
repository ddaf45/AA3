package com.mibiblioteca.digital.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// Importar Lombok si lo usas
// import lombok.Data; // Incluye @Getter, @Setter, @ToString, @EqualsAndHashCode
// import lombok.NoArgsConstructor;
// import lombok.AllArgsConstructor;

@Entity // Anotación clave: Declara que esta clase es una entidad JPA y se mapea a una tabla en la DB
@Table(name = "libros") // (Opcional) Define el nombre de la tabla en la DB. Si no se especifica, usa el nombre de la clase.
// @Data @NoArgsConstructor @AllArgsConstructor // Anotaciones de Lombok
public class Libro {

    @Id // Anotación clave: Marca el campo como la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Anotación clave: Configura la estrategia para generar valores de la PK (IDENTITY para auto-incremento de la DB)
    private Long id;

    @Column(name = "titulo", nullable = false, length = 255) // Mapea el campo a una columna. nullable=false hace que sea NOT NULL.
    private String titulo;

    @Column(name = "autor", length = 100)
    private String autor;

    @Column(name = "isbn", unique = true, length = 17) // unique=true: asegura que el ISBN sea único en la tabla
    private String isbn;

    @Column(name = "anio_publicacion") // Hibernate convierte a snake_case por defecto, pero se puede especificar
    private Integer anioPublicacion;

    @Column(name = "genero", length = 50)
    private String genero;

    // Si no usas Lombok, debes añadir los constructores, getters y setters manualmente:

    public Libro() {
        // Constructor vacío es requerido por JPA
    }

    public Libro(String titulo, String autor, String isbn, Integer anioPublicacion, String genero) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.anioPublicacion = anioPublicacion;
        this.genero = genero;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Integer getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(Integer anioPublicacion) { this.anioPublicacion = anioPublicacion; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    @Override
    public String toString() {
        return "Libro{" +
               "id=" + id +
               ", titulo='" + titulo + '\'' +
               ", autor='" + autor + '\'' +
               ", isbn='" + isbn + '\'' +
               ", anioPublicacion=" + anioPublicacion +
               ", genero='" + genero + '\'' +
               '}';
    }
}