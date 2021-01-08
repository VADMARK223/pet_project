<script>
    let data = "data";

    async function fetchDataHandler() {
        const url = "http://localhost:8080/svelte/test";
        const response = await fetch(url);
        const json = await response.json();
        data = json.data;
    }
</script>

<button on:click={fetchDataHandler}>Fetch data</button>  {data}