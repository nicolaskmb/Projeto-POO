package com.gameStash.service;

import com.gameStash.model.Cart;

import java.util.List;

public interface CartService {

	public Cart saveCart(Integer gameId, Integer userId);

	public List<Cart> getCartsByUser(Integer userId);
	
	public Integer getCountCart(Integer userId);

	public void updateQuantity(String sy, Integer cid);

}
