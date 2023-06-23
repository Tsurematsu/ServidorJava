document.getElementById("button").addEventListener("click", () => clickBut());

async function clickBut() {
    let retorno = await Java.paquete.prueba.mensaje({
        mail: 'correo_electrónico',
        password: 'Contraseña'
    });
    alert(retorno);
}