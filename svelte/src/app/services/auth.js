import api from "./api";

export const auth = async () => {
    return await api.get("/auth");
}

export const login = async (data) => {
    return await api.post("/login", JSON.stringify(data));
}

export const registration = async (data) => {
    return await api.post("/registration", JSON.stringify(data));
}