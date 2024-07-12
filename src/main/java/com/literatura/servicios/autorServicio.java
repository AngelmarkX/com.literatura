/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.literatura.servicios;

/**
 *
 * @author Miguel
 */




import com.literatura.modelo.Autor;
import com.literatura.repositorio.autorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class autorServicio {
    @Autowired
    private autorRepositorio autorRepository;

    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> listarAutoresVivos() {
        return autorRepository.findByFechaFallecimientoIsNull();
    }

    public List<Autor> listarAutoresVivosEnAnio(int anio) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}


