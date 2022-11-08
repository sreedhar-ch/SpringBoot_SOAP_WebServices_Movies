package com.javaspringclub.repository;

import com.javaspringclub.entity.MovieEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieEntityRepository extends CrudRepository<MovieEntity, Long> {

	MovieEntity findByTitle(String title);
}
