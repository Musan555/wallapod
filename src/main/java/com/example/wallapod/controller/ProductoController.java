package com.example.wallapod.controller;

import com.example.wallapod.entity.Categoria;
import com.example.wallapod.entity.FotoProducto;
import com.example.wallapod.entity.Producto;
import com.example.wallapod.repository.ProductoRepository; // Importa el repositorio
import com.example.wallapod.service.FotoProductoService;
import com.example.wallapod.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductoController {

    private final ProductoService productoService;
    private final FotoProductoService fotoProductoService;
    private final ProductoRepository productoRepository; // Declara el repositorio

    @Autowired
    public ProductoController(ProductoService productoService,
                              FotoProductoService fotoProductoService,
                              ProductoRepository productoRepository) {
        this.productoService = productoService;
        this.fotoProductoService = fotoProductoService;
        this.productoRepository = productoRepository; // Inyecta el repositorio
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/productos"; // Redirige a la lista de productos
    }

    @GetMapping("/productos")
    public String findAll(Model model) {
        model.addAttribute("productos", productoService.findAllProductosOrderByFechaCreacionDesc());
        model.addAttribute("categorias", productoService.findAllCategorias());
        return "producto-list";
    }

    @GetMapping("/productos/categoria/{id}")
    public String findAllByCategoria(Model model, @PathVariable Long id) {
        Optional<Categoria> categoriaSeleccionada = productoService.findCategoriaById(id);
        if (categoriaSeleccionada.isPresent()) {
            model.addAttribute("selectedCategoriaId", id);
            model.addAttribute("productos", productoService.findProductosByCategoriaOrderByFechaCreacionDesc(categoriaSeleccionada.get()));
            model.addAttribute("categorias", productoService.findAllCategorias());
            return "producto-list";
        }
        return "redirect:/productos";
    }

    @GetMapping("/productos/del/{id}")
    public String delete(@PathVariable Long id) {
        productoService.deleteProductoById(id);
        return "redirect:/productos";
    }

    @GetMapping("/productos/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        Optional<Producto> producto = productoService.findProductoById(id);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            return "producto-view";
        }
        return "redirect:/productos";
    }

    @GetMapping("/productos/new")
    public String newProducto(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", productoService.findAllCategorias());
        return "producto-new";
    }

    @PostMapping("/productos/new")
    public String crearProducto(@ModelAttribute Producto producto,
                                @RequestParam("archivosFotos[]") MultipartFile[] archivosFotos) {
        // Guardar el producto primero
        Producto productoGuardado = productoRepository.save(producto);

        // Guardar las fotos y asociarlas al producto
        if (archivosFotos != null && archivosFotos.length > 0) {
            List<FotoProducto> fotos = fotoProductoService.guardarFotos(List.of(archivosFotos), productoGuardado);
            productoGuardado.setFotos(fotos); // Asociar las fotos al producto
        }

        // Guardar el producto con sus fotos
        productoRepository.save(productoGuardado);

        return "redirect:/productos";
    }


    @GetMapping("/productos/edit/{id}")
    public String editProducto(@PathVariable Long id, Model model) {
        Optional<Producto> producto = productoService.findProductoById(id);
        if (producto.isPresent()) {
            model.addAttribute("categorias", productoService.findAllCategoriasSorted());
            model.addAttribute("producto", producto.get());
            return "producto-edit";
        }
        return "redirect:/productos";
    }

    @PostMapping("/productos/edit/{id}")
    public String editProductoUpdate(@PathVariable Long id, Producto producto) {
        producto.setId(id);
        productoService.saveProducto(producto);
        return "redirect:/productos";
    }
}
