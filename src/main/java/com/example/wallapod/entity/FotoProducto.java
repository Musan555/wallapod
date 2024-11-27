package com.example.wallapod.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fotos_productos")
public class FotoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne(targetEntity = Producto.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    @EqualsAndHashCode.Exclude // Excluir de hashCode y equals
    private Producto producto;
}

