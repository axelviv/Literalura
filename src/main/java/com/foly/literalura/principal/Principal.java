package com.foly.literalura.principal;

import com.foly.literalura.model.Datos;
import com.foly.literalura.model.DatosLibros;
import com.foly.literalura.model.Libro;
import com.foly.literalura.repository.LibroRepository;
import com.foly.literalura.service.ConsumoAPI;
import com.foly.literalura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorio;

    public Principal(LibroRepository repository) {
        this.repositorio = repository;
    }

    public void muestraMenu(){
        var opcion = -1;

        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en determinado año
                    5 - Listar libros por idioma
                    
                    0 - Salir
                    """;

            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibro(){
        System.out.println("Escribe el nombre del libro que deseas buscar:");
        var titulo = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + titulo.replace(" ", "+"));
        var datos = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datos.resultados().stream()
                .filter(l -> l.titulo().toLowerCase().contains(titulo.toLowerCase()))
                .findFirst();
        if(libroBuscado.isPresent()){
            System.out.println("Libro encontrado: ");
            System.out.println(libroBuscado.get());
            System.out.println(" ");

            Libro libro = new Libro(libroBuscado.get());
            repositorio.save(libro);

        } else {
            System.out.println("Libro no encontrado!");
            System.out.println(" ");
        }

    }

    private void guardarEnBD(){

    }


}
