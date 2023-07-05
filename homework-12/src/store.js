import {configureStore} from '@reduxjs/toolkit'
import productReducer from "./slices/ProductSlices";
import basketsReducer from "./slices/BasketSlices";

export default configureStore({
    reducer: {
        products: productReducer,
        baskets: basketsReducer,
    },
})