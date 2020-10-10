import * as t from "../../types";

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
