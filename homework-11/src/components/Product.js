import {useState} from "react";

export let ProductList = [
    {
        id: 1,
        name: "Яблоко",
        price: 100,
        amount: 15
    },
    {
        id: 2,
        name: "Груша",
        price: 100,
        amount: 10
    }
]

const ProductAll = () => {
    return (
        ProductList.map(product => {
            return (
                <div key={product.id} className={"client-item"}>
                    <h4>Name: {product.name}</h4>
                    <p>Price: {product.price}</p>
                    <p>Amount: {product.amount}</p>
                </div>
            );
        })
    );
}

const GetProductByName = () => {
    const name = document.getElementById("nameFind").value;

    return (
        ProductList.filter(item => item.name === name) .map(product => {
            return (
                <div key={product.id} className={"client-item"}>
                    <h4>Name: {product.name}</h4>
                    <p>Price: {product.price}</p>
                    <p>Amount: {product.amount}</p>
                </div>
            );
        })
    );
};

const RemoveProduct = () => {
    const name = document.getElementById("deleteFind").value

    ProductList = ProductList.filter(product => product.name !== name);
}

const AddProduct = () => {
    const idProduct = Math.random() * 100;
    const nameProduct = document.getElementById("productName").value
    const priceProduct = parseInt(document.getElementById("productPrice").value)
    const amountProduct = parseInt(document.getElementById("productAmount").value)

    const productIndex = ProductList.findIndex(item => item.id === idProduct);

    if (productIndex === -1){
        ProductList.push({id: idProduct, name: nameProduct, price: priceProduct, amount: amountProduct})
    }
}

const EditProduct = () => {
    const nameProduct = document.getElementById("productNameEdit").value
    const priceProduct = parseInt(document.getElementById("productPriceEdit").value)
    const amountProduct = parseInt(document.getElementById("productAmountEdit").value)

    const productIndex = ProductList.findIndex(item => item.name === nameProduct);

    if (productIndex !== -1){
        ProductList[productIndex].name = nameProduct
        ProductList[productIndex].price = priceProduct
        ProductList[productIndex].amount = amountProduct
    }
}

export const Products = () => {
    const [isProductFindAll, setIsProductFindAll] = useState(false);
    const [isProductFindByName, setIsProductByName] = useState(false)
    const [isProductDeleteById, setIsProductDeleteById] = useState(false);

    const selectAllFalse = () => {
        if (isProductFindAll){
            setIsProductFindAll(!isProductFindAll);
        }
        if (isProductFindByName){
            setIsProductByName(!isProductFindByName)
        }
        if (isProductDeleteById){
            setIsProductDeleteById(!isProductDeleteById)
        }
    }

    const selectPrintFindAll = () => {
        selectAllFalse()
        setIsProductFindAll(!isProductFindAll);
    }

    const selectPrintByName = () => {
        selectAllFalse()
        setIsProductByName(!isProductFindByName);
    }

    return (
        <div>
            <h1>Продукты</h1>
            <h2>Работа с продуктами</h2>
            <button onClick={selectPrintFindAll}>Показать все продукты</button>
            <br/><br/>

            <button onClick={selectPrintByName}>Найти по имени</button>
            <input type={"text"} id={"nameFind"} placeholder={"Name product"}/><br/><br/>

            <button onClick={RemoveProduct}>Удалить продукт по имени</button>
            <input type={"text"} id={"deleteFind"} placeholder={"Name product"}/><br/><br/>

            <p>Добавить продукт</p>
            <c>Name:</c>
            <input type={"text"} placeholder={"Name product"} id={"productName"}/><br/>
            <c>Price:</c>
            <input type={"number"} placeholder={"Price product"} id={"productPrice"}/><br/>
            <c>Amount:</c>
            <input type={"number"} placeholder={"Amount product"} id={"productAmount"}/><br/><br/>
            <button onClick={AddProduct}>Добавить продукт</button>
            <br/>

            <p>Изменить продукт</p>
            <c>Name:</c>
            <input type={"text"} placeholder={"Name product"} id={"productNameEdit"}/><br/>
            <c>Price:</c>
            <input type={"number"} placeholder={"Price product"} id={"productPriceEdit"}/><br/>
            <c>Amount:</c>
            <input type={"number"} placeholder={"Amount product"} id={"productAmountEdit"}/><br/><br/>
            <button onClick={EditProduct}>Изменить продукт</button>
            <br/>

            <h4>Список продуктов:</h4>
            <div>
                {isProductFindAll ? <ProductAll/> : <p></p>}
                {isProductFindByName ? <GetProductByName/> : <p></p>}
            </div>
        </div>);
}