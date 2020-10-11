import { useState } from "react";
//import AccountQuickLinks from './AccountQuickLinks';
import Link from "next/link";
import AccountQuickLinksMobile from "./AccountQuickLinksMobile";
import { connect } from "react-redux";
/*import { Drawer } from 'antd';
import PanelCartMobile from '../../panel/PanelCartMobile';*/

const MobileHeaderActions = (props) => {
  const [menuDrawer, setMenuDrawer] = useState(false);
  const [cartDrawer, setCartDrawer] = useState(false);
  const [searchDrawer, setSearchDrawer] = useState(false);
  const [categoriesDrawer, setCategoriesDrawer] = useState(false);

  const handleDrawerClose = () => {
    setMenuDrawer(false);
    setCartDrawer(false);
    setSearchDrawer(false);
    setCategoriesDrawer(false);
  };

  const { auth } = props;
  const { cartTotal } = props;
  return (
    <div className="navigation__right">
      <Link href="/account/shopping-cart">
        <a className="header__extra" href="#">
          <i className="icon-bag2 mobile--icons"></i>
          <span>
            <i>{cartTotal ? cartTotal : 0}</i>
          </span>
        </a>
      </Link>

      {auth.isLoggedIn && Boolean(auth.isLoggedIn) === true ? (
        <AccountQuickLinksMobile />
      ) : (
        <div className="header__extra">
          <Link href="/account/login">
            <i className="icon-user mobile--icons"></i>
          </Link>
        </div>
      )}
    </div>
  );
};

const state = (appState) => ({
  auth: appState.auth,
  cart: appState.cart,
});

export default connect(state)(MobileHeaderActions);
