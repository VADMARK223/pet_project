<script>
    import {onMount} from 'svelte';
    import {getSettings} from "../services/settings";

    let settings = "";

    onMount(async () => {
        settings = await getSettings();
    })
</script>

<h1>Settings</h1>
{settings}