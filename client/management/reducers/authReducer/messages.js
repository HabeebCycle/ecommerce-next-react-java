import { notification } from "antd";

export const modalSuccess = (type) => {
  notification[type]({
    message: "Welcome back!",
    description: "You have been successfully logged in!",
  });
};

export const modalWarning = (type) => {
  notification[type]({
    message: "Good bye!",
    description: "Your account has been logged out!",
  });
};
