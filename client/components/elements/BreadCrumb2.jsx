import React from 'react';
import Link from 'next/link';

const BreadCrumb = ({ breacrumb }) => {
    return (
        <div className="ps-breadcrumb 2">
            <ul className="breadcrumb">
                {breacrumb.map(item => {
                    if (!item.url) {
                        return <li key={item.text}>{item.text}</li>;
                    } else {
                        return (
                            <li key={item.text}>
                                <Link href={item.url}>
                                    <a>{item.text}</a>
                                </Link>
                            </li>
                        );
                    }
                })}
            </ul>
        </div>
    );
};

export default BreadCrumb;
