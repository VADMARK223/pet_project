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

<form on:submit|preventDefault={submitHandler} method="post">
    <h2>User: №{id} {username} <input type="submit" value="Remove"/></h2>
</form>
