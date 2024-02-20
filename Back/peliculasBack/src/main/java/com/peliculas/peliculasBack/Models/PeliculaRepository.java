package com.peliculas.peliculasBack.Models;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula,Long> {
    Optional<Pelicula> findByName(String Name);
    List<Pelicula> findByGeneroContainingOrDirectorContaining(String Genero, String Director);
    List<Pelicula> findByNameContaining(String Name);


}
