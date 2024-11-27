package com.example.wallapod.entity;

import com.example.wallapod.entity.FotoProducto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El título no puede estar en blanco")
    @Column(length = 1000, nullable = false)
    private String titulo;

    @NotNull(message = "El precio no puede estar en blanco")
    @Min(value = 0, message = "El precio debe ser positivo")
    private Double precio;

    @NotEmpty(message = "La descripción no puede estar en blanco")
    @Column(length = 2000, nullable = false)
    private String descripcion;

    @ManyToOne(targetEntity = Categoria.class)
    @JoinColumn(name = "id_categoria", nullable = false)
    @NotNull(message = "Debes seleccionar una categoría")
    private Categoria categoria;

    @OneToMany(mappedBy = "producto", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @EqualsAndHashCode.Exclude // Excluir de hashCode y equals
    private List<FotoProducto> fotos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Fecha de creación automática
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }

    // Métodos para gestionar la relación con FotoProducto

    // Añadir una foto al producto
    public void addFoto(FotoProducto foto) {
        fotos.add(foto);
        foto.setProducto(this); // Establecer la relación inversa
    }

    // Eliminar una foto del producto
    public void removeFoto(FotoProducto foto) {
        fotos.remove(foto);
        foto.setProducto(null); // Romper la relación inversa
    }
}
