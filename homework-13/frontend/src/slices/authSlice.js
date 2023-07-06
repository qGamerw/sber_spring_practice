import {createSlice} from '@reduxjs/toolkit'

const user = JSON.parse(localStorage.getItem("user"));

export const authSlice = createSlice({
    name: 'auth',
    initialState: {
        user: user? user:null,
        isLoggedIn: !user
    },
    reducers: {
        login: (state, action) => {
            state.isLoggedIn = false;
            state.user = action.payload;
        },
        logout: (state, action) => {
            state.isLoggedIn = true;
            state.user = null;
        }
    },
})

// Action creators are generated for each case reducer function
export const {
    login,
    logout
} = authSlice.actions

export default authSlice.reducer
