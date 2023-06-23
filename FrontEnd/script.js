function prueba1(form, body) {
    console.log("Prueba de función");
    console.log("ruta: " + form.action);
    console.log(body);
}

function prueba2(retorno, form, body) {
    console.log("Prueba de función retorno");
    console.log(retorno);
}

function prueba3(retorno, form, body) {
    console.log("Prueba de función retorno");
    console.log(retorno);
}

document.getElementById("button").addEventListener("click", () => clickBut());

async function clickBut() {
    let retorno = await Java.test.mensaje({ mail: 'correo_electrónico', password: 'Contraseña' })
    alert(retorno);
}