<script>
	export let name;
	let data = "data";
	async function fetchDataHandler() {
		const url = "http://localhost:8080/test";
		const response = await fetch(url);
		const json = await response.json();
		data = json.data;
	}
</script>

<main>
	<h1>Request {name}!</h1>
	<button on:click={fetchDataHandler}>Fetch data</button>
	<div>{data}</div>
</main>

<style>
	main {
		text-align: center;
		padding: 1em;
		max-width: 240px;
		margin: 0 auto;
	}

	h1 {
		color: #ff3e00;
		text-transform: uppercase;
		font-size: 4em;
		font-weight: 100;
	}

	@media (min-width: 640px) {
		main {
			max-width: none;
		}
	}
</style>