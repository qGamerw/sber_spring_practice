import axios from "axios";
import authHeader from "./auth-header";
import {set} from "../slices/userSlice";
import {setCart} from "../slices/cartSlice";
import {API_URL} from "./API_URL";

const API_URL_CART = `${API_URL}/cart`;

const getBasket = (userId, dispatch) => {

    return axios.get(API_URL_CART + `/${userId}`, {headers: authHeader()}).then(
        (response) => {
            console.log(response.data)
            dispatch(setCart(response.data))
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)

            dispatch(set([]));
        });
}

const addProduct = (userId, productId, amount, dispatch) => {

    return axios.post(`${API_URL_CART}/${userId}/product/${productId}`, {amount: amount}, {headers: authHeader()}).then(
        () => {
            getBasket(userId, dispatch);
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const updateAmount = (userId, productId, amount, dispatch) => {
    return axios.put(`${API_URL_CART}/${userId}/product/${productId}`, amount, {headers: authHeader()}).then(
        () => {
            getBasket(userId, dispatch);
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const deleteProduct = (userId, productId, dispatch) => {

    return axios.delete(`${API_URL_CART}/${userId}/product/${productId}`, {headers: authHeader()}).then(
        () => {
            getBasket(userId, dispatch);
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const cartService = {
    getBasket,
    addProduct,
    deleteProduct,
    updateAmount
};

export default cartService