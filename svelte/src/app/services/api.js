import axios from "axios";
// import {hash} from "./router";
import {user} from "./state";
import jwt_decode from "jwt-decode";

const axiosAPI = axios.create({
    baseURL: process.env.API_BASE_URL
});

const apiRequest = (method, url, request) => {
    const token = localStorage.getItem(process.env.JWT_TOKEN);
    const headers = {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + token
    };

    return axiosAPI({
        method,
        url,
        data: request,
        headers
    }).then(res => {
        if (res.headers.authorization !== undefined) {
            const token = res.headers.authorization;
            const decoded = jwt_decode(token);
            user.set(decoded);
            localStorage.setItem(process.env.JWT_TOKEN, token);
        }

        return Promise.resolve(res.data);
    })
        .catch(err => {
            console.log(err.response)
            const data = err.response.data;
            if (data !== undefined && data.status === 401) {
                console.log('Unauthorized error:' + data.message);
                // hash.set("login");
            } else {
                return Promise.reject(err)
            }
        });
}

const post = (url, request) => apiRequest("post", url, request);
const get = (url, request) => apiRequest("get", url, request);
const del = (url, request) => apiRequest("delete", url, request);

const api = {
    post,
    get,
    del
}
export default api;
