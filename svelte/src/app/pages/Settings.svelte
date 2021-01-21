<script>
    import {onMount} from 'svelte';
    import {getSettings} from "../services/settings";

    let settings = "";

    onMount(async () => {
        try {
            settings = await getSettings();
        } catch (e) {
            settings = e.response.data.message;
        }
    })
</script>

<h1>Settings</h1>
{settings}