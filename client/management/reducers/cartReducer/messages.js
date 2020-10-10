import { notification } from "antd";

export const modalSuccess = (type) => {
  notification[type]({
    message: "Success",
    description: "This product has been added to your cart!",
    duration: 1,
  });
};
export const modalWarning = (type) => {
  notification[type]({
    message: "Remove A Item",
    description: "This product has been removed from your cart!",
    duration: 1,
  });
};
