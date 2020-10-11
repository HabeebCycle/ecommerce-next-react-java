import Link from "next/link";
import { useState } from "react";
import { connect } from "react-redux";
import { Drawer } from "antd";
import PanelMenu from "../panel/PanelMenu";
import MobileHeaderActions from "./MobileHeaderActions";

const HeaderMobile = (props) => {
  const { auth } = props;
  const [menuDrawer, setMenuDrawer] = useState(false);

  const handleDrawerClose = () => {
    setMenuDrawer(false);
  };

  const handleShowMenuDrawer = () => {
    setMenuDrawer(!menuDrawer);
  };

  const MenuPanel = () => {
    return (
      <Drawer
        className="ps-panel--mobile"
        placement="right"
        closable={false}
        onClose={handleDrawerClose}
        visible={menuDrawer}
      >
        <div className="ps-panel--wrapper">
          <div className="ps-panel__header">
            <h3>Menu</h3>
            <span className="ps-panel__close" onClick={handleDrawerClose}>
              <i className="icon-cross"></i>
            </span>
          </div>
          <div className="ps-panel__content">
            <PanelMenu />
          </div>
        </div>
      </Drawer>
    );
  };

  return (
    <header className="header header--mobile">
      {MenuPanel()}
      <div className="header__top">
        <div className="header__left">
          <span className="region-logo">
            <img
              draggable="false"
              role="img"
              className="emoji"
              alt="🇬🇧"
              src="https://s.w.org/images/core/emoji/13.0.0/svg/1f1ec-1f1e7.svg"
            />{" "}
            &nbsp; <i className="fa fa-phone" aria-hidden="true"></i> +44 2036
            555 777 | <i className="fa fa-envelope-o" aria-hidden="true"></i>{" "}
            info@justtawa.com{" "}
          </span>
        </div>
        <div className="header__right">
          <ul className="navigation__extra">
            {auth.isLoggedIn && (
              <>
                <li>
                  <Link href="/">
                    <a>Dashboard</a>
                  </Link>
                </li>
                {auth.isUserVendor && (
                  <li>
                    <Link href="/">
                      <a>My Store</a>
                    </Link>
                  </li>
                )}
              </>
            )}
            {!auth.isUserVendor && (
              <>
                <li>
                  <Link href="/">
                    <a>
                      <i className="icon-heart" />
                      &nbsp;Wishlist
                    </a>
                  </Link>
                </li>
                <li>
                  <Link href="/">
                    <a>
                      <i className="icon-store" />
                      &nbsp;Stores
                    </a>
                  </Link>
                </li>
              </>
            )}
          </ul>
        </div>
      </div>
      <div className="navigation--mobile">
        <div className="navigation__left">
          <Link href="/">
            <a className="ps-logo">
              <img src="/static/img/logo_light.png" alt="MarketPlace" />
            </a>
          </Link>
        </div>
        <MobileHeaderActions />
      </div>
      <div className="ps-search--mobile">
        <div className="menu-search--mobile">
          <div className="ps-menu--icon">
            <a onClick={handleShowMenuDrawer}>
              <i className="icon-menu"></i>
            </a>
          </div>
          <form className="ps-form--search-mobile" action="/" method="get">
            <div className="form-group--nest">
              <input
                className="form-control"
                type="text"
                placeholder="Search something..."
              />
              <button>
                <i className="icon-magnifier"></i>
              </button>
            </div>
          </form>
        </div>
      </div>
    </header>
  );
};

const state = (appState) => ({
  auth: appState.auth,
});

export default connect(state)(HeaderMobile);
