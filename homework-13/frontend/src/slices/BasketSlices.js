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
    },
})

export const {
    set
} = basketsSlice.actions

export default basketsSlice.reducer