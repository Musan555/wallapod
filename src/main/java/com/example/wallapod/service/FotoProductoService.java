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
    private static final long MAX_FILE_SIZE = 10000000;
    private static final String UPLOADS_DIRECTORY = "uploads/imagesProductos";

    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    FotoProductoRepository fotoProductoRepository;

    public List<FotoProducto> guardarFotos(List<MultipartFile> fotos, Producto producto) {

        List<FotoProducto> listaFotos = new ArrayList<>();

        for (MultipartFile foto : fotos) {
            if (!foto.isEmpty()) {
                validarArchivo(foto);
                String nombreFoto = generarNombreUnico(foto);
                guardarArchivo(foto, nombreFoto);

                FotoProducto fotoProducto = FotoProducto.builder()
                        .nombre(nombreFoto)
                        .producto(producto).build();

                listaFotos.add(fotoProducto);
            }
        }

        producto.setFotos(listaFotos);
        return listaFotos;
    }

    public void addFoto(MultipartFile foto, Producto producto) {

        if (!foto.isEmpty()) {
            validarArchivo(foto);
            String nombreFoto = generarNombreUnico(foto);
            guardarArchivo(foto, nombreFoto);

            FotoProducto fotoProducto = FotoProducto.builder()
                    .nombre(nombreFoto)
                    .producto(producto).build();


            producto.getFotos().add(fotoProducto);
            productoRepository.save(producto);
        }
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

    public void guardarArchivo(MultipartFile file, String nuevoNombreFoto) {
        Path ruta = Paths.get(UPLOADS_DIRECTORY + File.separator + nuevoNombreFoto);
        //Movemos el archivo a la carpeta y guardamos su nombre en el objeto catgoría
        try {
            byte[] contenido = file.getBytes();
            Files.write(ruta, contenido);
        } catch (
                IOException e) {
            throw new RuntimeException("Error al guardar archivo", e);
        }
    }

    public void deleteFoto(Long idFoto) {
        Optional<FotoProducto> fotoProductoOptional = fotoProductoRepository.findById(idFoto);
        if(fotoProductoOptional.isPresent()){
            FotoProducto fotoProducto = fotoProductoOptional.get();
            Path archivoFoto = Paths.get(fotoProducto.getNombre());
            try {
                Files.deleteIfExists(archivoFoto);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fotoProductoRepository.deleteById(idFoto);
        }
    }

}
