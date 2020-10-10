import * as t from "../../types";

const initialState = {
  allProducts: null,
  singleProduct: null,
  error: false,
  totalProducts: 0,
  categories: null,
  brands: [],
  productsLoading: true,
  productLoading: true,
  searchResults: null,
};

const Products = (state = initialState, action) => {
  switch (action.type) {
    default:
      return state;
  }
};

export default Products;
