import {writable} from "svelte/store";

export const hash = writable('home');

function getUrlFromHash() {
    return location.hash.length >= 1
        ? location.hash.substring(1)
        : '';
}

function onloadHandler() {
    hash.set(getUrlFromHash());
}

function onhashchangeHandler() {
    hash.set(getUrlFromHash());
}

window.onhashchange = () => onhashchangeHandler()

window.onload = () => onloadHandler()