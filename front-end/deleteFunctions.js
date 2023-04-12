async function deletePizzaById (pizzaId) {
    const response = await fetch(API_URL + '/' + pizzaId, { method: 'DELETE' });
    return response;
};

function createDeleteBtn(pizza) {
     return `<button data-id="${pizza.id}" class="btn btn-danger">
                    Delete
            </button>`;
};

async function deletePizza(pizzaId){
    const response = await deletePizzaById(pizzaId);

    if(response.ok){
        loadData("");
    } else {
        console.log("ERROR");
        console.log(response.status);
    }
}