import Link from "next/link";
import { connect } from "react-redux";
import { setUserLogged } from "../../../management/reducers/authReducer/actions";
import { accountLinks } from "../../../utils/app-settings";

const AccountQuickLinks = (props) => {
  const handleLogout = (e) => {
    e.preventDefault();
    props.dispatch(setUserLogged(false));
  };

  //const accountLinks = [];
  const { isLoggedIn } = props;

  if (isLoggedIn === true) {
    return (
      <div className="ps-block--user-account">
        <i className="icon-user"></i>
        <div className="ps-block__content">
          <ul className="ps-list--arrow">
            {accountLinks.map((link) => (
              <li key={link.text}>
                <Link href={link.url}>
                  <a>{link.text}</a>
                </Link>
              </li>
            ))}
            <li className="ps-block__footer">
              <a href="#" onClick={handleLogout}>
                Logout
              </a>
            </li>
          </ul>
        </div>
      </div>
    );
  } else {
    return (
      <div className="ps-block--user-header">
        <div className="ps-block__left">
          <i className="icon-user desktop--icons"></i>
        </div>
        <div className="ps-block__right">
          <Link href="/account/login">
            <a>Login</a>
          </Link>
          <Link href="/account/register">
            <a>Register</a>
          </Link>
        </div>
      </div>
    );
  }
};

export default connect(null)(AccountQuickLinks);
