<script>
    let data = "";
    const url = "ws://localhost:8080/ws";
    const webSocket = new WebSocket(url);

    async function fetchDataHandler() {
        // const url = process.env.API_BASE_URL + "/test";
        // webSocket.onopen = function () {
        webSocket.send(JSON.stringify(
            {
                username: 'a',
                password: 'a'
            }
        ))
        // }
        // console.log("websocket" + webSocket.readyState)

    }
</script>

<button on:click={fetchDataHandler}>Fetch data</button>  {data}