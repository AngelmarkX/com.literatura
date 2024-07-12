/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.literatura.cliente;

/**
 *
 * @author Miguel
 */


import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class gutendexCliente {
    private final RestTemplate restTemplate = new RestTemplate();

    public String buscarLibroPorTitulo(String titulo) {
        String url = "https://gutendex.com/books?search=" + titulo;
        return restTemplate.getForObject(url, String.class);
    }
}
