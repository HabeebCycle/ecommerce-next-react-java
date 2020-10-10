import { BackTop } from "antd";
import Header from "./header/Header";
import Footer from "./footer/Footer";

function Layout({ children }) {
  return (
    <>
      <Header />
      {children}
      <Footer />

      <BackTop>
        <button className="ps-btn--backtop">
          <i className="icon-arrow-up"></i>
        </button>
      </BackTop>
    </>
  );
}

export default Layout;
