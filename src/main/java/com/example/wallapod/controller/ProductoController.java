package com.example.wallapod.controller;

import com.example.wallapod.entity.Categoria;
import com.example.wallapod.entity.Producto;
import com.example.wallapod.service.FotoProductoService;
import com.example.wallapod.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductoController {

    private final ProductoService productoService;
    private final FotoProductoService fotoProductoService;

    @Autowired
    public ProductoController(ProductoService productoService, FotoProductoService fotoProductoService) {
        this.productoService = productoService;
        this.fotoProductoService = fotoProductoService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/productos"; // Redirige a la lista de productos
    }


    @GetMapping("/productos")
    public String findAll(Model model) {
        model.addAttribute("productos", productoService.findAllProductos());
        model.addAttribute("categorias", productoService.findAllCategorias());
        model.addAttribute("titulo", "Titulo de página");
        return "producto-list";
    }

    @GetMapping("/productos/categoria/{id}")
    public String findAllByCategoria(Model model, @PathVariable Long id) {
        if (id.equals(-1L)) {
            return "redirect:/productos";
        }
        Optional<Categoria> categoriaSeleccionada = productoService.findCategoriaById(id);
        if (categoriaSeleccionada.isPresent()) {
            model.addAttribute("selectedCategoriaId", id);
            model.addAttribute("productos", productoService.findProductosByCategoria(categoriaSeleccionada.get()));
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
    public String newProductoInsert(Model model, @Valid Producto producto,
                                    BindingResult bindingResult,
                                    @RequestParam(value = "archivosFotos", required = false) List<MultipartFile> fotos) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categorias", productoService.findAllCategoriasSorted());
            return "producto-new";
        }

        try {
            // Guardar las fotos asociadas al producto
            if (fotos != null && !fotos.isEmpty()) {
                fotoProductoService.guardarFotos(fotos, producto); // Guardar fotos y asociarlas al producto
            }
        } catch (IllegalArgumentException ex) {
            model.addAttribute("categorias", productoService.findAllCategoriasSorted());
            model.addAttribute("mensaje", ex.getMessage());
            return "producto-new";
        }

        // Guardar el producto en la base de datos
        productoService.saveProducto(producto);

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
