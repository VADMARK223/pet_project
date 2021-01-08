<script>
    import {hash} from '../services/router';
    import Homepage from '../pages/HomePage.svelte';
    import TestPage from '../pages/TestPage.svelte';
    import NotFoundPage from '../pages/NotFoundPage.svelte';

    let value = Homepage;

    hash.subscribe(val => {
        console.log("hash=" + val);
        switch (val) {
            case 'home':
                value = Homepage;
                break;
            case 'test':
                value = TestPage;
                break;
            default:
                value = NotFoundPage;
        }
    })
</script>

<main>
    <svelte:component this="{value}"/>
</main>

<style>
    main {
        flex-grow: 1;
        background-color: var(--primary-color-2);
        color: var(--second-color);
        padding: 1rem;
    }
</style>