import * as t from "../../types";
import { modalSuccess, modalWarning } from "./messages";
import {
  getLocalStorageData,
  setLocalStorageData,
} from "../../../utils/common-helpers";
import { COMPARE_LOCAL_STORAGE_NAME } from "../../../utils/constants";

const initialState = {
  compareItems: [],
  compareTotal: 0,
};

const Compare = (state = initialState, action) => {
  let localStorage = getLocalStorageData();

  switch (action.type) {
    case t.GET_COMPARE_LIST:
      let currentList = initialState;

      if (localStorage) {
        currentList = localStorage[COMPARE_LOCAL_STORAGE_NAME];
      } else {
        localStorage = {};
        localStorage[COMPARE_LOCAL_STORAGE_NAME] = currentList;
        setLocalStorageData(localStorage);
      }

      return {
        ...state,
        ...{
          compareItems: currentList.compareItems,
          compareTotal: currentList.compareTotal,
        },
      };

    case t.ADD_TO_COMPARE_LIST:
      let item = action.payload;
      currentList = initialState;

      if (localStorage) {
        currentList = localStorage[COMPARE_LOCAL_STORAGE_NAME];

        if (currentList) {
          let existItem = currentList.compareItems.find(
            (product) => product.id === item.id
          );
          if (!existItem) {
            item.quantity = 1;
            currentList.compareItems.push(item);
            currentList.compareTotal++;
            setLocalStorageData(localStorage);
          }
        } else {
          currentList.compareItems.push(item);
          currentList.compareTotal++;
          localStorage[COMPARE_LOCAL_STORAGE_NAME] = currentList;
          setLocalStorageData(localStorage);
        }
      } else {
        currentList.compareItems.push(item);
        currentList.compareTotal++;
        localStorage = {};
        localStorage[COMPARE_LOCAL_STORAGE_NAME] = currentList;
        setLocalStorageData(localStorage);
      }
      modalSuccess("success");

      return {
        ...state,
        ...{
          compareItems: currentList.compareItems,
          compareTotal: currentList.compareTotal,
        },
      };

    case t.REMOVE_FROM_COMPARE_LIST:
      item = action.payload;
      currentList = initialState;

      if (localStorage) {
        currentList = localStorage[COMPARE_LOCAL_STORAGE_NAME];

        if (currentList) {
          let index = currentList.compareItems.indexOf(item);
          currentList.compareTotal = currentList.compareTotal - 1;
          currentList.compareItems.splice(index, 1);
          setLocalStorageData(localStorage);
          modalWarning("warning");
        }
      }

      return {
        ...state,
        ...{
          compareItems: currentList.compareItems,
          compareTotal: currentList.compareTotal,
        },
      };

    case t.CLEAR_COMPARE_LIST:
      if (localStorage) {
        localStorage[COMPARE_LOCAL_STORAGE_NAME] = initialState;
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

export default Compare;
