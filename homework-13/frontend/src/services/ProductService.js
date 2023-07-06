import axios from "axios";
import {set} from "../slices/ProductSlices";

const API_URL = "http://localhost:8080/products";

const getProduct = (dispatch) => {
    return axios.get(API_URL).then(
        (response) => {
            dispatch(set(response.data));
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)

            dispatch(set([]));
        });
};

const createProduct = (book, dispatch) => {
    return axios.post(API_URL, book).then(
        (response) => {
            getProduct(dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const deleteProduct = (id, dispatch) => {
    return axios.delete(API_URL + `/${id}`).then(
        (response) => {
            getProduct(dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const updateProduct = (product, dispatch) => {
    return axios.put(API_URL, product).then(
        (response) => {
            getProduct(dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const productService = {
    getProduct,
    createProduct,
    deleteProduct,
    updateProduct,
};

export default productService