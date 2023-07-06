import axios from "axios";

const API_URL = "http://localhost:8080/payments";

const pay = (idClient, dispatch) => {
    return axios.post(API_URL, {"idClient": 1}).then(
        (response) => {
            return true;
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