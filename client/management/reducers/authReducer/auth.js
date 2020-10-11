import * as t from "../../types";
import { modalSuccess, modalWarning } from "./messages";

const initialState = {
  isLoggedIn: false,
  isUserVendor: false,
  token: null,
};

const Auth = (state = initialState, action) => {
  switch (action.type) {
    case t.SET_USER_LOGGED:
      action.payload ? modalSuccess("success") : modalWarning("warning");
      return {
        ...state,
        ...{ isLoggedIn: action.payload },
      };

    case t.SET_USER_VENDOR:
      return {
        ...state,
        isUserVendor: action.payload,
      };

    case t.SET_USER_TOKEN:
      return {
        ...state,
        token: action.payload,
      };

    default:
      return state;
  }
};

export default Auth;
