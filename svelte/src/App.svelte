<script>
    import Sidenav from './app/components/Sidenav.svelte';
    import Router from './app/routing/Router.svelte';
    import {onMount} from 'svelte';
    import {auth} from "./app/services/auth";
    import {user} from "./app/services/state";
    import jwt_decode from "jwt-decode";

    onMount(async () => {
        const result = await auth();
        if (result) {
            const token = localStorage.getItem(process.env.JWT_TOKEN);
            const decoded = jwt_decode(token);
            user.set(decoded);
        }
    });

    let username;
    user.subscribe(value => {
        if (value != null) {
            username = value['sub'];
        } else {
            username = undefined;
        }
    })
</script>

<div class="modal fade show" tabindex="-1" id="recordModal" aria-modal="true" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Add weight</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <p>Empty body</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<header>
    <h2><a href="/">Svelte</a></h2>
    <button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#recordModal">Add weight</button>
    {#if username}
        <h4>Welcome back, {username}.</h4>
    {/if}
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

    .modal {
        display: none;
    }
</style>