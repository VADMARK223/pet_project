<script>
    import RouterLink from '../routing/RouterLink.svelte'
    import {user} from "../services/state";
</script>

{#if $user != null}
    <RouterLink url="logout">Log out</RouterLink>
    <RouterLink url="admin">Admin</RouterLink>
    <RouterLink url="settings">Settings</RouterLink>
    <RouterLink url="test">Test</RouterLink>
{:else}
    <RouterLink url="login">Sing in</RouterLink>
    <RouterLink url="registration">Registration</RouterLink>
{/if}




