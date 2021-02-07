<script>
    import {writable} from "svelte/store";
    import {login} from "../services/auth";
    import {redirect} from "../services/router";

    const userForm = writable({});
    let response = {};
    let error = "";

    async function handlerOnSubmit() {
        try {
            response = await login(userForm);
            redirect("");
        } catch (e) {
            if (e.response !== undefined) {
                console.log(e.response.data);
                error = e.response.data;
            } else {
                error = "Unknown error.";
            }
        }
    }
</script>

<p class="login-error">{error}</p>
<form class="login-content" on:submit|preventDefault={handlerOnSubmit}>
    <label for="username">Username:</label>
    <input id="username" type="text" bind:value={userForm.username}>

    <label for="password">Password:</label>
    <input id="password" type="password" bind:value={userForm.password}>
    <button type="submit">Sign in</button>
</form>

<style>
    .login-error {
        color: red;
    }

    .login-content {
        display: grid;
        grid-template-columns: 10% 90%;
        grid-column-gap: 10px;
        align-items: center;
    }
</style>
