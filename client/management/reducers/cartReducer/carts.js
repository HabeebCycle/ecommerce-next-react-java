import * as t from "../../types";
import { modalSuccess, modalWarning } from "./messages";
import {
  getLocalStorageData,
  setLocalStorageData,
} from "../../../utils/common-helpers";
import { CART_LOCAL_STORAGE_NAME } from "../../../utils/constants";
import { calculateAmount } from "../../../utils/product-helper";

const initialState = {
  cartItems: [],
  amount: 0,
  cartTotal: 0,
};

const Cart = (state = initialState, action) => {
  let localStorage = getLocalStorageData();
  //let currentCart = initialState;

  switch (action.type) {
    case t.GET_CART:
      let currentCart = initialState;

      if (localStorage) {
        currentCart = localStorage[CART_LOCAL_STORAGE_NAME];
      } else {
        localStorage = {};
        localStorage[CART_LOCAL_STORAGE_NAME] = currentCart;
        setLocalStorageData(localStorage);
      }

      return {
        ...state,
        ...{
          cartItems: currentCart.cartItems,
          amount: currentCart.amount,
          cartTotal: currentCart.cartTotal,
        },
      };

    case t.ADD_ITEM_TO_CART:
      let item = action.payload;
      currentCart = initialState;

      if (!item.quantity) {
        item.quantity = 1;
      }

      if (localStorage) {
        currentCart = localStorage[CART_LOCAL_STORAGE_NAME];

        if (currentCart) {
          let existItem = currentCart.cartItems.find(
            (product) => product.id === item.id
          );
          if (existItem) {
            existItem.quantity += item.quantity;
          } else {
            currentCart.cartItems.push(item);
          }
          currentCart.amount = calculateAmount(currentCart.cartItems);
          currentCart.cartTotal++;

          setLocalStorageData(localStorage);
        } else {
          currentCart.cartItems.push(item);
          currentCart.amount = calculateAmount(item);
          currentCart.cartTotal = item.quantity;
          localStorage[CART_LOCAL_STORAGE_NAME] = currentCart;

          setLocalStorageData(localStorage);
        }
      } else {
        currentCart.cartItems.push(item);
        currentCart.amount = calculateAmount(item);
        currentCart.cartTotal = item.quantity;
        localStorage = {};
        localStorage[CART_LOCAL_STORAGE_NAME] = currentCart;

        setLocalStorageData(localStorage);
      }

      modalSuccess("success");

      return {
        ...state,
        ...{
          cartItems: currentCart.cartItems,
          amount: currentCart.amount,
          cartTotal: currentCart.cartTotal,
        },
      };

    case t.REMOVE_ITEM_FROM_CART:
      item = action.payload;
      currentCart = initialState;

      if (localStorage) {
        currentCart = localStorage[CART_LOCAL_STORAGE_NAME];

        if (currentCart) {
          let index = currentCart.cartItems.findIndex(
            (product) => product.id === item.id
          );

          currentCart.cartTotal = currentCart.cartTotal - item.quantity;
          currentCart.cartItems.splice(index, 1);
          currentCart.amount = calculateAmount(currentCart.cartItems);

          if (currentCart.cartItems.length === 0) {
            currentCart = initialState;
          }

          setLocalStorageData(localStorage);

          modalWarning("warning");
        }
      }

      return {
        ...state,
        ...{
          cartItems: currentCart.cartItems,
          amount: currentCart.amount,
          cartTotal: currentCart.cartTotal,
        },
      };

    case t.INCREASE_CART_ITEM_QTY:
      item = action.payload;
      currentCart = initialState;

      if (localStorage) {
        currentCart = localStorage[CART_LOCAL_STORAGE_NAME];

        if (currentCart) {
          let selectedItem = currentCart.cartItems.find(
            (product) => product.id === item.id
          );

          if (selectedItem) {
            selectedItem.quantity += item.quantity;
            currentCart.cartTotal += item.quantity;
            currentCart.amount = calculateAmount(currentCart.cartItems);
          }

          setLocalStorageData(localStorage);
        }
      }

      return {
        ...state,
        ...{
          cartItems: currentCart.cartItems,
          amount: currentCart.amount,
          cartTotal: currentCart.cartTotal,
        },
      };

    case t.DECREASE_CART_ITEM_QTY:
      item = action.payload;
      currentCart = initialState;

      if (localStorage) {
        currentCart = localStorage[CART_LOCAL_STORAGE_NAME];

        if (currentCart) {
          let selectedItem = currentCart.cartItems.find(
            (product) => product.id === item.id
          );

          if (selectedItem) {
            selectedItem.quantity -= item.quantity;
            currentCart.cartTotal -= item.quantity;
            currentCart.amount = calculateAmount(currentCart.cartItems);
          }

          setLocalStorageData(localStorage);
        }
      }

      return {
        ...state,
        ...{
          cartItems: currentCart.cartItems,
          amount: currentCart.amount,
          cartTotal: currentCart.cartTotal,
        },
      };

    case t.UPDATE_CART:
      item = action.payload;
      currentCart = initialState;

      if (localStorage) {
        currentCart = localStorage[CART_LOCAL_STORAGE_NAME];

        if (currentCart) {
          let selectedItem = currentCart.cartItems.find(
            (product) => product.id === item.id
          );

          if (selectedItem) {
            let qty = selectedItem.quantity - item.quantity;
            selectedItem.quantity = item.quantity;
            currentCart.cartTotal -= qty;
            currentCart.amount = calculateAmount(currentCart.cartItems);
          }

          setLocalStorageData(localStorage);
        }
      }

      return {
        ...state,
        ...{
          cartItems: currentCart.cartItems,
          amount: currentCart.amount,
          cartTotal: currentCart.cartTotal,
        },
      };

    case t.CLEAR_CART:
      if (localStorage) {
        localStorage[CART_LOCAL_STORAGE_NAME] = initialState;
        setLocalStorageData(localStorage);
      }
      return {
        ...state,
        ...{ initialState },
      };

    default:
      return state;
  }
};

export default Cart;
