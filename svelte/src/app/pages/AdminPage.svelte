<script>
    import User from '../components/User.svelte'
    let users = "";

    async function fetchDataHandler() {
        const url = "http://localhost:8080/svelte/admin";
        const response = await fetch(url);
        users = await response.json();
    }

    fetchDataHandler();
</script>
{#each users as user}
    <User id="{user.id}" username="{user.username}"/>
{/each}
