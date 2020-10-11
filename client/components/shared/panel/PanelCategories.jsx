import { useState } from "react";
import { Menu } from "antd";
import Link from "next/link";
import categories from "../../../public/static/data/static-categories.json";

const { SubMenu } = Menu;

const PanelCategories = () => {
  const [openKeys, setOpenKeys] = useState(["sub1"]);

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
    <Menu mode="inline" openKeys={openKeys} onOpenChange={onOpenChange}>
      {categories.map((category) => (
        <Menu.Item key={category.id}>
          <a href={`/shop?category=${category.slug}`}>{category.name}</a>
        </Menu.Item>
      ))}
    </Menu>
  );
};

export default PanelCategories;
