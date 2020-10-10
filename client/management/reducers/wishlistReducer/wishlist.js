import * as t from "../../types";
import { modalSuccess, modalWarning } from "./messages";
import {
  getLocalStorageData,
  setLocalStorageData,
} from "../../../utils/common-helpers";
import { WISHLIST_LOCAL_STORAGE_NAME } from "../../../utils/constants";

const initialState = {
  wishlistItems: [],
  wishlistTotal: 0,
};

const Wishlist = (state = initialState, action) => {
  let localStorage = getLocalStorageData();

  switch (action.type) {
    case t.GET_WISHLIST_LIST:
      let currentList = initialState;

      if (localStorage) {
        currentList = localStorage[WISHLIST_LOCAL_STORAGE_NAME];
      } else {
        localStorage = {};
        localStorage[WISHLIST_LOCAL_STORAGE_NAME] = currentList;
        setLocalStorageData(localStorage);
      }

      return {
        ...state,
        ...{
          wishlistItems: currentList.wishlistItems,
          wishlistTotal: currentList.wishlistTotal,
        },
      };

    case t.ADD_TO_WISHLISH_LIST:
      let item = action.payload;
      currentList = initialState;

      if (localStorage) {
        currentList = localStorage[WISHLIST_LOCAL_STORAGE_NAME];

        if (currentList) {
          let existItem = currentList.wishlistItems.find(
            (product) => product.id === item.id
          );
          if (!existItem) {
            currentList.wishlistItems.push(item);
            currentList.wishlistTotal++;
            setLocalStorageData(localStorage);
          }
        } else {
          currentList.wishlistItems.push(item);
          currentList.wishlistTotal++;
          localStorage[WISHLIST_LOCAL_STORAGE_NAME] = currentList;
          setLocalStorageData(localStorage);
        }
      } else {
        currentList.wishlistItems.push(item);
        currentList.wishlistTotal++;
        localStorage = {};
        localStorage[WISHLIST_LOCAL_STORAGE_NAME] = currentList;
        setLocalStorageData(localStorage);
      }
      modalSuccess("success");

      return {
        ...state,
        ...{
          wishlistItems: currentList.wishlistItems,
          wishlistTotal: currentList.wishlistTotal,
        },
      };

    case t.REMOVE_FROM_WISHLIST_LIST:
      item = action.payload;
      currentList = initialState;

      if (localStorage) {
        currentList = localStorage[WISHLIST_LOCAL_STORAGE_NAME];

        if (currentList) {
          let index = currentList.wishlistItems.indexOf(item);
          currentList.wishlistTotal = currentList.wishlistTotal - 1;
          currentList.wishlistItems.splice(index, 1);
          setLocalStorageData(localStorage);
          modalWarning("warning");
        }
      }

      return {
        ...state,
        ...{
          wishlistItems: currentList.wishlistItems,
          wishlistTotal: currentList.wishlistTotal,
        },
      };

    case t.CLEAR_COMPARE_LIST:
      if (localStorage) {
        localStorage[WISHLIST_LOCAL_STORAGE_NAME] = initialState;
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

export default Wishlist;
