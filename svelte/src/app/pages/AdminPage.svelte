<script>
    let data = "";

    async function fetchDataHandler() {
        const url = "http://localhost:8080/svelte/test";
        const response = await fetch(url);
        data = await response.json();
    }

    fetchDataHandler();
</script>
<h2>User: №{data.id} {data.username} <button>Remove</button></h2>