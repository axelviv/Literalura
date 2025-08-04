package com.foly.literalura.repository;

import com.foly.literalura.model.Autor;
import com.foly.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}
