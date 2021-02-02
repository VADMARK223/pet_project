<script>
    import {onMount} from 'svelte';
    import {getUserList} from "../services/users";
    import User from '../components/User.svelte';

    let users = "";
    onMount(async () => {
        users = await getUserList();
    })
</script>

<table class="table">
    <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Username</th>
            <th scope="col">Action</th>
        </tr>
    </thead>
    <tbody>
        {#each users as user}
            <User id="{user.id}" username="{user.username}"/>
        {/each}
    </tbody>
</table>