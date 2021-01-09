<script>
    import {hash} from '../services/router';
    import HomePage from '../pages/HomePage.svelte';
    import LoginPage from '../pages/LoginPage.svelte';
    import LogoutPage from '../pages/LogoutPage.svelte';
    import RegistrationPage from '../pages/RegistrationPage.svelte';
    import AdminPage from '../pages/AdminPage.svelte';
    import SettingsPage from '../pages/SettingsPage.svelte';
    import TestPage from '../pages/TestPage.svelte';
    import NotFoundPage from '../pages/NotFoundPage.svelte';

    let value = HomePage;

    hash.subscribe(val => {
        console.log("Hash = " + val);
        switch (val) {
            case 'home':
                value = HomePage;
                break;
            case 'login':
                value = LoginPage;
                break;
            case 'logout':
                value = LogoutPage;
                break;
            case 'registration':
                value = RegistrationPage;
                break;
            case 'admin':
                value = AdminPage;
                break;
            case 'settings':
                value = SettingsPage;
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