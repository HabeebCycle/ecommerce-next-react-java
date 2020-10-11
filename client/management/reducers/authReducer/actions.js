import * as t from "../../types";

export const setUserLogged = (isLogged) => ({
  type: t.SET_USER_LOGGED,
  payload: isLogged,
});

export const setUserVendor = (isVendor) => ({
  type: t.SET_USER_VENDOR,
  payload: isVendor,
});

export const setUserToken = (token) => ({
  type: t.SET_USER_TOKEN,
  payload: token,
});
