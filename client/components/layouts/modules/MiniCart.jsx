import { useEffect } from "react";
import Link from "next/link";
import {
  getCart,
  removeItemFromCart,
} from "../../../management/reducers/cartReducer/actions";
import { isStaticData } from "../../../utils/app-settings";
import { baseUrl } from "../../../repositories/Repository";
import { currency } from "../../../utils/app-settings";
import store from "../../../management/store";

const MiniCart = () => {
  useEffect(() => {
    store.dispatch(getCart());
  }, []);

  const handleRemoveCartItem = (product) => {
    store.dispatch(removeItemFromCart(product));
  };

  const { amount, cartTotal, cartItems } = store.getState().cart;
  return (
    <div className="ps-cart--mini">
      <a className="header__extra" href="#">
        <i className="icon-bag2"></i>
        <span>
          <i>{cartTotal ? cartTotal : 0}</i>
        </span>
      </a>
      {cartItems && cartItems.length > 0 ? (
        <div className="ps-cart__content">
          <div className="ps-cart__items">
            {cartItems && cartItems.length > 0
              ? cartItems.map((product) => (
                  <div className="ps-product--cart-mobile" key={product.id}>
                    <div className="ps-product__thumbnail">
                      <Link href="/product/[pid]" as={`/product/${product.id}`}>
                        <a>
                          <img
                            src={
                              isStaticData === false
                                ? `${baseUrl}${product.thumbnail.url}`
                                : product.thumbnail.url
                            }
                            alt="martfury"
                          />
                        </a>
                      </Link>
                    </div>
                    <div className="ps-product__content">
                      <a
                        className="ps-product__remove"
                        onClick={handleRemoveCartItem(product)}
                      >
                        <i className="icon-cross"></i>
                      </a>
                      <Link href="/product/[pid]" as={`/product/${product.id}`}>
                        <a className="ps-product__title">{product.title}</a>
                      </Link>
                      <p>
                        <strong>Sold by:</strong> {product.vendor}
                      </p>
                      <small>
                        {product.quantity} x {currency.symbol}
                        {product.price}
                      </small>
                    </div>
                  </div>
                ))
              : ""}
          </div>
          <div className="ps-cart__footer">
            <h3>
              Sub Total:
              <strong>${amount ? amount : 0}</strong>
            </h3>
            <figure>
              <Link href="/account/shopping-cart">
                <a className="ps-btn">View Cart</a>
              </Link>
              <Link href="/account/checkout">
                <a className="ps-btn">Checkout</a>
              </Link>
            </figure>
          </div>
        </div>
      ) : (
        <div className="ps-cart__content">
          <div className="ps-cart__items">
            <span>No products in cart</span>
          </div>
        </div>
      )}
    </div>
  );
};

export default MiniCart;
