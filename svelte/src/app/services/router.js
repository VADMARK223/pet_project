import {writable} from "svelte/store";

export const hash = writable('home');

function hashSetter() {
    console.log("Location hash: " + location.hash);
    hash.set(
        location.hash.length >= 2
            ? location.hash.substring(2)
            : ''
    );
}

window.onhashchange = () => hashSetter()