console.log("hola");

document.getElementById("button").addEventListener("click", () => call({
    route: 'Validación/validate',
    mail: 'correo_electrónico',
    password: 'Contraseña',
}));



async function call(dataIN, retornoFunt = (e) => { console.log(e); }) {
    let retorno;
    await fetch("../Kernel/connect.php", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(dataIN),
    }).then(response => response.text()).then(data => {
        retornoFunt(data);
        retorno = data;
    }).catch(err => console.error(err));
    return retorno;
};