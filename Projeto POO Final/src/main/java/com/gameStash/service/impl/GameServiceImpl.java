package com.gameStash.service.impl;

import com.gameStash.model.Game;
import com.gameStash.repository.GameRepository;
import com.gameStash.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

	@Autowired
	private GameRepository gameRepository;

	@Override
	public Game saveGame(Game game) {
		return gameRepository.save(game);
	}

	@Override
	public List<Game> getAllGames() {
		return gameRepository.findAll();
	}

	@Override
	public Page<Game> getAllGamesPagination(Integer pageNo, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return gameRepository.findAll(pageable);
	}

	@Override
	public Boolean deleteGame(Integer id) {
		Game game = gameRepository.findById(id).orElse(null);

		if (!ObjectUtils.isEmpty(game)) {
			gameRepository.delete(game);
			return true;
		}
		return false;
	}

	@Override
	public Game getGameById(Integer id) {
		Game game = gameRepository.findById(id).orElse(null);
		return game;
	}

	@Override
	public Game updateGame(Game game, MultipartFile image) {

		Game dbGame = getGameById(game.getId());

		String imageName = image.isEmpty() ? dbGame.getImage() : image.getOriginalFilename();

		dbGame.setTitle(game.getTitle());
		dbGame.setCategory(game.getCategory());
		dbGame.setPrice(game.getPrice());
		dbGame.setImage(imageName);
		dbGame.setIsActive(game.getIsActive());

		Game updateGame = gameRepository.save(dbGame);

		if (!ObjectUtils.isEmpty(updateGame)) {

			if (!image.isEmpty()) {

				try {
					File saveFile = new ClassPathResource("static/img").getFile();

					Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "game_img" + File.separator
							+ image.getOriginalFilename());
					Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return game;
		}
		return null;
	}

	@Override
	public List<Game> getAllActiveGames(String category) {
		List<Game> games = null;
		if (ObjectUtils.isEmpty(category)) {
			games = gameRepository.findByIsActiveTrue();
		} else {
			games = gameRepository.findByCategory(category);
		}

		return games;
	}

	@Override
	public List<Game> searchGame(String ch) {
		return gameRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(ch, ch);
	}

	@Override
	public Page<Game> searchGamePagination(Integer pageNo, Integer pageSize, String ch) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		return gameRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(ch, ch, pageable);
	}

	@Override
	public Page<Game> getAllActiveGamePagination(Integer pageNo, Integer pageSize, String category) {

		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Game> pageGame = null;

		if (ObjectUtils.isEmpty(category)) {
			pageGame = gameRepository.findByIsActiveTrue(pageable);
		} else {
			pageGame = gameRepository.findByCategory(pageable, category);
		}
		return pageGame;
	}

	@Override
	public Page<Game> searchActiveGamePagination(Integer pageNo, Integer pageSize, String category, String ch) {

		Page<Game> pageGame = null;
		Pageable pageable = PageRequest.of(pageNo, pageSize);

		pageGame = gameRepository.findByisActiveTrueAndTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(ch,
				ch, pageable);
		return pageGame;
	}

}
