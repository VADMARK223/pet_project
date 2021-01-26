<script>
    import {onMount} from "svelte";
    import {redirect} from "../services/router";
    import {user} from "../services/state";

    onMount(() => {
        console.log("On mount.");
        user.set(null);
        localStorage.removeItem(process.env.JWT_TOKEN);
        redirect("");
    })
</script>
