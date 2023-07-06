import axios from "axios";
import {getUser} from "./UserService";

const API_URL = "http://localhost:8080/payments";

const pay = (idClient, dispatch) => {
    return axios.post(API_URL, {"idClient": idClient}).then(
        (response) => {
            getUser(idClient, dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};



const payService = {
    pay
};

export default payService