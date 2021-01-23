import axios from "axios";
import {hash} from "./router";

const axiosAPI = axios.create({
    baseURL: process.env.API_BASE_URL
});

const apiRequest = (method, url, request) => {
    const headers = {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhIiwiaWQiOjEsImV4cCI6MTYxMjQ3MjQwMH0.00fmyZ2keSjLow47jbtkmTYc8G-sHioi24obOckF-iRgPPdpXqej3jG15B7NJJGcyidv-MMA3gy3ykkmjFWVqg'
    };

    return axiosAPI({
        method,
        url,
        data: request,
        headers
    }).then(res => {
        return Promise.resolve(res.data);
    })
        .catch(err => {
            console.log(err.response)
            const data = err.response.data;
            if (data !== undefined && data.status === 401) {
                console.log('Unauthorized error:' + data.message);
                hash.set("login");
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
