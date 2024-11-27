package com.example.wallapod.service;

import com.example.wallapod.entity.Categoria;
import com.example.wallapod.entity.Producto;
import com.example.wallapod.repository.CategoriaRepository;
import com.example.wallapod.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository,
                           CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // Obtener todos los productos ordenados por fecha de creación descendente
    public List<Producto> findAllProductosOrderByFechaCreacionDesc() {
        return productoRepository.findAll(Sort.by(Sort.Order.desc("fechaCreacion")));
    }

    // Obtener productos por categoría, ordenados por fecha de creación descendente
    public List<Producto> findProductosByCategoriaOrderByFechaCreacionDesc(Categoria categoria) {
        return productoRepository.findByCategoriaOrderByFechaCreacionDesc(categoria);
    }

    // Obtener todos los productos (sin ordenar)
    public List<Producto> findAllProductos() {
        return productoRepository.findAll();
    }

    // Obtener todas las categorías
    public List<Categoria> findAllCategorias() {
        return categoriaRepository.findAll();
    }

    // Obtener una categoría por ID
    public Optional<Categoria> findCategoriaById(Long id) {
        return categoriaRepository.findById(id);
    }

    // Obtener un producto por su ID
    public Optional<Producto> findProductoById(Long id) {
        return productoRepository.findById(id);
    }

    // Eliminar un producto por su ID
    public void deleteProductoById(Long id) {
        productoRepository.deleteById(id);
    }

    // Guardar un producto
    public void saveProducto(Producto producto) {
        productoRepository.save(producto);
    }

    // Obtener todas las categorías ordenadas por nombre ascendente
    public List<Categoria> findAllCategoriasSorted() {
        return categoriaRepository.findAll(Sort.by(Sort.Order.asc("nombre")));
    }
}

