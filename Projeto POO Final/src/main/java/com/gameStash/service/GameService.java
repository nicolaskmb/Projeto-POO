package com.gameStash.service;

import com.gameStash.model.Game;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GameService {

	public Game saveGame(Game game);

	public List<Game> getAllGames();

	public Boolean deleteGame(Integer id);

	public Game getGameById(Integer id);

	public Game updateGame(Game game, MultipartFile file);

	public List<Game> getAllActiveGames(String category);

	public List<Game> searchGame(String ch);

	public Page<Game> getAllActiveGamePagination(Integer pageNo, Integer pageSize, String category);

	public Page<Game> searchGamePagination(Integer pageNo, Integer pageSize, String ch);

	public Page<Game> getAllGamesPagination(Integer pageNo, Integer pageSize);

	public Page<Game> searchActiveGamePagination(Integer pageNo, Integer pageSize, String category, String ch);

}
