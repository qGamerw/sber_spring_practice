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

const isProductBasket = async (userId, dispatch) => {
    try {
        const response = await axios.get(`${API_URL}/${userId}`);
        getUser(userId, dispatch);

        return response.status === 200;
    } catch (error) {
        const _content = (error.response && error.response.data) + " " +
            error.message + " " +
            error.toString();

        console.error(_content);
        return false;
    }
};

const cartService = {
    add,
    updateProduct,
    deleteProduct,
    isProductBasket
};

export default cartService;