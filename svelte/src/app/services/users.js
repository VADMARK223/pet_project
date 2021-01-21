import api from "./api"

export const getUserList = async () => {
    return await api.get("/admin");
}

export const deleteUser = async (id) => {
    try {
        return await api.del("/admin/user/" + id);
    } catch (error) {
        console.log(error);
    }
}