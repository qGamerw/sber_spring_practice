import axios from "axios";
import {set} from "../slices/UserSlices";

const API_URL = "http://localhost:8080/clients";

export const getUser = (id, dispatch) => {
    return axios.get(`${API_URL}/${id}`).then(
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

export const createUser = (dispatch, user) => {
    return axios.post(API_URL, user).then(
        (response) => {
            getUser(dispatch, response.data);
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const updateUser = (dispatch, user) => {
    return axios.post(API_URL, user).then(
        (response) => {
            getUser(dispatch, user.id)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};
const signIn = (dispatch, email) => {
    return axios.get(API_URL + "/email?email=" + email).then(
        (response) => {
            dispatch(set(response.data))
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const userService = {
    getUser, createUser, updateUser, signIn
};

export default userService