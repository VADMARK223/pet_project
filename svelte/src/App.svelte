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

<div class="modal fade show" tabindex="-1" id="recordModal" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Add weight</h4>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <p>Empty body</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save</button>
            </div>
        </div>
    </div>
</div>

<header class="navbar navbar-expand-md navbar-dark bg-dark py-0">
    <nav class="container-xxl flex-wrap flex-md-nowrap" aria-label="Main navigation">
        <a href="/" class="navbar-brand">
            <img src="/images/svelte.png" alt="Svelte">Svelte
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#dbNavbar" aria-controls="dbNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="dbNavbar">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <Sidenav/>
                </li>
            </ul>
            <ul class="navbar-nav ms-md-auto">
                <li class="nav-item nav-link">
                    {#if username}
                        Welcome back, {username}.
                    {/if}
                </li>
                <li class="nav-item">
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#recordModal">
                        <span aria-hidden="true">&plus;</span>Add weight
                    </button>
                </li>
            </ul>
        </div>
    </nav>
</header>
<article>
    <Router/>
</article>

<!--<style>
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
</style>-->
