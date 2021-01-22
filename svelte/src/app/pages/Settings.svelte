<script>
    import {onMount} from 'svelte';
    import {getSettings} from "../services/settings";
    import {hash} from "../services/router";

    let settings = "";

    onMount(async () => {
        try {
            settings = await getSettings();
        } catch (e) {
            console.log("Error: " + e.response.data.message)
            hash.set("login");
        }
    })
</script>

<h1>Settings</h1>
{settings}