document.getElementById("button").addEventListener("click", () => clickBut());

async function clickBut() {
    let retorno = await Java.test.mensaje({
        mail: 'correo_electrónico',
        password: 'Contraseña'
    });
    console.log(retorno);
}