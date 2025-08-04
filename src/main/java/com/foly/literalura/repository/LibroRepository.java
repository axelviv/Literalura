package com.foly.literalura.repository;

import com.fasterxml.jackson.databind.ser.impl.IndexedStringListSerializer;
import com.foly.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT l FROM Libro l WHERE l.idiomas = :idioma")
    List<Libro> idiomaBuscado(String idioma);

}
