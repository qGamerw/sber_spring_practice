import {Products} from "./components/Product";
import {Clients} from "./components/Client";

import './App.css';
import React, {useState} from "react";
import {Basket} from "./components/Basket";
import {Pays} from "./components/Payment";


function App() {
    const [isClient, setIsClient] = useState(false);
    const [isProduct, setIsProduct] = useState(false);
    const [isBasket, setIsBasket] = useState(false);
    const [isPayment, setIsPayment] = useState(false);




    const selectClient = () => {
        setIsClient(!isClient);
    }

    const selectProduct = () => {
        setIsProduct(!isProduct);
    }

    const selectBasket = () => {
        setIsBasket(!isBasket);
    }

    const selectPayment = () => {
        setIsPayment(!isPayment);
    }

    return (
        <div className={"Main"}>
            <button onClick={selectClient}>Взаимодействие с клиентом: {isClient ? "Выбрано" : "Не выбрано"}</button>
            {isClient ? <Clients/> : <p></p>}

            <button onClick={selectProduct}>Взаимодействие с продуктом: {isProduct ? "Выбрано" : "Не выбрано"}</button>
            {isProduct ? <Products/> : <p></p>}

            <button onClick={selectBasket}>Взаимодействие с корзиной: {isBasket ? "Выбрано" : "Не выбрано"}</button>
            {isBasket ? <Basket/> : <p></p>}

            <button onClick={selectPayment}>Взаимодействие с оплата: {isPayment ? "Выбрано" : "Не выбрано"}</button>
            {isPayment ? <Pays/> : <p></p>}
        </div>
    );
}

export default App;
