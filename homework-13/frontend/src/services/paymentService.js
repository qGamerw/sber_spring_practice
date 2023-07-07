import axios from "axios";
import UserService from "./userService";
import authHeader from "./auth-header";
import {API_URL} from "../API_URL";

export const API_URL_PAY = API_URL + "/payment";

const pay = (payment, dispatch) => {

    return axios.post(API_URL_PAY, payment, {headers: authHeader()}).then(
        () => {
            UserService.getUser(payment.userId, dispatch)
        },
        (error) => {
            const _content = (error.response && error.response.data) ||
                error.message ||
                error.toString();

            console.error(_content)
        });
};

const paymentService = {
    pay,
};

export default paymentService