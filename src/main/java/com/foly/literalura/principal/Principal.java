package com.foly.literalura.principal;

import com.foly.literalura.model.*;
import com.foly.literalura.repository.AutorRepository;
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
    private AutorRepository repositorioAutor;

    public Principal(LibroRepository repository, AutorRepository repositoryAutor) {
        this.repositorio = repository;
        this.repositorioAutor = repositoryAutor;
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
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarPorIdioma();
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

            Optional<DatosAutor> datosAutor = libroBuscado.get().autor().stream()
                    .findFirst();
            if (datosAutor.isPresent()){
                Autor autor = new Autor(datosAutor.get());
                repositorioAutor.save(autor);
            }



        } else {
            System.out.println("Libro no encontrado!");
            System.out.println(" ");
        }

    }

    private void listarLibrosRegistrados(){

        List<Libro> libros = repositorio.findAll();
        System.out.println(libros);

    }

    private void listarAutoresRegistrados(){

        List<Autor> autores = repositorioAutor.findAll();
        System.out.println(autores);

    }

    private void listarAutoresVivos(){
        System.out.println("Ingresa el año que quieres buscar:");
        var anio = teclado.nextInt();
        teclado.nextLine();

        List<Autor> autoresEncontrados = repositorioAutor.autoresVivos(anio);

        if (autoresEncontrados.isEmpty()){
            System.out.println("No se encontraron autores vivos en ese año!!");
        }else {
            autoresEncontrados.forEach(a -> System.out.println(a));
        }
    }

    private void listarPorIdioma(){
        System.out.println("Ingresa el idioma de los libros que quieres buscar:");
        var idioma = teclado.nextLine();

        List<Libro> librosEncontrados = repositorio.idiomaBuscado(idioma);

        if (librosEncontrados.isEmpty()){
            System.out.println("No se encontraron libros en ese idioma!!");
        }else {
            librosEncontrados.forEach(l -> System.out.println(l));
        }
    }


}
