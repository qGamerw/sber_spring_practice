import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/";

const register = (registration) => {
    const {email, username, password} = registration;
    console.log(registration)
    return axios.post(API_URL + "signup", {
        email,
        username,
        password,
    });
};

const login = (login) => {
    const {username, password} = login;

    return axios
        .post(API_URL + "signin", {
            username,
            password,
        })
        .then((response) => {
            console.log(response)
            if (response.data.accessToken) {
                localStorage.setItem("user", JSON.stringify(response.data));
            }

            return response.data;
        });
};

const logout = () => {
    console.log("logout")
    localStorage.removeItem("user");
};

const authService = {
    register,
    login,
    logout,
};

export default authService;
