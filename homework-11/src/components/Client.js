import React, {useState} from "react";

export let ClientsList = [
    {
        id: 1,
        name: "Unanimous",
        email: "ad.Unanimous@Unanim.ad",
        idCard: 1111,
        username: "admin",
        password: "admin",
        price: 100
    },
    {
        id: 2,
        name: "Unanimous2",
        email: "ad.Unanimous@Unanim.",
        idCard: 1111,
        username: "admin",
        password: "admin",
        price: 100
    }
]

const ClientAll = () => {
    return (
        ClientsList.map(client => {
            return (
                <div key={client.id} className={"client-item"}>
                    <h4>Name: {client.name}</h4>
                    <p>Email: {client.email}</p>
                    <p>IdCard: {client.idCard}</p>
                    <p>Price: {client.price}</p>
                </div>
            );
        })
    );
}

const GetClientByName = () => {
    const name = document.getElementById("nameFind").value;

    return (
        ClientsList.filter(item => item.name === name).map(client => {
            return (
                <div key={client.id} className={"client-item"}>
                    <h4>Name: {client.name}</h4>
                    <p>Email: {client.email}</p>
                    <p>IdCard: {client.idCard}</p>
                    <p>Price: {client.price}</p>
                </div>
            );
        })
    );
};

export const RemoveClient = () => {
    const name = document.getElementById("deleteFind").value

    ClientsList = ClientsList.filter(product => product.name !== name);
}

const AddClient = () => {
    const idClient = Math.random() * 100;
    const nameClient = document.getElementById("clientName").value
    const emailClient = document.getElementById("clientEmail").value
    const idCardClient = parseInt(document.getElementById("clientIdCard").value)
    const usernameClient = document.getElementById("clientUsername").value
    const passwordClient = document.getElementById("clientPassword").value

    const clientIndex = ClientsList.findIndex(item => item.name === nameClient);

    if(clientIndex === -1){
        ClientsList.push({
            id: idClient, name: nameClient, email: emailClient,
            idCard: idCardClient, username: usernameClient, password: passwordClient,
            price: 0
        })
    }
}

export const Clients = () => {

    const [isClientFindAll, setIsClientFindAll] = useState(false);
    const [isClientFindByName, setIsClientByName] = useState(false);
    const [isClientDeleteById, setIsClientDeleteById] = useState(false);

    const selectAllFalse = () => {
        if (isClientFindAll) {
            setIsClientFindAll(!isClientFindAll);
        }
        if (isClientFindByName) {
            setIsClientByName(!isClientFindByName)
        }
        if (isClientDeleteById) {
            setIsClientDeleteById(!isClientDeleteById)
        }
    }

    const selectClientFindAll = () => {
        selectAllFalse()
        setIsClientFindAll(!isClientFindAll);
    }

    const selectClientByName = () => {
        selectAllFalse()
        setIsClientByName(!isClientFindByName);
    }

    return (
        <div>
            <h1>Клиенты</h1>
            <h2>Работа с клиентом</h2>
            <button onClick={selectClientFindAll}>Показать все клиентов</button>
            <br/><br/>

            <button onClick={selectClientByName}>Найти по имени</button>
            <input type={"text"} id={"nameFind"} placeholder={"Name client"}/><br/><br/>

            <button onClick={RemoveClient}>Удалить клиента по имени</button>
            <input type={"text"} id={"deleteFind"} placeholder={"Name client"}/><br/><br/>

            <p>Добавить клиента</p>
            <c>Name:</c>
            <input type={"text"} placeholder={"Name client"} id={"clientName"}/><br/>
            <c>Email:</c>
            <input type={"text"} placeholder={"Email client"} id={"clientEmail"}/><br/>
            <c>IdCard:</c>
            <input type={"number"} placeholder={"IdCard client"} id={"clientIdCard"}/><br/>
            <c>Username:</c>
            <input type={"text"} placeholder={"Username client"} id={"clientUsername"}/><br/>
            <c>Password:</c>
            <input type={"text"} placeholder={"Password client"} id={"clientPassword"}/><br/><br/>
            <button onClick={AddClient}>Добавить</button>
            <br/>

            <h4>Список клиентов:</h4>
            <div>
                {isClientFindAll ? <ClientAll/> : <p></p>}
                {isClientFindByName ? <GetClientByName/> : <p></p>}
            </div>
        </div>);
}