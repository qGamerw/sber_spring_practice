import {createSlice} from '@reduxjs/toolkit'

export const productSlice = createSlice({
    name: 'products',
    initialState: {
        products: [],
    },
    reducers: {
        set: (state, action) => {
            state.products = action.payload;
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
    set,

    pushProduct, editProduct,
    searchProducts, removeProduct
    , clearSearchResults
} = productSlice.actions

export default productSlice.reducer