<script>
    import {onMount} from 'svelte';
    import {getUserList} from "../services/users";
    import User from '../components/User.svelte';

    let users = "";
    onMount(async () => {
        users = await getUserList();
    })
</script>

{#each users as user}
    <User id="{user.id}" username="{user.username}"/>
{/each}