package com.gameStash.repository;

import com.gameStash.model.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer> {

	List<Game> findByIsActiveTrue();

	Page<Game> findByIsActiveTrue(Pageable pageable);

	List<Game> findByCategory(String category);

	List<Game> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String ch, String ch2);

	Page<Game> findByCategory(Pageable pageable, String category);

	Page<Game> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String ch, String ch2,
			Pageable pageable);

	Page<Game> findByisActiveTrueAndTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String ch, String ch2,
			Pageable pageable);
}
