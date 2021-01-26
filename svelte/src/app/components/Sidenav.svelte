<script>
    import RouterLink from '../routing/RouterLink.svelte'
    import {user} from "../services/state";

    let username;
    user.subscribe(value => {
        if (value !== null) {
            username = value['sub'];
        }
    })
</script>

{#if username !== undefined}
    <RouterLink url="logout">Log out</RouterLink>
    <br>
    <RouterLink url="admin">Admin</RouterLink>
    <br>
    <RouterLink url="settings">Settings</RouterLink>
    <br>
    <RouterLink url="test">Test</RouterLink>
    <br>
{:else}
    <RouterLink url="login">Sing in</RouterLink>
    <br>
    <RouterLink url="registration">Registration</RouterLink>
    <br>
{/if}




