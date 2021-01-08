<script>
    let data = "data";

    async function fetchDataHandler() {
        const url = "http://localhost:8080/svelte/admin";
        const response = await fetch(url);

        data = await response.text();
        // document.getElementById("temp").insertAdjacentHTML('afterend', data)
    }

    fetchDataHandler();
</script>
Data: {data}