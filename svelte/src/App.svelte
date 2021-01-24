<script>
    import Sidenav from './app/components/Sidenav.svelte';
    import Router from './app/routing/Router.svelte';
    import {onMount} from 'svelte';
    import {auth} from "./app/services/auth";
    import {authenticate} from "./app/services/state";

    onMount(async () => {
        const result = await auth("Hello");
        console.log("On mount: " + result);
        authenticate.set(result);
    });

    /*const jwt = localStorage.getItem("BEARER_JWT");
    // console.log("jwt: " + jwt);
    const headers = {
        'Content-Type': 'application/json'
        // 'Authorization': jwt
    }
    const axiosAPI = axios.create({
        headers
    });
    window.addEventListener("load", function () {
        console.log("All resources loading!");
        axiosAPI.post('/auth').then(res => {
            console.log("Res: " + res.data);
            console.log("Header Authorization: " + res.headers.authorization)
            //localStorage.setItem("BEARER_JWT", res.data);
        })
            .catch(function (error) {
                console.log("Error: " + error)
                // localStorage.setItem("TEST", "VADMARK");
                // window.location.href = "/login";
            });
    })*/
</script>

<header>
    <h2><a href="/">Svelte</a></h2>
</header>
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