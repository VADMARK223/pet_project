<script>
    import RouterLink from '../routing/RouterLink.svelte'
    import {user} from "../services/state";
</script>

{#if $user != null}
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




