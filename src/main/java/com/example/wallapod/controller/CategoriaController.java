package com.example.wallapod.controller;

import com.example.wallapod.DTO.CategoriaCosteMedioDTO;
import com.example.wallapod.entity.Categoria;
import com.example.wallapod.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/categorias")
    public String categoria(Model model) {
        List<CategoriaCosteMedioDTO> categoriasConStats = categoriaService.obtenerCategoriasConStats();
        model.addAttribute("categorias", categoriasConStats);
        return "categoria-list";
    }

    @GetMapping("/categoria/delete/{id}")
    public String borrarCategoria(@PathVariable("id") Long id) {
        categoriaService.eliminarCategoria(id);
        return "redirect:/categorias";
    }

    @GetMapping("/categorias/new")
    public String addCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categoria-new";
    }

    @PostMapping("/categorias/new")
    public String addCategoriaInsert(@ModelAttribute("categoria") Categoria categoria,
                                     @RequestParam("file") MultipartFile file,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {
        try {
            categoriaService.guardarCategoria(categoria, file);
        } catch (IllegalArgumentException e) {
            model.addAttribute("mensaje", e.getMessage());
            return "categoria-new";
        }
        return "redirect:/categorias";
    }

    // RUTA PARA EDITAR
    @GetMapping("/categoria/edit/{id}")
    public String editarCategoria(@PathVariable("id") Long id, Model model) {
        Categoria categoria = categoriaService.obtenerCategoriaPorId(id);
        model.addAttribute("categoria", categoria);
        return "categoria-edit"; // Debes crear una vista categoria-edit.html
    }

    @PostMapping("/categoria/edit/{id}")
    public String editarCategoriaSubmit(@PathVariable("id") Long id,
                                        @ModelAttribute("categoria") Categoria categoria,
                                        @RequestParam("file") MultipartFile file,
                                        RedirectAttributes redirectAttributes,
                                        Model model) {
        try {
            categoriaService.actualizarCategoria(id, categoria, file);
        } catch (IllegalArgumentException e) {
            model.addAttribute("mensaje", e.getMessage());
            return "categoria-edit"; // Si hay un error, regresamos a la vista de edición
        }
        return "redirect:/categorias";
    }
}
