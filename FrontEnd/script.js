function prueba1(form, body) {
    console.log("Prueba de función, obteniendo formulario ruta: " + form.action + "  body:" + JSON.stringify(body));
}

function prueba2(retorno, form, body) {
    console.log(retorno);
}

async function clickBut() {
    let retorno = await Java.test.mensaje({ mail: 'correo_electrónico', password: 'Contraseña' })
    alert(retorno);
}