<script>
    import {onMount} from 'svelte';
    import {getUserList} from "../services/users";
    import User from '../components/User.svelte';

    let users = "";
    let error = "";
    onMount(async () => {
        try {
            users = await getUserList();
        } catch (e) {
            error = e.response.data.message;
        }
    })
</script>

<p class="admin-error">{error}</p>
{#each users as user}
    <User id="{user.id}" username="{user.username}"/>
{/each}

<style>
    .admin-error {
        color: red;
    }
</style>
