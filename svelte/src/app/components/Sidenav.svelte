<script>
    import RouterLink from '../routing/RouterLink.svelte'
    import {user} from "../services/state";

    let username;
    user.subscribe(value => {
        console.log(value);
        const user = JSON.stringify(value);
        username = user;
    })
</script>

{#if username === true}
    <RouterLink url="logout">Log out</RouterLink><br>
    <RouterLink url="admin">Admin</RouterLink><br>
    <RouterLink url="settings">Settings</RouterLink><br>
    <RouterLink url="test">Test</RouterLink><br>
{:else}
    <RouterLink url="login">Sing in</RouterLink><br>
    <RouterLink url="registration">Registration</RouterLink><br>
{/if}




