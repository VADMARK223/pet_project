import axios from "axios";

const axiosAPI = axios.create({
    baseURL: process.env.API_BASE_URL
});

const apiRequest = (method, url, request) => {
    const headers = {
        'Content-Type':'application/json',
        'Authorization':'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzIiwidXNlcklkIjozMCwicm9sZSI6W3siaWQiOjIsIm5hbWUiOiJST0xFX1VTRVIiLCJ1c2VycyI6bnVsbCwiYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJleHAiOjE2MTIwNDA0MDB9.bWlc0DRBpRyFLU9wMPlDEvdTJWATl-AII1odZNDS-F9c2AQMZr83mOjUDtjCQz4GfEOT8z4QAv-vP3ZEPLA-Zw'
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
