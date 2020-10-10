import * as t from "../../types";

const initialState = {
  fetchError: null,
  addError: null,
  uploadError: null,
};

const Errors = (state = initialState, action) => {
  switch (action.type) {
    case t.SET_FETCH_ERROR:
      return {
        ...state,
        fetchError: action.payload,
      };

    case t.SET_ADD_ERROR:
      return {
        ...state,
        addError: action.payload,
      };

    case t.SET_UPLOAD_ERROR:
      return {
        ...state,
        uploadError: action.payload,
      };

    default:
      return state;
  }
};

export default Errors;
