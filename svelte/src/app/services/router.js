import {writable} from "svelte/store";

export const hash = writable('home');

function getUrlFromHash() {
    return location.hash.length >= 2
        ? location.hash.substring(2)
        : '';
}

function onloadHandler() {
    console.log("Load handler: " + location.hash);
    hash.set(getUrlFromHash());
}

function onhashchangeHandler() {
    console.log("Hash change handler: " + location.hash);
    hash.set(getUrlFromHash());
}

window.onhashchange = () => onhashchangeHandler()

window.onload = () => onloadHandler()