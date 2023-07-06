import {createSlice} from '@reduxjs/toolkit'

export const basketsSlice = createSlice({
    name: 'baskets',
    initialState: {
        baskets: [],
    },
    reducers: {
        set: (state, action) => {
            state.products = action.payload;
        },

        pushBasket: (state, action) => {
            const {product, amount} = action.payload;
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
            const {oldname, name, price} = action.payload;

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
        decreaseAmount: (state, action) => {
            const product = action.payload;
            const existingProduct = state.baskets.find(p => p.idProduct === product.id);
            if (existingProduct) {
                if (existingProduct.amount > 0) {
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

export const {
    set,
    pushBasket, editBasket,
    removeBasket, increaseAmount,
    decreaseAmount, pay
} = basketsSlice.actions

export default basketsSlice.reducer