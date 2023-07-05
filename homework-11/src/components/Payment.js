import React from "react";
import {BasketList, deleteBasketById} from "./Basket";
import {ClientsList} from "./Client";
import {ProductList} from "./Product";

const pay = () => {
    const nameClient = document.getElementById("payClientName").value;
    const clientIndex = ClientsList.findIndex(item => item.name === nameClient);

    if (clientIndex === -1) {
        return false;
    }

    const idClient = ClientsList[clientIndex].id;
    const basketIndex = BasketList.findIndex(item => item.idClient === idClient);

    if (basketIndex === -1) {
        return false;
    }

    const idProduct = BasketList[basketIndex].idProduct;
    const productIndex = ProductList.findIndex(item => item.id === idProduct);

    ClientsList[clientIndex].price -= BasketList[basketIndex].amount * ProductList[productIndex].price;

    deleteBasketById(BasketList[basketIndex].id)

    alert("Удалили");
}

export const Pays = () => {
    return (
        <div>
            <h1>Оплата</h1>
            <h2>Работа с оплатой</h2>
            <p>Оплатить продукты</p>
            <input type={"text"} id={"payClientName"} placeholder={"Name client"}/><br/><br/>
            <button onClick={pay}>Оплатить</button>
        </div>);
}