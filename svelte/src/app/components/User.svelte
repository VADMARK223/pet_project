<script>
    export let id
    export let username = "Undefined username"
    function submitHandler() {
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/svelte/admin/user/" + id)
        xhr.onload = function () {
            location.reload();
        }
        xhr.send();
    }
</script>

<form on:submit|preventDefault={submitHandler} method="post">
    <h2>User: №{id} {username} <input type="submit" value="Remove"/></h2>
</form>
