<script>
    import {onMount} from 'svelte';
    import {getUserList} from "../services/users";
    import User from '../components/User.svelte';
    import {hash} from "../services/router";

    let users = "";
    onMount(async () => {
        try {
            users = await getUserList();
        } catch (e) {
            hash.set("login");
        }
    })
</script>

{#each users as user}
    <User id="{user.id}" username="{user.username}"/>
{/each}