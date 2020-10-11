import { useState } from "react";
import Link from "next/link";
import Router from "next/router";

import store from "../../../management/store";
import ProductResult from "../../elements/products/ProductSearchResult";
import { getProductsByKeyword } from "../../../management/reducers/productReducer/actions";
import { exampleCategories } from "../../../utils/app-settings";

const SearchHeader = () => {
  const [searchPanel, setSearchPanel] = useState(false);
  const [keyword, setKeyword] = useState("");

  const searchByProductName = (word, object) => {
    let matches = [];
    let regexp = new RegExp(word.toLowerCase(), "g");

    object.forEach((product) => {
      if (product.title.toLowerCase().match(regexp)) matches.push(product);
    });

    return matches;
  };

  const handleSearch = (e) => {
    if (e.target.value !== "") {
      const word = e.target.value;
      store.dispatch(getProductsByKeyword(word));
      setSearchPanel(true);
      setKeyword(word);
    } else {
      setSearchPanel(true);
      //this.setState({ searchPanel: false, searchProducts: [] });
      setSearchPanel(false);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    Router.push(`/search?keyword=${keyword}`);
  };

  //const { searchPanel } = this.state;
  const { searchResults } = store.getState().products;

  return (
    <form
      className="ps-form--quick-search"
      method="get"
      action="/"
      onSubmit={handleSubmit}
    >
      <div className="ps-form__categories">
        <select className="form-control search-panel">
          {exampleCategories.map((option) => (
            <option value={option} key={option}>
              {option}
            </option>
          ))}
        </select>
      </div>
      <input
        className="form-control"
        type="text"
        placeholder="I'm shopping for..."
        onChange={handleSearch}
      />
      <button onClick={handleSubmit}>Search</button>
      <div
        className={`ps-panel--search-result${
          searchPanel && searchPanel === true ? " active " : ""
        }`}
      >
        <div className="ps-panel__content">
          {searchResults && searchResults.length > 0 ? (
            searchResults.map((product) => (
              <ProductResult product={product} key={product.id} />
            ))
          ) : (
            <span>Not found! Try with another keyword.</span>
          )}
        </div>
        <div className="ps-panel__footer text-center">
          <Link href="/search">
            <a>See all results</a>
          </Link>
        </div>
      </div>
    </form>
  );
};

export default SearchHeader;
