<script>
    import Sidenav from './app/components/Sidenav.svelte';
    import Router from './app/routing/Router.svelte';
    import {onMount} from 'svelte';
    import {auth} from "./app/services/auth";
    import {authenticate} from "./app/services/state";
    import {user} from "./app/services/state";

    onMount(async () => {
        const result = await auth("Hello");
        console.log("On mount: " + result);
        authenticate.set(result);
    });

    let username;
    user.subscribe(value => {
        if (value != null) {
            console.log("User: " + JSON.stringify(value));
            username = value['sub'];
        }
    })
</script>

<header>
    <h2><a href="/">Svelte</a></h2>
    {#if username}
        <h4>Welcome back, {username}.</h4>
    {/if}
</header>
<nav>
    <Sidenav/>
</nav>
<article>
    <Router/>
</article>

<style>
    nav {
        width: 150px;
        float: left;
    }

    article {
        height: 100%;
        overflow: hidden;
    }
</style>