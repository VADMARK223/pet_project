<script>
    import {deleteUser} from "../services/users";

    export let id
    export let username = "Undefined username"

    function submitHandler() {
        deleteUser(id).then(() => {
            location.reload();
        });
    }
</script>

<tr>
    <td>{id}</td>
    <td>{username}</td>
    <td>
        <form on:submit|preventDefault={submitHandler} method="post">
            <input type="submit" value="Remove"/>
        </form>
    </td>
</tr>