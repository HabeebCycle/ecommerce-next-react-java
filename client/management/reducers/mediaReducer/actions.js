import * as t from "../../types";

export const getBannersBySlugs = (banners) => ({
  type: t.GET_BANNERS,
  payload: banners,
});

export const getPromotionsBySlugs = (promotions) => ({
  type: t.GET_PROMOTIONS,
  payload: promotions,
});
