<script>
    import Sidenav from './app/components/Sidenav.svelte';
    import Router from './app/routing/Router.svelte';
    import {onMount} from 'svelte';
    import {auth} from "./app/services/auth";
    import {user} from "./app/services/state";
    import jwt_decode from "jwt-decode";

    onMount(async () => {
        const result = await auth();
        if (result) {
            const token = localStorage.getItem(process.env.JWT_TOKEN);
            const decoded = jwt_decode(token);
            user.set(decoded);
        }
    });

    let username;
    user.subscribe(value => {
        if (value != null) {
            username = value['sub'];
        } else {
            username = undefined;
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