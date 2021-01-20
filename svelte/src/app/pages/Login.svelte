<script>
    import {writable} from "svelte/store";
    import {login} from "../services/auth";

    const userForm = writable({});
    let response = {};
    let error = "";
    async function handlerOnSubmit() {
        try {
            response = await login(userForm)
            error = "";
        } catch (e) {
            error = e.response.data;
        }
    }
</script>

<p class="login-error">{error}</p>
<form class="login-content" on:submit|preventDefault={handlerOnSubmit}>
    <label>Username:</label>
    <label>
        <input type="text" bind:value={userForm.username}>
    </label>
    <label>Password:</label>
    <label>
        <input type="password" bind:value={userForm.password}>
    </label>
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
