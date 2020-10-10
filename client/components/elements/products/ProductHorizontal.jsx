import React, { Component } from 'react';
import Link from 'next/link';
import { Rate } from 'antd';
import { connect } from 'react-redux';
import Rating from '../Rating';
import { formatCurrency } from '../../../utilities/product-helper';
import { baseUrl } from '../../../repositories/Repository';
import { isStaticData } from '../../../utilities/app-settings';
class ProductHorizontal extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        const { product, currency } = this.props;
        let thumbnail;
        if (isStaticData === false) {
            thumbnail = `${baseUrl}${product.thumbnail.url}`;
        } else {
            thumbnail = product.thumbnail.url;
        }
        return (
            <div className="ps-product--horizontal">
                <div className="ps-product__thumbnail">
                    <Link href="/shop">
                        <a>
                            <img src={thumbnail} alt="martfury" />
                        </a>
                    </Link>
                </div>
                <div className="ps-product__content">
                    <Link href="/product/[pid]" as={`/product/${product.id}`}>
                        <a className="ps-product__title">{product.title}</a>
                    </Link>
                    <div className="ps-product__rating">
                        <Rating />
                    </div>
                    {product.is_sale === true ? (
                        <p className="ps-product__price sale">
                            {currency ? currency.symbol : '$'}
                            {formatCurrency(product.price)}
                            <del className="ml-2">
                                {currency ? currency.symbol : '$'}
                                {product.sale_price}
                            </del>
                        </p>
                    ) : (
                        <p className="ps-product__price">
                            {currency ? currency.symbol : '$'}
                            {formatCurrency(product.price)}
                        </p>
                    )}
                </div>
            </div>
        );
    }
}

const mapStateToProps = state => {
    return state.setting;
};
export default connect(mapStateToProps)(ProductHorizontal);
