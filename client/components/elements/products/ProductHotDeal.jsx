import React from 'react';

import CountDown from '../CountDown';
import { Rate, Progress } from 'antd';
import ThumbnailHotDeal from '../detail/modules/thumbnail/ThumbnailHotDeal';
import Rating from '../Rating';

const ProductHotDeal = () => (
    <div className="ps-product--detail ps-product--hot-deal">
        <div className="ps-product__header">
            <ThumbnailHotDeal />
            <div className="ps-product__info">
                <h5>Investor</h5>
                <h3 className="ps-product__name">
                    Anderson Composites - Custom Hood
                </h3>
                <div className="ps-product__meta">
                    <h4 className="ps-product__price sale">
                        $36.78 <del> $56.99</del>
                    </h4>
                    <div className="ps-product__rating">
                        <Rating />
                        <span>(1 review)</span>
                    </div>
                    <div className="ps-product__specification">
                        <p>
                            Status:
                            <strong className="in-stock"> In Stock</strong>
                        </p>
                    </div>
                </div>
                <div className="ps-product__expires">
                    <p>Expires In</p>
                    <CountDown
                        timeTillDate="12 31 2020, 6:00 am"
                        timeFormat="MM DD YYYY, h:mm a"
                    />
                </div>
                <div className="ps-product__processs-bar">
                    <Progress percent={60} showInfo={false} />
                    <p>
                        <strong>4/79</strong> Sold
                    </p>
                </div>
            </div>
        </div>
    </div>
);

export default ProductHotDeal;
