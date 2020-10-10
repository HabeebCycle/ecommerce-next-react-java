import Link from "next/link";
import Rating from "../Rating";
import { formatCurrency } from "../../../utils/product-helper";
import { isStaticData, currency } from "../../../utils/app-settings";
import { baseUrl } from "../../../repositories/Repository";

const ProductResult = (props) => {
  const { product } = props;

  return (
    <div className="ps-product ps-product--wide ps-product--search-result">
      <div className="ps-product__thumbnail">
        <Link href="/product/[pid]" as={`/product/${product.id}`}>
          <a>
            <img
              src={
                isStaticData === true
                  ? product.thumbnail.url
                  : `${baseUrl}${product.thumbnail.url}`
              }
              alt="marketplace"
            />
          </a>
        </Link>
      </div>
      <div className="ps-product__content">
        <Link href="/product/[pid]" as={`/product/${product.id}`}>
          <a className="ps-product__title">{product.title}</a>
        </Link>
        <div className="ps-product__rating">
          <Rating />
          <span>{product.ratingCount}</span>
        </div>
        {product.is_sale === true ? (
          <p className="ps-product__price sale">
            {currency ? currency.symbol : "$"}
            {formatCurrency(product.price)}
            <del className="ml-1">
              {currency ? currency.symbol : "$"}
              {formatCurrency(product.sale_price)}
            </del>
          </p>
        ) : (
          <p className="ps-product__price">
            {currency ? currency.symbol : "$"}
            {formatCurrency(product.price)}
          </p>
        )}
      </div>
    </div>
  );
};

export default ProductResult;
