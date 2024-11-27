package com.example.wallapod.repository;

import com.example.wallapod.entity.Categoria;
import com.example.wallapod.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Buscar productos por categoría ordenados por fecha de creación descendente
    List<Producto> findByCategoriaOrderByFechaCreacionDesc(Categoria categoria);

}