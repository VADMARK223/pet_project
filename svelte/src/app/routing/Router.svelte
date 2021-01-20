<script>
    import {hash} from '../services/router';
    import HomePage from '../pages/Home.svelte';
    import LoginPage from '../pages/Login.svelte';
    import LogoutPage from '../pages/Logout.svelte';
    import RegistrationPage from '../pages/Registration.svelte';
    import AdminPage from '../pages/Admin.svelte';
    import SettingsPage from '../pages/Settings.svelte';
    import TestPage from '../pages/Test.svelte';
    import NotFoundPage from '../pages/NotFound.svelte';

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