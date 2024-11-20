package com.example.wallapod.repository;

import com.example.wallapod.DTO.CategoriaCosteMedioDTO;
import com.example.wallapod.entity.Categoria;
import com.example.wallapod.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.reflect.TypeVariable;
import java.math.BigDecimal;
import java.util.List;

@Repository //Indica que esta clase es un repositorio
public interface ProductoRepository extends JpaRepository<Producto,Long> {
    List<Producto> findByCategoria(Categoria categoria);
    Long countByCategoria(Categoria categoria); //Número de productos en una categoría


}