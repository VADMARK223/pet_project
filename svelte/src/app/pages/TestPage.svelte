<script>
    let data = "";

    async function fetchDataHandler() {
        const url = "http://localhost:8080/svelte/test";
        const response = await fetch(url);
        data = await response.text();
    }
</script>

<button on:click={fetchDataHandler}>Fetch data</button>  {data}