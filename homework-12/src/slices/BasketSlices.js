import logo from '../img/logo.svg';
import {createSlice} from '@reduxjs/toolkit'

export const basketsSlice = createSlice({
    name: 'baskets',
    initialState: {
        baskets: [
            {
                id: 1,
                idClient : 1,
                idProduct: 1,
                price: 100,
                amount: 10,
                name: "Самый нужный товар №1"
            },
            {
                id: 2,
                idClient : 1,
                idProduct: 2,
                price: 10,
                amount: 1,
                name: "Самый нужный товар №2"
            },
            {
                id: 3,
                idClient : 1,
                idProduct: 3,
                price: 30,
                amount: 1,
                name: "Самый нужный товар №3"
            }
        ],
    },
    reducers: {
        pushBasket: (state, action) => {
            const { product, amount } = action.payload;
            const existingProduct = state.baskets.find(p => p.idProduct === product.id);
            if (existingProduct) {
                existingProduct.amount += +amount;
                return;
            }

            const productBasket = {
                id: Math.floor(Math.random() * 1_000_000),
                idClient: 3,
                idProduct: product.id,
                price: product.price,
                amount: amount,
                name: product.name
            };

            state.baskets = [productBasket, ...state.baskets];
        },
        editBasket: (state, action) => {
            const { oldname, name, price } = action.payload;

            state.products = state.products.map((product) => {
                if (product.name === oldname) {
                    console.log(action.payload);
                    product.name = name; // Измененное значение присваивается полю name
                    product.price = price;
                }
                return product; // Обязательно возвращаем объект product из map()
            });
        },
        increaseAmount: (state, action) => {
            const product = action.payload;
            const existingProduct = state.baskets.find(p => p.idProduct === product.id);
            if (existingProduct) {
                existingProduct.amount += +1;
            }
        },
        dicreaseAmount: (state, action) => {
            const product = action.payload;
            const existingProduct = state.baskets.find(p => p.idProduct === product.id);
            if (existingProduct) {
                if (existingProduct.amount > 0){
                    existingProduct.amount -= +1;
                }
            }
        },

        removeBasket: (state, action) => {
            console.log(action.payload)
            state.baskets = state.baskets.filter(product => product.id !== action.payload)
        },

        pay: (state, action) => {
            state.baskets = [{}]
        },
    },
})

// Action creators are generated for each case reducer function
export const {pushBasket, editBasket,
    removeBasket, increaseAmount,
    dicreaseAmount, pay} = basketsSlice.actions

export default basketsSlice.reducer