/**
 * 
 */

 console.log("ENTRA")
 
 $(document).ready(function() {
 	$.ajax({
		 url: 'https://api.thecatapi.com/v1/images/search',
		 dataType: 'json',
		 success: (data) => {
		 let estegato=data[0]
		 $('#gatos').append('<img src="'+estegato.url+'" alt="Foto de '+estegato.id+'"class="fotogato" />')}
	})
	
	$.ajax({
		 url: 'http://dew-jrevvil-2324.dsicv.upv.es:8080/Trabajo-NOL/alumnos_de_asignatura?asig=DEW',
		 dataType: 'json',
		 success: (data) => {
			 console.log(data)
		 $('#alumnos').append(data)}
	})
})
