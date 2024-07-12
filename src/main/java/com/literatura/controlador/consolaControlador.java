package com.literatura.controlador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.literatura.cliente.gutendexCliente;
import com.literatura.modelo.Autor;
import com.literatura.modelo.Libro;
import com.literatura.servicios.autorServicio;
import com.literatura.servicios.libroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
public class consolaControlador {

    @Autowired
    private libroServicio libroService;

    @Autowired
    private autorServicio autorService;

    @Autowired
    private gutendexCliente gutendexClient;

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Buscar libro por título y registrarlo");
            System.out.println("2. Listar libros registrados");
            System.out.println("3. Listar autores registrados");
            System.out.println("4. Listar autores vivos en un año determinado");
            System.out.println("5. Listar libros por idioma");
            System.out.println("6. Salir");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el título del libro: ");
                    String titulo = scanner.nextLine();
                    String resultadoJson = gutendexClient.buscarLibroPorTitulo(titulo);
                    System.out.println("Resultado de la búsqueda: ");
                    mostrarResultados(resultadoJson);           
                    break;
                case 2:
                    List<Libro> libros = libroService.listarLibros();
                    System.out.println("Libros registrados:");
                    mostrarLibros(libros);
                    break;
                case 3:
                    List<Autor> autores = autorService.listarAutores();
                    System.out.println("Autores registrados:");
                    mostrarAutores(autores);
                    break;
                case 4:
                    System.out.print("Ingrese el año: ");
                    int anio = scanner.nextInt();
                    scanner.nextLine();
                    List<Autor> autoresVivos = autorService.listarAutoresVivosEnAnio(anio);
                    System.out.println("Autores vivos en el año " + anio + ":");
                    mostrarAutores(autoresVivos);
                    break;
                case 5:
                    System.out.print("Ingrese el idioma: ");
                    String idioma = scanner.nextLine();
                    List<Libro> librosPorIdioma = libroService.listarLibrosPorIdioma(idioma);
                    System.out.println("Libros en el idioma " + idioma + ":");
                    mostrarLibros(librosPorIdioma);
                    break;
                case 6:
                    System.out.println("Saliendo...");
                    System.exit(0);
                default:
                    System.out.println("Opción inválida, intente nuevamente.");
            }
        }
    }

    private void mostrarLibros(List<Libro> libros) {
        for (Libro libro : libros) {
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autores:");
            Autor autor = libro.getAutor();
            if (autor != null) {
                System.out.println("- " + autor.getNombre());
            }
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Formato: " + libro.getFormato());
            System.out.println("-------------");
        }
    }

    private void mostrarAutores(List<Autor> autores) {
        for (Autor autor : autores) {
            System.out.println(autor.getNombre());
        }
    }

    private void mostrarResultados(String resultadoJson) {
        JsonObject jsonObject = JsonParser.parseString(resultadoJson).getAsJsonObject();
        JsonArray resultados = jsonObject.getAsJsonArray("results");

        List<Libro> librosEncontrados = new ArrayList<>();
        for (JsonElement jsonElement : resultados) {
            JsonObject libroJson = jsonElement.getAsJsonObject();
            String titulo = libroJson.has("title") && !libroJson.get("title").isJsonNull() ? libroJson.get("title").getAsString() : "Título no disponible";

            JsonArray autoresJson = libroJson.has("authors") && !libroJson.get("authors").isJsonNull() ? libroJson.getAsJsonArray("authors") : new JsonArray();
            List<Autor> autores = new ArrayList<>();
            for (JsonElement autorElement : autoresJson) {
                JsonObject autorJson = autorElement.getAsJsonObject();
                String nombreAutor = autorJson.has("name") && !autorJson.get("name").isJsonNull() ? autorJson.get("name").getAsString() : "Autor no disponible";
                int birthYear = autorJson.has("birth_year") && !autorJson.get("birth_year").isJsonNull() ? autorJson.get("birth_year").getAsInt() : -1;
                int deathYear = autorJson.has("death_year") && !autorJson.get("death_year").isJsonNull() ? autorJson.get("death_year").getAsInt() : -1;

                Autor autor = new Autor();
                autor.setNombre(nombreAutor);
                autor.setFechaNacimiento(null); 
                autor.setFechaFallecimiento(null); 
                autores.add(autor);
            }

            if (!autores.isEmpty()) {
                Libro libro = new Libro();
                libro.setTitulo(titulo);
                libro.setAutor(autores.get(0)); 
                libro.setIdioma("Inglés"); 
                libro.setFormato("Libro impreso"); 
                librosEncontrados.add(libro);

                libroService.registrarLibro(libro);
            }
        }

        mostrarLibros(librosEncontrados);
    }
}
