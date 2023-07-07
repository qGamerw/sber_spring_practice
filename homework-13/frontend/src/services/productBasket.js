import axios from "axios";
import authHeader from "./auth-header";
import {set} from "../slices/userSlice";
import {setCart} from "../slices/cartSlice";
import {API_URL} from "../API_URL";

export const API_URL_BASKET = API_URL + "/basket";

const getBasket = (userId, dispatch) => {

    return axios.get(API_URL_BASKET + `/${userId}`, {headers: authHeader()}).then(
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

    console.log(`${API_URL_BASKET}/${userId}/product/${productId}`, {amount: amount}, {headers: authHeader()})

    return axios.post(`${API_URL_BASKET}/${userId}/product/${productId}`, {amount: amount}, {headers: authHeader()}).then(
        () => {
            getBasket(userId, dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const updateProduct = (userId, productId, amount, dispatch) => {
    return axios.put(`${API_URL_BASKET}/${userId}/product/${productId}`, amount, {headers: authHeader()}).then(
        () => {
            getBasket(userId, dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const deleteProduct = (userId, productId, dispatch) => {

    return axios.delete(`${API_URL_BASKET}/${userId}/product/${productId}`, {headers: authHeader()}).then(
        () => {
            getBasket(userId, dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const productBasket = {
    getBasket,
    addProduct,
    deleteProduct,
    updateProduct
};

export default productBasket