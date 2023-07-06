import {createSlice} from '@reduxjs/toolkit'

export const userSlice = createSlice({
    name: 'user',
    initialState: {
        user: {}

    },
    reducers: {
        set: (state, action) => {
            state.user = action.payload;
        },
    }
})

// Action creators are generated for each case reducer function
export const {
    calculateTotal,
    set,
    setReg,
    setAuth
} = userSlice.actions

export default userSlice.reducer