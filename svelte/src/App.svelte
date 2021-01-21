<script>
    import Sidenav from './app/components/Sidenav.svelte'
    import Router from './app/routing/Router.svelte';
</script>

<header>
    <h2><a href="/">Svelte</a></h2>
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