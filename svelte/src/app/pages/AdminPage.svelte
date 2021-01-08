<script>
    let data = "data";

    async function fetchDataHandler() {
        const url = "http://localhost:8080/svelte/users";
        const response = await fetch(url);

        data = await response.text();
        document.getElementById("temp").insertAdjacentHTML('afterend', data)
    }
</script>

<button on:click={fetchDataHandler}>Fetch users</button>
<hr>
<div id="temp"></div>