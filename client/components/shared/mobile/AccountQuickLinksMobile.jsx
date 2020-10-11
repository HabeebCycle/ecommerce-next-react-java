import Link from "next/link";
import { Dropdown, Menu } from "antd";
import { accountLinks } from "../../../utils/app-settings";
import { setUserLogged } from "../../../management/reducers/authReducer/actions";
import { connect } from "react-redux";

const AccountQuickLinksMobile = (props) => {
  const handleLogout = (e) => {
    e.preventDefault();
    props.dispatch(setUserLogged(false));
  };

  const menu = (
    <Menu>
      {accountLinks.map((link) => (
        <Menu.Item key={link.url}>
          <Link href={link.url}>
            <a>{link.text}</a>
          </Link>
        </Menu.Item>
      ))}

      <Menu.Item>
        <a href="#" onClick={handleLogout}>
          Logout
        </a>
      </Menu.Item>
    </Menu>
  );

  return (
    <Dropdown overlay={menu} placement="bottomLeft">
      <a href="#" className="header__extra ps-user--mobile">
        <i className="icon-user"></i>
      </a>
    </Dropdown>
  );
};

export default connect(null)(AccountQuickLinksMobile);
