import { useEffect } from "react";
import Link from "next/link";
import Menu from "../../elements/menu/Menu";
import menuData from "../../../public/static/data/menu";
import { stickyHeader } from "../../../utils/common-helpers";
import SearchHeader from "../modules/SearchHeader";
import ElectronicHeaderActions from "../modules/ElectronicHeaderActions";

const HeaderMain = () => {
  useEffect(() => {
    if (process.browser) {
      window.addEventListener("scroll", stickyHeader);
    }
  }, []);
  return (
    <header
      className="header header--standard header--market-place-2"
      id="headerSticky"
    >
      <div className="header__content">
        <div className="container">
          <div className="header__content-left">
            <Link href="/">
              <a className="ps-logo">
                <img
                  className="site-logo"
                  alt="Markeplace Logo"
                  src="https://justtawa.com/ng/wp-content/uploads/2020/08/jt156x52.png"
                />
              </a>
            </Link>
            <div className="menu--product-categories">
              <div className="menu__toggle">
                <i className="icon-menu"></i>
                <span> Shop by Category</span>
              </div>
              <div className="menu__content">
                <Menu
                  data={menuData.product_categories}
                  className="menu--dropdown"
                />
              </div>
            </div>
          </div>
          <div className="header__content-center">
            <SearchHeader />
            <p>
              <Link href="/shop">
                <a>iphone x</a>
              </Link>
              <Link href="/shop">
                <a>virtual</a>
              </Link>
              <Link href="/shop">
                <a>apple</a>
              </Link>
              <Link href="/shop">
                <a>wireless</a>
              </Link>
              <Link href="/shop">
                <a>simple chair</a>
              </Link>
              <Link href="/shop">
                <a>classic watch</a>
              </Link>
              <Link href="/shop">
                <a>macbook</a>
              </Link>
            </p>
          </div>
          <div className="header__content-right">
            <ElectronicHeaderActions />
          </div>
        </div>
      </div>
      <nav className="navigation">
        <div className="container">
          <div className="primary-nav-menu">
            <Menu
              data={menuData.menuPrimary.menu_1}
              className="menu menu--market-2"
            />
          </div>
        </div>
      </nav>
    </header>
  );
};

export default HeaderMain;
