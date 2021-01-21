import axios from "axios";

const axiosAPI = axios.create({
    baseURL: process.env.API_BASE_URL
});

const apiRequest = (method, url, request) => {
    const headers = {
        'Content-Type':'application/json',
        'Authorization':'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhIiwiaWQiOjEsImV4cCI6MTYxMjQ3MjQwMH0.00fmyZ2keSjLow47jbtkmTYc8G-sHioi24obOckF-iRgPPdpXqej3jG15B7NJJGcyidv-MMA3gy3ykkmjFWVqg'
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
            return Promise.reject(err)
        });
}

const post = (url, request) => apiRequest("post", url, request);
const get = (url, request) => apiRequest("get", url, request);
const del = (url, request) => apiRequest("delete", url, request);

const api ={
    post,
    get,
    del
}
export default api;
