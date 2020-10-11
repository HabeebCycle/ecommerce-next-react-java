import { useState } from "react";
import { appSettings } from "../../utils/app-settings";

const SubscribePopup = (props) => {
  const [isSubscribeShow, setIsSubscribeShow] = useState(true);

  const handleCloseSubscribePopup = (e) => {
    e.preventDefault();
    setIsSubscribeShow(false);
  };

  const { active } = props;

  if (isSubscribeShow) {
    return (
      <div className={`ps-popup ${active ? "active" : ""}`} id="subscribe">
        <div
          className="ps-popup__content bg--cover"
          style={{
            backgroundImage: "url('/static/img/subscribe.jpg')",
          }}
        >
          <a
            className="ps-popup__close"
            href="#"
            onClick={handleCloseSubscribePopup}
          >
            <i className="icon-cross"></i>
          </a>
          <form className="ps-form--subscribe-popup" action="/" method="get">
            <div className="ps-form__content">
              <h4>
                Get <strong>25%</strong> Discount
              </h4>
              <p>
                Subscribe to the {appSettings.name} mailing list <br /> to
                receive updates on new arrivals, special offers
                <br /> and our promotions.
              </p>
              <div className="form-group">
                <input
                  className="form-control"
                  type="text"
                  placeholder="Email Address"
                  required
                />
                <button className="ps-btn">Subscribe</button>
              </div>
              <div className="ps-checkbox">
                <input
                  className="form-control"
                  type="checkbox"
                  id="not-show"
                  name="not-show"
                />
                <label htmlFor="not-show">Don't show this popup again</label>
              </div>
            </div>
          </form>
        </div>
      </div>
    );
  } else {
    return "";
  }
};

export default SubscribePopup;
