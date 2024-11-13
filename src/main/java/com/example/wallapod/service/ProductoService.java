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

    public List<Producto> findAllProductos() {
        return productoRepository.findAll();
    }

    public List<Categoria> findAllCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> findCategoriaById(Long id) {
        return categoriaRepository.findById(id);
    }

    public List<Producto> findProductosByCategoria(Categoria categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    public void deleteProductoById(Long id) {
        productoRepository.deleteById(id);
    }

    public Optional<Producto> findProductoById(Long id) {
        return productoRepository.findById(id);
    }

    public void saveProducto(Producto producto) {
        productoRepository.save(producto);
    }

    public List<Categoria> findAllCategoriasSorted() {
        return categoriaRepository.findAll(Sort.by("nombre").ascending());
    }
}
