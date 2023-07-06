import {createSlice} from '@reduxjs/toolkit'

export const userSlice = createSlice({
    name: 'user',
    initialState: {
        user: {},
        isReg: false,
        isAuth: true,

    },
    reducers: {
        set: (state, action) => {
            state.user = action.payload;
        },
        setReg: (state, action) => {
            state.isReg = action.payload
        },
        setAuth: (state, action) => {
            state.isAuth = action.payload
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