<script>
    import {deleteUser} from "../services/users";
    import {user} from "../services/state";

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
        {#if username !== $user['sub']}
            <form on:submit|preventDefault={submitHandler} method="post">
                <input type="submit" value="Remove"/>
            </form>
        {/if}
    </td>
</tr>