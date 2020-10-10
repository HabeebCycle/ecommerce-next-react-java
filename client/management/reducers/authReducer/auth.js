import * as t from "../../types";
import { modalSuccess, modalWarning } from "./messages";

const initialState = {
  isLoggedIn: false,
  isUserVendor: false,
};

const Users = (state = initialState, action) => {
  switch (action.type) {
    case t.SET_USER_LOGGED:
      action.payload ? modalSuccess("success") : modalWarning("warning");
      return {
        ...state,
        isLoggedIn: action.payload,
      };

    case t.SET_USER_VENDOR:
      return {
        ...state,
        isUserVendor: action.payload,
      };

    default:
      return state;
  }
};

export default Users;
