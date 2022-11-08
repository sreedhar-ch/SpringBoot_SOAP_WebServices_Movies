package com.javaspringclub.service;

import com.javaspringclub.entity.MovieEntity;

import java.util.List;

public interface MovieEntityService {

	MovieEntity getEntityById(long id);
	MovieEntity getEntityByTitle(String title);
	List<MovieEntity> getAllEntities();
	MovieEntity addEntity(MovieEntity entity);
	boolean updateEntity(MovieEntity entity);
	boolean deleteEntityById(long id);
}
