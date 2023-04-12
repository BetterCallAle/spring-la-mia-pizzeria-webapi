let API_URL = 'http://localhost:8080/api/v.1/pizzas';
const appWrapper = document.getElementById('app');
const searchInput = document.getElementById('search-input');
const row = document.querySelector('.row');
const form = document.getElementById('search-form')
const createForm = document.getElementById('create-form')
const homeBtn = document.getElementById('home-btn');


loadData("");

//ADD ELEMENT
const addElement = document.getElementById('add-element');
addElement.addEventListener("click", (e)=>{
    e.preventDefault();
    row.innerHTML = "";
    form.classList.add('d-none');
    createForm.classList.remove('d-none');
    createForm.addEventListener("submit", createPizza);
})

//HOMEPAGE
homeBtn.addEventListener("click", (e)=>{
    e.preventDefault();
    form.classList.remove('d-none');
    createForm.classList.add('d-none');
    loadData("");
})


//FILTER
form.addEventListener("submit", (e)=> {
    e.preventDefault();
    console.log(searchInput.value);
    loadData(searchInput.value);
})