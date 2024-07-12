/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.literatura.repositorio;

/**
 *
 * @author Miguel
 */




import com.literatura.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface libroRepositorio extends JpaRepository<Libro, Long> {
    List<Libro> findByIdioma(String idioma);
    List<Libro> findByTituloContaining(String titulo);
}


