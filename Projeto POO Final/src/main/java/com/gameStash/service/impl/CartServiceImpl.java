package com.gameStash.service.impl;

import com.gameStash.model.Cart;
import com.gameStash.model.Game;
import com.gameStash.model.UserDtls;
import com.gameStash.repository.CartRepository;
import com.gameStash.repository.GameRepository;
import com.gameStash.repository.UserRepository;
import com.gameStash.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GameRepository gameRepository;

	@Override
	public Cart saveCart(Integer gameId, Integer userId) {

		UserDtls userDtls = userRepository.findById(userId).get();
		Game game = gameRepository.findById(gameId).get();

		Cart cartStatus = cartRepository.findByGameIdAndUserId(gameId, userId);

		Cart cart = null;

		if (ObjectUtils.isEmpty(cartStatus)) {
			cart = new Cart();
			cart.setGame(game);
			cart.setUser(userDtls);
			cart.setQuantity(1);
			cart.setTotalPrice(game.getPrice());
		} else {
			cart = cartStatus;
			cart.setQuantity(cart.getQuantity() + 1);
			cart.setTotalPrice(cart.getQuantity() * cart.getGame().getPrice());
		}
		Cart saveCart = cartRepository.save(cart);

		return saveCart;
	}

	@Override
	public List<Cart> getCartsByUser(Integer userId) {
		List<Cart> carts = cartRepository.findByUserId(userId);

		Double totalOrderPrice = 0.0;
		List<Cart> updateCarts = new ArrayList<>();
		for (Cart c : carts) {
			Double totalPrice = (c.getGame().getPrice() * c.getQuantity());
			c.setTotalPrice(totalPrice);
			totalOrderPrice = totalOrderPrice + totalPrice;
			c.setTotalOrderPrice(totalOrderPrice);
			updateCarts.add(c);
		}

		return updateCarts;
	}

	@Override
	public Integer getCountCart(Integer userId) {
		Integer countByUserId = cartRepository.countByUserId(userId);
		return countByUserId;
	}

	@Override
	public void updateQuantity(String sy, Integer cid) {

		Cart cart = cartRepository.findById(cid).get();
		int updateQuantity;

		if (sy.equalsIgnoreCase("de")) {
			updateQuantity = cart.getQuantity() - 1;

			if (updateQuantity <= 0) {
				cartRepository.delete(cart);
			} else {
				cart.setQuantity(updateQuantity);
				cartRepository.save(cart);
			}

		} else {
			updateQuantity = cart.getQuantity() + 1;
			cart.setQuantity(updateQuantity);
			cartRepository.save(cart);
		}

	}

}
