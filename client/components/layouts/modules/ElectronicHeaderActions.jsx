import Link from "next/link";
import store from "../../../management/store";

import MiniCart from "./MiniCart";
import AccountQuickLinks from "./AccountQuickLinks";

const ElectronicHeaderActions = () => {
  const { compare, wishlist, auth } = store.getState();
  return (
    <div className="header__actions">
      <Link href="/account/compare">
        <a className="header__extra">
          <i className="icon-chart-bars"></i>
          <span>
            <i>{compare ? compare.compareTotal : compare.compareTotal}</i>
          </span>
        </a>
      </Link>
      <Link href="/account/wishlist">
        <a className="header__extra">
          <i className="icon-heart"></i>
          <span>
            <i>{wishlist.wishlistTotal}</i>
          </span>
        </a>
      </Link>
      <MiniCart />
      {auth.isLoggedIn && Boolean(auth.isLoggedIn) === true ? (
        <AccountQuickLinks isLoggedIn={true} />
      ) : (
        <AccountQuickLinks isLoggedIn={false} />
      )}
    </div>
  );
};

export default ElectronicHeaderActions;
