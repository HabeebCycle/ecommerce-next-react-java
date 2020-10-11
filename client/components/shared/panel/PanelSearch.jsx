import { useState } from "react";
import { getProductsByKeyword } from "../../../management/reducers/productReducer/actions";
import ProductResult from "../../elements/products/ProductSearchResult";
import { connect } from "react-redux";

const PanelSearch = (props) => {
  const [searchPanel, setSearchPanel] = useState(false);
  const [searchProducts, setSearchProducts] = useState([]);
  const [keyword, setKeyword] = useState("");

  const searchByProductName = (word, object) => {
    let matches = [];
    let regexp = new RegExp(word.toLowerCase(), "g");

    object.forEach((product) => {
      if (product.title.toLowerCase().match(regexp)) matches.push(product);
    });

    return matches;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const word = keyword;
    Router.push(`/search?keyword=${word}`);
  };

  const handleSearch = (e) => {
    if (e.target.value !== "") {
      const keyword = e.target.value;
      props.dispatch(getProductsByKeyword(keyword));
      setSearchPanel(true);
      setKeyword(e.target.value);
    } else {
      setSearchPanel(false);
      setSearchProducts([]);
    }
  };

  const { searchResults } = props;
  return (
    <div className="ps-panel__search-results">
      <form className="ps-form--search-mobile" action="/" method="get">
        <div className="form-group--nest">
          <input
            className="form-control"
            type="text"
            placeholder="I'm shopping for..."
            onChange={handleSearch}
          />
          <button>
            <i className="icon-magnifier"></i>
          </button>
        </div>
      </form>
      {searchResults &&
        searchResults.map((product) => (
          <ProductResult product={product} key={product.id} />
        ))}
    </div>
  );
};

export default connect((state) => state.products)(PanelSearch);
