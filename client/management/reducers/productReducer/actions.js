import * as t from "../../types";

export const getProductsByKeyword = (keyword) => ({
  type: t.GET_PRODUCTS_BY_KEYWORD,
  payload: keyword,
});
