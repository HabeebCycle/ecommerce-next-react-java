import React from 'react';
import Link from 'next/link';

const DemoItem = ({ data }) => (
    <div className="ps-block--demo">
        <div className="ps-block__thumbnail">
            <Link href={data.link} key={data.text}>
                <a>
                    <img src={data.image} alt={data.text} />
                </a>
            </Link>
        </div>

        <div className="ps-block__content">
            <Link href={data.link}>
                <a className="ps-block__title">{data.text}</a>
            </Link>
        </div>
    </div>
);

export default DemoItem;
