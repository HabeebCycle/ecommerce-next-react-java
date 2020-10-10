import * as t from "../types";

export const setUserProfiles = (users) => ({
  type: t.SET_USER_PROFILES,
  payload: users,
});

export const addUserProfile = (user) => ({
  type: t.ADD_USER_PROFILE,
  payload: user,
});

export const getUserProfile = (userId) => ({
  type: t.GET_USER_PROFILE,
  payload: userId,
});

export const setUserProfile = (user) => ({
  type: t.SET_USER_PROFILE,
  payload: user,
});

export const deleteUserProfile = (userId) => ({
  type: t.DELETE_USER_PROFILE,
  payload: userId,
});

export const updateUserProfile = (user) => ({
  type: t.UPDATE_USER_PROFILE,
  payload: user,
});

export const setFetchError = (err) => ({
  type: t.SET_FETCH_ERROR,
  payload: err,
});

export const setAddError = (err) => ({
  type: t.SET_ADD_ERROR,
  payload: err,
});

export const setUploadError = (err) => ({
  type: t.SET_UPLOAD_ERROR,
  payload: err,
});
