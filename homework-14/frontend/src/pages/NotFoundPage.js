import {Button, message, Result} from "antd";
import {Link} from "react-router-dom";
import React from "react";

export const NotFoundPage = () => {

    const handleButtonClick = () => {
        setTimeout(() => {
            message.success('Cum и киберпространство!', 3);
        }, 100);
    };
    handleButtonClick();

    return (
        <Result
            status="404"
            title="Приветствую, Gachi-Warrior! Ты проник на территорию настоящего Gachi-мира - место, где сходятся cum и киберпространство! "
            subTitle="p.s. Ты прошел один из повторяемых испытаний: Найти cum в киберпространсве."
            extra={<Button type="primary"><Link to="/products">Назад на главную</Link></Button>}
        />
    )
}