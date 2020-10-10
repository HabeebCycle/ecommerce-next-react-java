import axios from 'axios';
/*const baseDomain = 'http://45.76.97.89:3000';*/
const baseDomain = 'http://localhost:1337';
const authorization_prefix = 'Bearer ';

export const customHeaders = {
    Accept: 'application/json',
    /* Authorization: authorization_prefix + token || undefined*/
};

export const baseUrl = `${baseDomain}`;

export default axios.create({
    baseUrl,
    headers: customHeaders,
});

export const serializeQuery = query => {
    return Object.keys(query)
        .map(
            key =>
                `${encodeURIComponent(key)}=${encodeURIComponent(query[key])}`
        )
        .join('&');
};
