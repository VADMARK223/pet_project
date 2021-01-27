import api from "./api"

export const getSettings = async () => {
    return await api.get("/settings");
}

export const upload = async (imageAsBase64) => {
    return await api.post("/upload", imageAsBase64);
}