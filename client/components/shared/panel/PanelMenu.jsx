import { useState } from "react";

import { Menu } from "antd";
import { menuPrimary } from "../../../public/static/data/menu";
import Link from "next/link";

const { SubMenu } = Menu;

const PanelMenu = () => {
  const [openKeys, setOpenKeys] = useState([]);

  const rootSubmenuKeys = ["sub1", "sub2", "sub4"];

  const onOpenChange = (keys) => {
    const latestOpenKey = keys.find((k) => openKeys.indexOf(k) === -1);
    if (rootSubmenuKeys.indexOf(latestOpenKey) === -1) {
      setOpenKeys(keys);
    } else {
      const ok = latestOpenKey ? [latestOpenKey] : [];
      setOpenKeys(ok);
    }
  };

  return (
    <Menu
      mode="inline"
      openKeys={openKeys}
      onOpenChange={onOpenChange}
      className="menu--mobile-2"
    >
      {menuPrimary.menu_1.map((item) => {
        if (item.subMenu) {
          return (
            <SubMenu
              key={item.text}
              title={
                <Link href={item.url}>
                  <a>{item.text}</a>
                </Link>
              }
            >
              {item.subMenu.map((subItem) => (
                <Menu.Item key={subItem.text}>
                  <Link href={subItem.url}>
                    <a>{subItem.text}</a>
                  </Link>
                </Menu.Item>
              ))}
            </SubMenu>
          );
        } else if (item.megaContent) {
          return (
            <SubMenu
              key={item.text}
              title={
                <Link href={item.url}>
                  <a>{item.text}</a>
                </Link>
              }
            >
              {item.megaContent.map((megaItem) => (
                <SubMenu
                  key={megaItem.heading}
                  title={<span>{megaItem.heading}</span>}
                >
                  {megaItem.megaItems.map((megaSubItem) => (
                    <Menu.Item key={megaSubItem.text}>
                      <Link href={item.url}>
                        <a>{megaSubItem.text}</a>
                      </Link>
                    </Menu.Item>
                  ))}
                </SubMenu>
              ))}
            </SubMenu>
          );
        } else {
          return (
            <Menu.Item key={item.text}>
              {item.type === "dynamic" ? (
                <Link
                  href={`${item.url}/[pid]`}
                  as={`${item.url}/${item.endPoint}`}
                >
                  l<a>{item.text}</a>
                </Link>
              ) : (
                <Link href={item.url} as={item.alias}>
                  <a>{item.text}</a>
                </Link>
              )}
            </Menu.Item>
          );
        }
      })}
    </Menu>
  );
};

export default PanelMenu;
