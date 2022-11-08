package com.javaspringclub.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name = "movies")
public class MovieEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long movieId;
    private String title;
    private String category;

    protected String spaceship;

    public MovieEntity() {

    }

    public MovieEntity(String title, String category) {
        this.title = title;
        this.category = category;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getSpaceship() {
        return spaceship;
    }

    public void setSpaceship(String spaceship) {
        this.spaceship = spaceship;
    }

}
