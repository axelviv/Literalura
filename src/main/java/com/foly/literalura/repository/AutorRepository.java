package com.foly.literalura.repository;

import com.foly.literalura.model.Autor;
import com.foly.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :anio AND (a.fechaFallecimiento >= :anio)")
    List<Autor> autoresVivos(Integer anio);
}
