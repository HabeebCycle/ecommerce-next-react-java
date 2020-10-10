import * as t from "../../types";

export const getCart = () => ({
  type: t.GET_CART,
});

export const addItemToCart = (item) => ({
  type: t.ADD_ITEM_TO_CART,
  payload: item,
});

export const removeItemFromCart = (item) => ({
  type: t.REMOVE_ITEM_FROM_CART,
  payload: item,
});

export const increaseItemCart = (item) => ({
  type: t.INCREASE_CART_ITEM_QTY,
  payload: item,
});

export const decreaseItemCart = (item) => ({
  type: t.DECREASE_CART_ITEM_QTY,
  payload: item,
});

export const updateCart = (item) => ({
  type: t.UPDATE_CART,
  payload: item,
});

export const clearCart = () => ({
  type: t.CLEAR_CART,
});
