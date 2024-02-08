package com.peliculas.peliculasBack.Service;

import com.peliculas.peliculasBack.Models.Pelicula;
import com.peliculas.peliculasBack.Models.PeliculaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PeliculaService {
    @Autowired
    private PeliculaRepository repository;

    @Transactional(readOnly = true)
    public CustomResponse<List<Pelicula>> getAll(){
        return new CustomResponse<>(
                this.repository.findAll(),
                false,
                200,
                "OK"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Pelicula> insert(Pelicula movie){
        Optional<Pelicula> exists =this.repository.findByName(movie.getName());
        if(exists.isPresent()){
            return new CustomResponse<>(null,true,400,"El nombre de la peli esta registrado");
        }
        return new CustomResponse<>(
                this.repository.saveAndFlush(movie),
                false,
                200,
                "Película Registrado!"
        );
    }

}
