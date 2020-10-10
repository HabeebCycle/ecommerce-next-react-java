import { notification } from "antd";

export const modalSuccess = (type) => {
  notification[type]({
    message: "Added to compare list!",
    description: "This product has been added to the compare list!",
  });
};

export const modalWarning = (type) => {
  notification[type]({
    message: "Removed from compare list",
    description: "This product has been removed from the compare list!",
  });
};
