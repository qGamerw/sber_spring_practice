import React, {useState} from "react";
import {ClientsList} from "./Client";
import {ProductList} from "./Product";

export let BasketList = [
    {
        id: 1,
        idProduct: 1,
        idClient: 1,
        amount: 1
    },
    {
        id: 2,
        idProduct: 2,
        idClient: 2,
        amount: 1
    },
    {
        id: 2,
        idProduct: 1,
        idClient: 2,
        amount: 1
    }
]

const GetBasketByClientName = () => {
    const nameClient = document.getElementById("idFind").value;

    const clientIndex = ClientsList.findIndex(item => item.name === nameClient);
    const idClient = ClientsList[clientIndex].id

    return (
        BasketList.filter(item => item.idClient === idClient).map(basket => {
            return (
                <div key={basket.id} className={"client-item"}>

                    <p>Id Product: {basket.idProduct}</p>
                    <p>Id Client: {basket.idClient}</p>
                    <p>Amount: {basket.amount}</p>
                </div>
            );
        })
    );
};

export const deleteBasketById = (id) => {
    BasketList = BasketList.filter(item => item.id !== id);
}

const RemoveBasket = () => {
    const nameProduct = document.getElementById("deleteFindProduct").value
    const nameClient = document.getElementById("deleteFindClient").value

    const productIndex = ProductList.findIndex(item => item.name === nameProduct);
    const clientIndex = ClientsList.findIndex(item => item.name === nameClient);

    if (clientIndex !== -1 && productIndex !== -1){
        const idProduct = ProductList[productIndex].id
        const idClient = ClientsList[clientIndex].id

        const basketIndex = BasketList.findIndex(item => item.idProduct === idProduct
        && item.idClient === idClient);

        const idBasket = BasketList[basketIndex].id

        BasketList = BasketList.filter(item => item.id !== idBasket);
        return true;
    }
    return false;
}

const AddBasket = () => {
    const id = Math.random() * 100;
    const nameClient = document.getElementById("basketNameClient").value
    const nameProduct = document.getElementById("basketNameProduct").value
    const amount = parseInt(document.getElementById("basketAmount").value)

    const clientIndex = ClientsList.findIndex(item => item.name === nameClient);
    const productIndex = ProductList.findIndex(item => item.name === nameProduct);

    if (clientIndex !== -1 && productIndex !== -1) {
        ClientsList[clientIndex].price = amount * ProductList[productIndex].price;
        BasketList.push({id: id, idClient: ClientsList[clientIndex].id, idProduct: ProductList[productIndex].id, amount: amount})
        alert("Продукт добавлен")
    }
}

export const Basket = () => {

    const [isBasketFindByClientId, setIsBasketById] = useState(false);
    const [isBasketDeleteById, setIsBasketDeleteByClientId] = useState(false);

    const selectAllFalse = () => {
        if (isBasketFindByClientId) {
            setIsBasketById(!isBasketFindByClientId);
        }
        if (isBasketDeleteById) {
            setIsBasketDeleteByClientId(!isBasketDeleteById)
        }
    }

    const selectClientById = () => {
        selectAllFalse()
        setIsBasketById(!isBasketFindByClientId);
    }

    return (
        <div>
            <h1>Корзина</h1>
            <h2>Работа с Корзина</h2>
            <button onClick={selectClientById}>Найти по имени</button>
            <input type={"text"} id={"idFind"} placeholder={"Name client"}/><br/><br/>

            <button onClick={RemoveBasket}>Удалить продукт имени </button>
            <input type={"text"} id={"deleteFindProduct"} placeholder={"Name product"}/><br/><br/>
            <input type={"text"} id={"deleteFindClient"} placeholder={"Name client"}/><br/><br/>

            <p>Добавить продукт</p>
            <c>Id Product:</c>
            <input type={"text"} placeholder={"Name Product basket"} id={"basketNameProduct"}/><br/>
            <c>Id Client:</c>
            <input type={"text"} placeholder={"Name Client basket"} id={"basketNameClient"}/><br/>
            <c>Amount:</c>
            <input type={"number"} placeholder={"Amount basket"} id={"basketAmount"}/><br/><br/>

            <button onClick={AddBasket}>Добавить</button>
            <br/>

            <h4>Список корзин:</h4>
            <div>
                {isBasketFindByClientId ? <GetBasketByClientName/> : <p></p>}
            </div>
        </div>);
}