import React from 'react';
import { connect } from 'react-redux';
import CountDown from '../CountDown';
import { Progress } from 'antd';
import Rating from '../Rating';
import ThumbnailDealHot from '../detail/modules/thumbnail/ThumbnailDealHot';
import Link from 'next/link';
import { formatCurrency } from '../../../utilities/product-helper';

class ProductDealHot extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const { product, currency } = this.props;

        return (
            <div className="ps-product--detail ps-product--hot-deal">
                <div className="ps-product__header">
                    <ThumbnailDealHot product={product} />
                    <div className="ps-product__info">
                        <h5>Investor</h5>
                        <h3 className="ps-product__name">
                            <Link
                                href="/product/[pid]"
                                as={`/product/${product.id}`}>
                                <a>{product.title}</a>
                            </Link>
                        </h3>

                        <div className="ps-product__meta">
                            {product.is_sale === true ? (
                                <h4 className="ps-product__price sale">
                                    <del className="mr-2">
                                        {currency ? currency.symbol : '$'}
                                        {formatCurrency(product.sale_price)}
                                    </del>
                                    {currency ? currency.symbol : '$'}
                                    {formatCurrency(product.price)}
                                </h4>
                            ) : (
                                <h4 className="ps-product__price">
                                    {currency ? currency.symbol : '$'}
                                    {formatCurrency(product.price)}
                                </h4>
                            )}
                            <div className="ps-product__rating">
                                <Rating />
                                <span>(1 review)</span>
                            </div>
                            <div className="ps-product__specification">
                                <p>
                                    Status:
                                    <strong className="in-stock">
                                        In Stock
                                    </strong>
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
    }
}

const mapStateToProps = state => {
    return state.setting;
};

export default connect(mapStateToProps)(ProductDealHot);
