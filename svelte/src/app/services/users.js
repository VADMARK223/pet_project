import api from "./api"

export const getUserList = async () => {
    try {
        return await api.get("/admin");
    } catch (error) {
        console.log(error);
    }
}

export const deleteUser = async (id) => {
    try {
        return await api.del("/admin/user/" + id);
    } catch (error) {
        console.log(error);
    }
}