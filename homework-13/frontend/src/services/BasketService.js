import axios from "axios";
import {getUser} from "./UserService";

const API_URL = "http://localhost:8080/baskets";

export const add = (userId, productId, amountProduct, dispatch) => {
    return axios.post(API_URL + `/${userId}`, {"id": productId, "amount": amountProduct}).then(
        (response) => {
            getUser(userId, dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const updateProduct = (userId, productId, amountProduct, dispatch) => {
    return axios.put(API_URL + `/${userId}`, {"id": productId, amount: amountProduct}).then(
        (response) => {
            getUser(userId, dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const deleteProduct = (userId, productId, dispatch) => {
    return axios.delete(API_URL + `/${userId}`, {data: {"id": productId}}).then(
        (response) => {
            getUser(userId, dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const cartService = {
    add, updateProduct, deleteProduct
};

export default cartService;