package com.literatura.servicios;

import com.literatura.modelo.Autor;
import com.literatura.modelo.Libro;
import com.literatura.repositorio.autorRepositorio;
import com.literatura.repositorio.libroRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class libroServicio {
    @Autowired
    private libroRepositorio libroRepository;
    @Autowired
    private autorRepositorio autorRepositorio;

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepository.findByTituloContaining(titulo);
    }

    public List<Libro> listarLibrosPorIdioma(String idioma) {
        return libroRepository.findByIdioma(idioma);
    }

    @Transactional
    public void registrarLibro(Libro libro) {
       
        Autor autor = libro.getAutor();
        if (autor != null && (autor.getId() == null || !autorRepositorio.existsById(autor.getId()))) {
            autor = autorRepositorio.save(autor);
            libro.setAutor(autor);
        }

     
        libroRepository.save(libro);
    }
}

