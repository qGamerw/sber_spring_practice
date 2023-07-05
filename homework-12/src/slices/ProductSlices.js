import logo from '../img/censored.png';
import {createSlice} from '@reduxjs/toolkit'

export const productSlice = createSlice({
    name: 'products',
    initialState: {
        products: [
            {
                id: 1,
                oldname: "",
                name: "Самый нужный товар №1",
                price: 100,
                url: logo
            },
            {
                id: 2,
                oldname: "",
                name: "Самый нужный товар №2",
                price: 10,
                url: logo
            },
            {
                id: 3,
                oldname: "",
                name: "Самый нужный товар №3",
                price: 30,
                url: logo
            }
        ],
    },
    reducers: {
        pushProduct: (state, action) => {
            const product = action.payload;
            product.id = Math.floor(Math.random() * 1_000_000);
            product.url = logo

            console.log(action.payload)
            state.products = [action.payload, ...state.products]
        },
        editProduct: (state, action) => {
            const {id, name, price} = action.payload;

            state.products = state.products.map((product) => {
                if (product.id === id) {

                    product.name = name;
                    product.price = price;
                    console.log(product);
                }
                return product;
            });
        },
        removeProduct: (state, action) => {
            console.log(action.payload)
            state.products = state.products.filter(product => product.id !== action.payload)
        },
        searchProducts: (state, action) => {
            const query = action.payload.toLowerCase();
            state.query = state.products.filter(product => product.name.toLowerCase().includes(query));
        },
        clearSearchResults: (state, action) => {
            state.query = state.products;
        },
    },
})

export const {
    pushProduct, editProduct,
    searchProducts, removeProduct
    , clearSearchResults
} = productSlice.actions

export default productSlice.reducer