<script>
    import {hash} from '../services/router';
    import {writable} from "svelte/store";
    import {registration} from "../services/auth";

    const registrationForm = writable({});
    let response = {};
    let error = "";
    async function handlerOnSubmit() {
        try {
            response = await registration(registrationForm)
            error = "";
            hash.set("home");
        } catch (e) {
            error = e.response.data;
        }
    }
</script>

<p class="registration-error">{error}</p>
<form class="registration-content" on:submit|preventDefault={handlerOnSubmit}>
    <label>Username:</label>
    <label>
        <input type="text" bind:value={registrationForm.username}>
    </label>
    <label>Password:</label>
    <label>
        <input type="password" bind:value={registrationForm.password}>
    </label>
    <label>Password confirmation:</label>
    <label>
        <input type="password" bind:value={registrationForm.confirmPassword}>
    </label>
    <button type="submit">Sign in</button>
</form>

<style>
    .registration-error {
        color: red;
    }
    .registration-content {
        display: grid;
        grid-template-columns: 10% 90%;
        grid-column-gap: 10px;
        align-items: center;
    }
</style>