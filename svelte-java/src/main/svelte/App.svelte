<script lang="ts">
	import { createRestClient } from "./backend";

	const restClient = createRestClient();
	let promise = restClient.listPeople();
	let addingPerson = false;

	let newPersonName: string;
	let newPersonAge: number;

	async function addPerson() {
		let p = { id: 0, name: newPersonName, age: newPersonAge };
		addingPerson = true;
		await restClient.addPerson(p);
		promise = restClient.listPeople();
		addingPerson = false;
	}
</script>

<main class="container">
	<div class="row mt-5 gy-2">
        <div class="col-md-5">
            <input class="form-control" placeholder="Name" bind:value={newPersonName}>
        </div>
        <div class="col-md-5">
            <input class="form-control" type="number" placeholder="Age" bind:value={newPersonAge}>
        </div>
        <div class="col-md-2 d-flex">
            {#if !addingPerson}
                <button class="btn btn-primary flex-fill" on:click={addPerson}>Add</button>
            {:else}
                <div>Saving...</div>
            {/if}
        </div>
	</div>
	{#await promise}
		<div class="row mt-3">
            <div class="col">Loading...</div>
        </div>
	{:then people}
        <div class="row mt-3">
            <div class="col">
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Age</th>
                        </tr>
                    </thead>
                    <tbody>
                        {#each people as p}
                            <tr>
                                <td>{p.id}</td>
                                <td>{p.name}</td>
                                <td>{p.age}</td>
                            </tr>
                        {/each}
                    </tbody>
                </table>
            </div>
        </div>
	{/await}
</main>
