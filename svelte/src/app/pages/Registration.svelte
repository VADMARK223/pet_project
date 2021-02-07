<script>
    import {redirect} from '../services/router';
    import {writable} from "svelte/store";
    import {registration} from "../services/auth";

    const registrationForm = writable({});
    let response = {};
    let error = "";

    async function handlerOnSubmit() {
        try {
            response = await registration(registrationForm)
            redirect("");
        } catch (e) {
            error = e.response.data;
        }
    }
</script>

<p class="registration-error">{error}</p>
<form class="registration-content" on:submit|preventDefault={handlerOnSubmit}>
    <label for="username">Username:</label>
    <input id="username" type="text" bind:value={registrationForm.username}>

    <label for="password">Password:</label>
    <input id="password" type="password" bind:value={registrationForm.password}>

    <label for="confirmPassword">Password confirmation:</label>
    <input id="confirmPassword" type="password" bind:value={registrationForm.confirmPassword}>
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