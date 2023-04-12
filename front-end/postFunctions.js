async function postPizza(jsonData){
    const fetchOptions = {
        method : 'POST',
        headers: {
            'Content-Type' : 'application/json'
        },
        body: jsonData
    };

    const response = await fetch(API_URL, fetchOptions);
    return response;
}

async function createPizza(event){
    event.preventDefault();
    //read input values
    const formData = new FormData(event.target);
    const formDataObj = Object.fromEntries(formData.entries());
    
    //Make the whole form's input JSON
    const formDataJson = JSON.stringify(formDataObj);
    console.log(formDataJson);

    const response = await postPizza(formDataJson);
    const responseBody = await response.json;

    if(response.ok){
        form.classList.remove('d-none');
        createForm.classList.add('d-none');
        loadData("");
        event.target.reset();
    }else{
        console.log("ERROR");
        console.log(response.status);
        console.log(responseBody);
    }

}