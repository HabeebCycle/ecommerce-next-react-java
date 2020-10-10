import * as t from "../../types";

export const getWishlist = () => ({
  type: t.GET_WISHLIST_LIST,
});

export const addToWishList = (item) => ({
  type: t.ADD_TO_WISHLISH_LIST,
  payload: item,
});

export const removeFromWishList = (item) => ({
  type: t.REMOVE_FROM_WISHLIST_LIST,
  payload: item,
});

export const clearCompareList = () => ({
  type: t.CLEAR_WISHLIST_LIST,
});
