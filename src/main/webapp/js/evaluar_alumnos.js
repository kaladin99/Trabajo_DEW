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
	$("#btn_left").prop("disabled",false)
	$("#btn_right").prop("disabled",false)
	if (current == 0) $("#btn_left").prop("disabled",true)
	if (current == array.length - 1) $("#btn_right").prop("disabled",true)
}

let cambiarNota = (nota) => {
	$.ajax({
	    type: "put",
	    url: "http://dew-jrevvil-2324.dsicv.upv.es:8080/Trabajo-NOL/cambiar_nota",
	    data: JSON.stringify({dni:alumnos[current_alumno]["dni"],nota:nota,asignatura:asig}),
	    contentType: "application/json; charset=utf-8",
	    success: (res) => {
	         console.log(res)
	     },
	})
}
 
$(document).ready(function() {
	console.log("INPUT VALUE",$("#asignatura").val())
	console.log("ASIG JS VALUE",asig)
	$.ajax({
		 url: 'http://dew-jrevvil-2324.dsicv.upv.es:8080/Trabajo-NOL/alumnos_de_asignatura?asig='+asig,
		 dataType: 'json',
		 success: (data) => {
			console.log(data)
			alumnos = data
			$('#alumno_nombre').text(data[current_alumno]["nombre"] + " " + data[current_alumno]["apellidos"])
			$('#nota').val(data[current_alumno]["nota"])
			disable_buttons(alumnos,current_alumno)
		 }
	})
	
	
	
	$('#btn_left').click(() => {
		if (modificado) cambiarNota($("#nota").val())
		console.log(alumnos)
		console.log("Current alumno antes:",current_alumno)
		console.log("CLICK LEFT")
		current_alumno--
		console.log("Current alumno despues:",current_alumno)
		disable_buttons(alumnos,current_alumno)
		$('#alumno_nombre').text(alumnos[current_alumno]["nombre"] + " " + alumnos[current_alumno]["apellidos"])
		$('#nota').val(alumnos[current_alumno]["nota"])
	});
	
	$('#btn_right').click(() => {
		if (modificado) cambiarNota($("#nota").val())
		console.log(alumnos)
		console.log("Current alumno antes:",current_alumno)
		console.log("CLICK RIGHT")
		current_alumno++
		console.log("Current alumno despues:",current_alumno)
		disable_buttons(alumnos,current_alumno)
		$('#alumno_nombre').text(alumnos[current_alumno]["nombre"] + " " + alumnos[current_alumno]["apellidos"])
		$('#nota').val(alumnos[current_alumno]["nota"])
	});
	
	$("#nota").on( "change", function() {
	  modificado = true
	});
	
	
	
	
	
	
	
	
	
	
})
