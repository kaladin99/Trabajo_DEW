/**
 * 
 */

 console.log("ENTRA")
 
 let alumnos
 let current_alumno = 0
 
 let disable_buttons = (array, current) => {
	
	console.log(array)
	console.log(current)
	
	$("btn_left").prop("disabled",false)
	$("btn_right").prop("disabled",false)
	
	if (current == 0) {
		$("btn_left").prop("disabled",true)
	}
	
	if (current == array.length - 1) {
		$("btn_right").prop("disabled",true)
	}
 }
 
 $(document).ready(function() {
	$.ajax({
		 url: 'http://dew-jrevvil-2324.dsicv.upv.es:8080/Trabajo-NOL/alumnos_de_asignatura?asig=DEW',
		 dataType: 'json',
		 success: (data) => {
			console.log(data)
			alumnos = data
			$('#alumno_nombre').text(data[current_alumno]["nombre"] + " " + data[current_alumno]["apellidos"])
			$('#nota').text(data[current_alumno]["nota"] == "" ? "Sin calificar" : data[current_alumno]["nota"])
		 }
	})
	
	
	
	$('#btn_left').click(() => {
		console.log(alumnos)
		console.log("Current alumno antes:",current_alumno)
		console.log("CLICK LEFT")
		current_alumno--
		console.log("Current alumno despues:",current_alumno)
		disable_buttons(alumnos,current_alumno)
		$('#alumno_nombre').text(alumnos[current_alumno]["nombre"] + " " + alumnos[current_alumno]["apellidos"])
		$('#nota').text(alumnos[current_alumno]["nota"] == "" ? "Sin calificar" : alumnos[current_alumno]["nota"])
	});
	
	$('#btn_right').click(() => {
		console.log(alumnos)
		console.log("Current alumno antes:",current_alumno)
		console.log("CLICK RIGHT")
		current_alumno++
		console.log("Current alumno despues:",current_alumno)
		disable_buttons(alumnos,current_alumno)
		$('#alumno_nombre').text(alumnos[current_alumno]["nombre"] + " " + alumnos[current_alumno]["apellidos"])
		$('#nota').text(alumnos[current_alumno]["nota"] == "" ? "Sin calificar" : alumnos[current_alumno]["nota"])
	});
	
	
	
	
	
	
	
	
	
	
})
