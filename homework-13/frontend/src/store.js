import {configureStore} from '@reduxjs/toolkit'
import productReducer from "./slices/ProductSlices";
import basketsReducer from "./slices/BasketSlices";
import userReducer from "./slices/UserSlices";

export default configureStore({
    reducer: {
        products: productReducer,
        baskets: basketsReducer,
        user: userReducer
    },
})