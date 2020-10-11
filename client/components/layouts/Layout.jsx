import { BackTop } from "antd";
import store from "../../management/store";
import Header from "./header/Header";
import Footer from "./footer/Footer";

function Layout({ children }) {
  return (
    <div className="layout--default">
      <Header />
      {children}
      <Footer />
      <div id="loader-wrapper">
        <div className="loader-section section-left"></div>
        <div className="loader-section section-right"></div>
      </div>
      <BackTop>
        <button className="ps-btn--backtop">
          <i className="icon-arrow-up"></i>
        </button>
      </BackTop>
    </div>
  );
}

export default Layout;
