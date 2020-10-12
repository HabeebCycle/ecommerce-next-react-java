import * as t from "../../types";

const initialState = {
  banners: [],
  promotions: [],
};

const Media = (state = initialState, action) => {
  switch (action.type) {
    case t.GET_BANNERS:
      return {
        ...state,
        banners: action.payload,
      };

    case t.GET_PROMOTIONS:
      return {
        ...state,
        promotions: action.payload,
      };

    default:
      return state;
  }
};

export default Media;
