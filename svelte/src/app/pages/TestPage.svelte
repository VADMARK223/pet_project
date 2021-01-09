<script>
    let data = "";

    async function fetchDataHandler() {
        const url = process.env.API_BASE_URL + "/test";
        const response = await fetch(url);
        data = await response.text();
    }
</script>

<button on:click={fetchDataHandler}>Fetch data</button>  {data}