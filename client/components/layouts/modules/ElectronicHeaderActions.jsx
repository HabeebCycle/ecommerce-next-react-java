import Link from "next/link";

import MiniCart from "./MiniCart";
import AccountQuickLinks from "./AccountQuickLinks";
import { connect } from "react-redux";

const ElectronicHeaderActions = (props) => {
  const { compare, wishlist, auth } = props;
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
      <AccountQuickLinks isLoggedIn={auth.isLoggedIn} />
    </div>
  );
};

const state = (appState) => ({
  auth: appState.auth,
  compare: appState.compare,
  wishlist: appState.wishlist,
});

export default connect(state)(ElectronicHeaderActions);
