<script>
    import RouterLink from '../routing/RouterLink.svelte'
    import {authenticate} from "../services/state";

    let authenticateValue;
    authenticate.subscribe(value => {
        authenticateValue = value;
        console.log("Sidenav#value=" + value);
    })
</script>

Authenticate: {authenticateValue}.<br>
{#if authenticateValue === true}
    <RouterLink url="logout">Log out</RouterLink><br>
    <RouterLink url="admin">Admin</RouterLink><br>
    <RouterLink url="settings">Settings</RouterLink><br>
    <RouterLink url="test">Test</RouterLink><br>
{:else}
    <RouterLink url="login">Sing in</RouterLink><br>
    <RouterLink url="registration">Registration</RouterLink><br>
{/if}




