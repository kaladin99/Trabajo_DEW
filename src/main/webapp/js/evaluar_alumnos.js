/**
 * 
 */

console.log("ENTRA")
 
let alumnos
let current_alumno = 0
let params = new URLSearchParams(document.location.search);
let asig = params.get("asig");
let modificado = false
let disable_buttons = (array, current) => {
	modificado = false;
	$("#btn_left").prop("disabled",false)
	$("#btn_right").prop("disabled",false)
	if (current == 0) $("#btn_left").prop("disabled",true)
	if (current == array.length - 1) $("#btn_right").prop("disabled",true)
}

let cambiarNota = (nota) => {
	alumnos[current_alumno]["nota"] = nota
	$.ajax({
	    type: "put",
	    url: "http://dew-jrevvil-2324.dsicv.upv.es:8080/Trabajo-NOL/cambiar_nota",
	    data: JSON.stringify({dni:alumnos[current_alumno]["dni"],nota:nota,asignatura:asig}),
	    contentType: "application/json; charset=utf-8",
        success: (res) => {
	        showToast('Nota del cambiada correctamente.', 'success');
	    },
	    error: (jqXHR, textStatus, errorThrown) => {
	        console.error('Error:', textStatus, errorThrown);
	        showToast('Ha ocurrido un error al cambiar la nota.', 'danger');
	    }
	})
}

let showToast = (message, type='success') => {
    const toastContainer = document.getElementById('toast-container');

    const toast = document.createElement('div');
    toast.className = `toast align-items-center text-white bg-${type} border-0`;
    toast.role = 'alert';
    toast.ariaLive = 'assertive';
    toast.ariaAtomic = 'true';

    toast.innerHTML = `
        <div class="d-flex">
            <div class="toast-body">
                ${message}
            </div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
    `;

    toastContainer.appendChild(toast);

    const bsToast = new bootstrap.Toast(toast);
    bsToast.show();

    setTimeout(() => {
        bsToast.hide();
        toast.addEventListener('hidden.bs.toast', () => {
            toastContainer.removeChild(toast);
        });
    }, 5000);
 }
 
$(document).ready(function() {
	let guardado = false
	$.ajax({
		 url: 'http://dew-jrevvil-2324.dsicv.upv.es:8080/Trabajo-NOL/alumnos_de_asignatura?asig='+asig,
		 dataType: 'json',
		 success: (data) => {
			alumnos = data
			$('#alumno_nombre').text(data[current_alumno]["nombre"] + " " + data[current_alumno]["apellidos"])
			$('#nota').val(data[current_alumno]["nota"])
			$('#foto_alumno_base64').attr("src", "data:image/png;base64, "+data[current_alumno]["img"]);
			disable_buttons(alumnos,current_alumno)
		 }
	})
	
	$('#btn_left').click(() => {
		current_alumno--
		disable_buttons(alumnos,current_alumno)
		$('#alumno_nombre').text(alumnos[current_alumno]["nombre"] + " " + alumnos[current_alumno]["apellidos"])
		$('#nota').val(alumnos[current_alumno]["nota"])
		$('#foto_alumno_base64').attr("src", "data:image/png;base64, "+alumnos[current_alumno]["img"]);
	});
	
	$('#btn_right').click(() => {
		current_alumno++
		disable_buttons(alumnos,current_alumno)
		$('#alumno_nombre').text(alumnos[current_alumno]["nombre"] + " " + alumnos[current_alumno]["apellidos"])
		$('#nota').val(alumnos[current_alumno]["nota"])
		$('#foto_alumno_base64').attr("src", "data:image/png;base64, "+alumnos[current_alumno]["img"]);

	});
	
	$("#nota").on( "change", function() {
		if (guardado == false) {
				cambiarNota($("#nota").val())
				guardado = true;
		}
	  	modificado = true
	});
	
	$('#nota').keypress(function() {
		guardado = false;
		//SI EN 5 SEGUNDOS NO CLICA FUERA DEL INPUT PARA QUE SE ACTIVE EL EVENTO ONCHANGE, SE GUARDA AUTOMATICAMENTE
	    setTimeout(()=> {
			if (guardado == false) {
				cambiarNota($("#nota").val())
				guardado = true;
			}
		}, 5000)
	});
	
})
