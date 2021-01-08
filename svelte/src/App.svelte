<script>
    import Router from './app/routing/Router.svelte';
    import Sidenav from './app/components/Sidenav.svelte'

    let data = "data";

    async function fetchDataHandler() {
        const url = "http://localhost:8080/test";
        const response = await fetch(url);
        const json = await response.json();
        data = json.data;
    }
</script>

<main>
    <button on:click={fetchDataHandler}>Fetch data</button>  {data}
    <Sidenav/>
    <Router/>
</main>