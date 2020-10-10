import { notification } from "antd";

export const modalSuccess = (type) => {
  notification[type]({
    message: "Added to wishlisht!",
    description: "This product has been added to wishlist!",
  });
};

export const modalWarning = (type) => {
  notification[type]({
    message: "Removed from wishlist",
    description: "This product has been removed from wishlist!",
  });
};
