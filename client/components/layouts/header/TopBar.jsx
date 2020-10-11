import Link from "next/link";
import { connect } from "react-redux";

const TopBar = (props) => {
  const { auth } = props;

  return (
    <div id="topbar" className="topbar">
      <div className="top-container">
        <div className="topbar-row row">
          <div className="topbar-left topbar-sidebar col-xs-12 col-sm-12 col-md-5 hidden-sm hidden-xs">
            <div className="widget">
              <span className="region-logo">
                <img
                  draggable="false"
                  role="img"
                  className="emoji"
                  alt="🇬🇧"
                  src="https://s.w.org/images/core/emoji/13.0.0/svg/1f1ec-1f1e7.svg"
                />{" "}
                &nbsp; <i className="fa fa-phone" aria-hidden="true"></i> +44
                2036 555 777 |{" "}
                <i className="fa fa-envelope-o" aria-hidden="true"></i>{" "}
                info@justtawa.com{" "}
              </span>
            </div>
          </div>
          <div className="topbar-right topbar-sidebar col-xs-12 col-sm-12 col-md-7 hidden-sm hidden-xs">
            <div className="widget">
              <div className="topbar-menu">
                <ul className="menu-list">
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
                  <li>
                    <Link href="/">
                      <a>Basket</a>
                    </Link>
                  </li>
                  <li>
                    <Link href="/">
                      <a>Wishlist</a>
                    </Link>
                  </li>
                  <li>
                    <Link href="/">
                      <a>Stores</a>
                    </Link>
                  </li>
                  <li>
                    <Link href="/">
                      <a>Track Orders</a>
                    </Link>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

const state = (appState) => ({
  auth: appState.auth,
});

export default connect(state)(TopBar);
