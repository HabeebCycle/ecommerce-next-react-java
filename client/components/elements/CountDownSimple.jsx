import React, { Component } from 'react';

import moment from 'moment';

class CountDownSimple extends Component {
    constructor(props) {
        super(props);
        this.state = {
            seconds: null,
            minutes: null,
            hours: null,
            days: null,
        };
    }

    componentDidMount() {
        this.interval = setInterval(() => {
            const { timeTillDate, timeFormat } = this.props;
            const then = moment(timeTillDate, timeFormat);
            const now = moment();
            const countdown = moment(then - now);
            const days = countdown.format('D');
            const hours = countdown.format('HH');
            const minutes = countdown.format('mm');
            const seconds = countdown.format('ss');

            this.setState({ days, hours, minutes, seconds });
        }, 1000);
    }

    componentWillUnmount() {
        if (this.interval) {
            clearInterval(this.interval);
        }
    }
    render() {
        const { days, hours, minutes, seconds } = this.state;
        return (
            <ul className="ps-countdown">
                <li>
                    <span className="days mr-1">{days}</span>
                </li>
                <li>
                    <span className="hours ml-1 mr-1">{hours}</span>
                </li>
                <li>
                    <span className="minutes ml-1 mr-1">{minutes}</span>
                </li>
                <li>
                    <span className="seconds ml-1">{seconds}</span>
                </li>
            </ul>
        );
    }
}

export default CountDownSimple;
