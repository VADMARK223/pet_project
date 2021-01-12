import axios from "axios";

const axiosAPI = axios.create({
    baseURL: process.env.API_BASE_URL
});

const apiRequest = (method, url, request) => {
    const headers = {
        authorization: ""
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

const get = (url, request) => apiRequest("get", url, request);
const del = (url, request) => apiRequest("delete", url, request);

const api ={
    get,
    del
}
export default api;
