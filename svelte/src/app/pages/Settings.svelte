<!--suppress HtmlUnknownTarget -->
<script>
    import {onMount} from 'svelte';
    import {getSettings, upload} from "../services/settings";

    let imgAsBase64;

    onMount(async () => {
        imgAsBase64 = await getSettings();
    })

    function onFileSelected(e) {
        console.log("On file selected.")
        const file = e.target.files[0];
        console.log("File: ", file);

        let reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = ev => {
            imgAsBase64 = ev.target.result;
        }
    }

    async function handlerOnSubmit() {
        await upload(imgAsBase64);
        location.reload();
    }
</script>

<img src="{imgAsBase64}" alt="Avatar invalid."/>
<form on:submit|preventDefault={handlerOnSubmit} enctype="multipart/form-data">
    <input type="file" on:change={(e)=>{onFileSelected(e)}}>
    <button type="submit">Upload</button>
</form>

