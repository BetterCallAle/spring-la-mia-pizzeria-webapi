//DOM
function createCard(data){
    return `<div class="col">
                <div class="card">
                    <div class="card-body text-center">
                        <h1>${data.name}</h1>
                        <p>${data.description}</p>
                        <p>${data.price}</p>
                        <div>${createDeleteBtn(data)}</div>
                    </div>
                </div>
            </div>`
}

function getInputValue(){
    const inputValue = document.getElementById('search-input');
    return inputValue.value;
}


async function getPizzas(value){
    let URL = "";
    if(value.length > 0){
        URL = API_URL +'?name=' + value;
    } else {
        URL = API_URL;
    }
    const results = await fetch(URL);
    return results;
}

//LOAD DATA FUNCTION
async function loadData(value){
    const response = await getPizzas(value);
    if(response.ok){
        const data = await response.json();
        row.innerHTML = "";
        data.forEach(pizza => {
            row.innerHTML += createCard(pizza);  
        });
        //DELETE BTNS
        const deleteBtns = document.querySelectorAll('button[data-id]');
        for (let btn of deleteBtns) {
            btn.addEventListener('click', () => {
                deletePizza(btn.dataset.id);
            });
        }
    } else {
        console.log("ERROR");
    }
}