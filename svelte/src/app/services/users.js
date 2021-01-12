import api from "./api"

export const getUserList = async () => {
    try {
        const response = await api.get("/admin");
        return response;
    } catch (error) {
        console.log(error);
    }
}