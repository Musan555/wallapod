package com.example.wallapod.controller;

import ch.qos.logback.core.model.Model;
import com.example.wallapod.entity.Producto;
import com.example.wallapod.repository.FotoProductoRepository;
import com.example.wallapod.repository.ProductoRepository;
import com.example.wallapod.service.FotoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
public class FotoProductoController {

    @Autowired
    private FotoProductoRepository fotoProductoRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private FotoProductoService fotoProductoService;

    // Eliminar foto del producto
    @GetMapping("productos/{id1}/fotos/{id2}/delete")
    public String deleteFoto(@PathVariable("id1") Long idProducto,
                             @PathVariable("id2") Long idFoto) {
        // Buscar el producto por id
        Optional<Producto> productoOptional = productoRepository.findById(idProducto);

        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();

            // Llamar al servicio para eliminar la foto del producto
            fotoProductoService.removeFoto(idFoto, producto);

            // Redirigir al formulario de edición del producto después de eliminar la foto
            return "redirect:/productos/edit/" + idProducto;
        } else {
            // Si no encontramos el producto, redirigimos a la lista de productos con un mensaje de error
            return "redirect:/productos?error=ProductoNoEncontrado";
        }
    }


    @PostMapping("/productos/edit/{id}/addfoto")
    public String addFoto(@PathVariable("id") Long idProducto, Model model,
                          @RequestParam(value = "archivoFoto") MultipartFile archivoFoto) {
        Optional<Producto> productoOptional = productoRepository.findById(idProducto);
        if (productoOptional.isPresent()) {
            fotoProductoService.addFoto(archivoFoto, productoOptional.get());
        }
        return "redirect:/productos/edit/" + idProducto;
    }
}
