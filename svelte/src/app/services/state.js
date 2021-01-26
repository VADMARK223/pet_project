import {writable} from "svelte/store";

export const authenticate = writable(false);
export const user = writable({});