package com.example.wallapod.service;

import com.example.wallapod.DTO.CategoriaCosteMedioDTO;
import com.example.wallapod.entity.Categoria;
import com.example.wallapod.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    private static final List<String> PERMITTED_TYPES = List.of("image/jpeg", "image/png", "image/gif", "image/avif", "image/webp");
    private static final long MAX_FILE_SIZE = 10000000;
    private static final String UPLOADS_DIRECTORY = "uploads/imagesCategorias";

    public List<CategoriaCosteMedioDTO> obtenerCategoriasConStats() {
        return categoriaRepository.obtenerCategoriasConStats();
    }

    public void eliminarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        Path ruta = Paths.get(UPLOADS_DIRECTORY + File.separator + categoria.getFoto());
        categoriaRepository.deleteById(id);
        try {
            Files.deleteIfExists(ruta);
        } catch (IOException e) {
            throw new RuntimeException("Error al borrar el archivo de la categoría");
        }
    }

    public void guardarCategoria(Categoria categoria, MultipartFile file) {
        validarArchivo(file);
        String nuevoNombreArchivo = generarNombreUnico(file);
        guardarFoto(file, nuevoNombreArchivo);
        categoria.setFoto(nuevoNombreArchivo);
        categoriaRepository.save(categoria);
    }

    public Categoria obtenerCategoriaPorId(Long id) {
        return categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
    }

    public void actualizarCategoria(Long id, Categoria categoria, MultipartFile file) {
        // Actualizar el nombre y otros campos según la categoría
        Categoria categoriaExistente = categoriaRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        // Si el archivo es nuevo, validarlo y guardarlo
        if (file != null && !file.isEmpty()) {
            validarArchivo(file);
            String nuevoNombreArchivo = generarNombreUnico(file);
            guardarFoto(file, nuevoNombreArchivo);
            categoriaExistente.setFoto(nuevoNombreArchivo);
        }

        categoriaExistente.setNombre(categoria.getNombre()); // Usamos el campo 'nombre'
        categoriaExistente.setDescripcion(categoria.getDescripcion()); // También actualizamos la descripción

        categoriaRepository.save(categoriaExistente); // Guardamos la categoría actualizada
    }

    public void validarArchivo(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Archivo no seleccionado");
        }
        if (!PERMITTED_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException("El archivo seleccionado no es una imagen.");
        }
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("Archivo demasiado grande. Sólo se admiten archivos < 10MB");
        }
    }

    public String generarNombreUnico(MultipartFile file) {
        UUID nombreUnico = UUID.randomUUID();
        String extension;
        if (file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()) {
            extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        } else {
            throw new IllegalArgumentException("El archivo seleccionado no es una imagen.");
        }
        return nombreUnico + extension;
    }

    public void guardarFoto(MultipartFile file, String nuevoNombreFoto) {
        Path ruta = Paths.get(UPLOADS_DIRECTORY + File.separator + nuevoNombreFoto);
        try {
            byte[] contenido = file.getBytes();
            Files.write(ruta, contenido);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar archivo", e);
        }
    }
}

