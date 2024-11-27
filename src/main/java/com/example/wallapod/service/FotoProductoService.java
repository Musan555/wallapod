package com.example.wallapod.service;

import com.example.wallapod.entity.FotoProducto;
import com.example.wallapod.entity.Producto;
import com.example.wallapod.repository.FotoProductoRepository;
import com.example.wallapod.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FotoProductoService {

    private static final List<String> PERMITTED_TYPES = List.of("image/jpeg", "image/png", "image/gif", "image/avif", "image/webp");
    private static final long MAX_FILE_SIZE = 10000000; // 10MB
    private static final String UPLOADS_DIRECTORY = "uploads/imagesProductos";

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    FotoProductoRepository fotoProductoRepository;

    // Guardar una lista de fotos al producto
    public List<FotoProducto> guardarFotos(List<MultipartFile> fotos, Producto producto) {
        List<FotoProducto> listaFotos = new ArrayList<>();
        for (MultipartFile foto : fotos) {
            if (!foto.isEmpty()) {
                validarArchivo(foto);
                String nombreFoto = generarNombreUnico(foto);
                guardarArchivo(foto, nombreFoto);

                FotoProducto fotoProducto = FotoProducto.builder()
                        .nombre(nombreFoto)
                        .producto(producto)
                        .build();

                listaFotos.add(fotoProducto);
                fotoProductoRepository.save(fotoProducto); // Guarda la foto en la base de datos
                producto.getFotos().add(fotoProducto);  // Añadimos la foto a la lista de fotos del producto
            }
        }
        productoRepository.save(producto); // Guardamos el producto con las fotos asociadas
        return listaFotos;
    }

    // Añadir una foto a un producto
    public void addFoto(MultipartFile foto, Producto producto) {
        if (!foto.isEmpty()) {
            validarArchivo(foto);
            String nombreFoto = generarNombreUnico(foto);
            guardarArchivo(foto, nombreFoto);

            FotoProducto fotoProducto = FotoProducto.builder()
                    .nombre(nombreFoto)
                    .producto(producto)
                    .build();

            producto.getFotos().add(fotoProducto); // Añadimos la foto a la lista de fotos del producto
            fotoProductoRepository.save(fotoProducto); // Guardamos la foto en la base de datos
            productoRepository.save(producto); // Guardamos el producto actualizado con la nueva foto
        }
    }

    // Validar el archivo de imagen
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

    // Generar un nombre único para la imagen
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

    // Guardar el archivo físicamente en el servidor
    public void guardarArchivo(MultipartFile file, String nuevoNombreFoto) {
        Path ruta = Paths.get(UPLOADS_DIRECTORY + File.separator + nuevoNombreFoto);
        try {
            byte[] contenido = file.getBytes();
            Files.write(ruta, contenido);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar archivo", e);
        }
    }

    // Eliminar una foto de un producto
    public void removeFoto(Long idFoto, Producto producto) {
        Optional<FotoProducto> fotoProductoOptional = fotoProductoRepository.findById(idFoto);
        if (fotoProductoOptional.isPresent()) {
            FotoProducto fotoProducto = fotoProductoOptional.get();

            // Eliminamos la foto de la lista del producto
            producto.getFotos().remove(fotoProducto);

            // Ahora eliminamos la foto solo si está asociada a un solo producto
            if (fotoProducto.getProducto() != null) { // La foto está asociada a un único producto
                // Eliminamos el archivo físicamente
                Path archivoFoto = Paths.get(UPLOADS_DIRECTORY + File.separator + fotoProducto.getNombre());
                try {
                    Files.deleteIfExists(archivoFoto);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // Eliminamos la foto de la base de datos
                fotoProductoRepository.delete(fotoProducto);
            }

            // Guardamos el producto actualizado sin la foto
            productoRepository.save(producto);
        }
    }
}




