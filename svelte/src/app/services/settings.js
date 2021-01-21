import api from "./api"

export const getSettings = async () => {
    return await api.get("/settings");
}