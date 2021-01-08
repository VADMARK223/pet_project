<script>
    import Sidenav from './app/components/Sidenav.svelte'
    import Router from './app/routing/Router.svelte';
</script>

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