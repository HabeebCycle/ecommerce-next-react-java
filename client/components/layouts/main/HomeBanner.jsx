import Slider from "react-slick";
import NextArrow from "../../elements/carousel/NextArrow";
import PrevArrow from "../../elements/carousel/PrevArrow";
import { connect } from "react-redux";
import { getItemBySlug } from "../../../utils/product-helper";
import Promotion from "../../elements/media/Promotion";
import BannerItem from "../../elements/media/BannerItem";

const HomeBanner = (props) => {
  const { banners, promotions } = props;

  const carouselSetting = {
    dots: false,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    nextArrow: <NextArrow />,
    prevArrow: <PrevArrow />,
  };

  const bannerData = getItemBySlug(banners, "home_banner");
  const promotionData = getItemBySlug(promotions, "home_promotions");
  let promotion1, promotion2;

  if (promotionData) {
    promotion1 = getItemBySlug(promotionData.items, "main_1");
    promotion2 = getItemBySlug(promotionData.items, "main_2");
  }
  return (
    <div className="ps-home-banner ps-home-banner--1">
      <div className="ps-container">
        <div className="ps-section__left">
          {bannerData !== null ? (
            <Slider {...carouselSetting} className="ps-carousel">
              {bannerData.items.map((item) => (
                <BannerItem source={item} key={item.id} />
              ))}
            </Slider>
          ) : (
            ""
          )}
        </div>
        <div className="ps-section__right">
          <Promotion
            link="/shop"
            image={promotion1 ? promotion1.image : null}
          />
          <Promotion
            link="/shop"
            image={promotion2 ? promotion2.image : null}
          />
        </div>
      </div>
    </div>
  );
};

export default connect((state) => state.media)(HomeBanner);
