import { combineReducers } from "redux";
import errors from "./errorReducer/errors";
import auth from "./authReducer/auth";
import products from "./productReducer/products";
import wishlist from "./wishlistReducer/wishlist";
import cart from "./cartReducer/carts";
import compare from "./compareReducer/compares";

export default combineReducers({
  errors,
  auth,
  products,
  wishlist,
  cart,
  compare,
});
