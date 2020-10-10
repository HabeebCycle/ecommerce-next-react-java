import * as t from "../../types";

export const getCompareList = () => ({
  type: t.GET_COMPARE_LIST,
});

export const addToCompareList = (item) => ({
  type: t.ADD_TO_COMPARE_LIST,
  payload: item,
});

export const removeFromCompareList = (item) => ({
  type: t.REMOVE_FROM_COMPARE_LIST,
  payload: item,
});

export const clearCompareList = () => ({
  type: t.CLEAR_COMPARE_LIST,
});
