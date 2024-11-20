package com.example.wallapod.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="Anuncios")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El título no puede estar en blanco")
    @Column(length = 1000)
    private String titulo;

    @NotNull(message = "El precio no puede estar en blanco")
    @Min(value = 0, message = "El precio debe ser positivo")
    private Double precio;

    @NotEmpty(message = "La descripcion no puede estar en blanco")
    @Column(length = 2000)
    private String descripcion;

    @ManyToOne(targetEntity = Categoria.class)
    @JoinColumn(name = "id_categoria")
    @NotNull (message ="Debes seleccionar una categoría")
    private Categoria categoria;

    @OneToMany(targetEntity = FotoProducto.class, cascade = CascadeType.ALL,
            mappedBy = "producto")
    private List<FotoProducto> fotos = new ArrayList<>();

}