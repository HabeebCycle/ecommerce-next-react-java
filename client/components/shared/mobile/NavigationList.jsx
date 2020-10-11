import { useState } from "react";
import { Drawer } from "antd";
import PanelMenu from "../panel/PanelMenu";
import PanelCartMobile from "../panel/PanelCartMobile";
import PanelSearch from "../panel/PanelSearch";
import PanelCategories from "../panel/PanelCategories";
import Link from "next/link";
import { isStaticData } from "../../../utils/app-settings";
import { baseUrl } from "../../../repositories/Repository";

const NavigationList = (props) => {
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

  const handleShowMenuDrawer = () => {
    setMenuDrawer(!menuDrawer);
    setCartDrawer(false);
    setSearchDrawer(false);
    setCategoriesDrawer(false);
  };

  const handleShowCartDrawer = () => {
    setMenuDrawer(false);
    setCartDrawer(!cartDrawer);
    setSearchDrawer(false);
    setCategoriesDrawer(false);
  };

  const handleShowSearchDrawer = () => {
    setMenuDrawer(false);
    setCartDrawer(false);
    setSearchDrawer(!searchDrawer);
    setCategoriesDrawer(false);
  };

  const handleShowCategoriesDrawer = () => {
    setMenuDrawer(false);
    setCartDrawer(false);
    setSearchDrawer(false);
    setCategoriesDrawer(!categoriesDrawer);
  };

  return (
    <div className="navigation--list">
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
      <Drawer
        className="ps-panel--mobile"
        placement="right"
        closable={false}
        onClose={handleDrawerClose}
        visible={cartDrawer}
      >
        <div className="ps-panel--wrapper">
          <div className="ps-panel__header">
            <h3>Shopping Cart</h3>
            <span className="ps-panel__close" onClick={handleDrawerClose}>
              <i className="icon-cross"></i>
            </span>
          </div>
          <div className="ps-panel__content">
            <PanelCartMobile />
          </div>
        </div>
      </Drawer>
      <Drawer
        className="ps-panel--mobile"
        placement="right"
        closable={false}
        onClose={handleDrawerClose}
        visible={searchDrawer}
      >
        <div className="ps-panel--wrapper">
          <div className="ps-panel__header">
            <h3>Search</h3>
            <span className="ps-panel__close" onClick={handleDrawerClose}>
              <i className="icon-cross"></i>
            </span>
          </div>
          <div className="ps-panel__content">
            <PanelSearch />
          </div>
        </div>
      </Drawer>
      <Drawer
        className="ps-panel--mobile"
        placement="right"
        closable={false}
        onClose={handleDrawerClose}
        visible={categoriesDrawer}
      >
        <div className="ps-panel--wrapper">
          <div className="ps-panel__header">
            <h3>Categories</h3>
            <span className="ps-panel__close" onClick={handleDrawerClose}>
              <i className="icon-cross"></i>
            </span>
          </div>
          <div className="ps-panel__content">
            <PanelCategories />
          </div>
        </div>
      </Drawer>
      <div className="navigation__content">
        <a
          className={`navigation__item ${menuDrawer === true ? "active" : ""}`}
          onClick={handleShowMenuDrawer}
        >
          <i className="icon-menu"></i>
          <span> Menu</span>
        </a>
        <a
          className={`navigation__item ${
            categoriesDrawer === true ? "active" : ""
          }`}
          onClick={handleShowCategoriesDrawer}
        >
          <i className="icon-list4"></i>
          <span> Categories</span>
        </a>
        <a
          className={`navigation__item ${
            searchDrawer === true ? "active" : ""
          }`}
          onClick={handleShowSearchDrawer}
        >
          <i className="icon-magnifier"></i>
          <span> Search</span>
        </a>
        <a
          className={`navigation__item ${cartDrawer === true ? "active" : ""}`}
          onClick={handleShowCartDrawer}
        >
          <i className="icon-bag2"></i>
          <span> Cart</span>
        </a>
      </div>
    </div>
  );
};

export default NavigationList;
