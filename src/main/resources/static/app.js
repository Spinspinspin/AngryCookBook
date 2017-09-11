const baseurl= 'http://localhost:8080/api/recipes';

function fillInDetails(data) {
	let html = `
		<h1>${data.title}</h1>
		<h2>${data.description}</h2>
		<div> Time: ${data.numberOfMinutes} minutes<div><br>
	`;
	for (let ingredients of data.ingredients) {
		console.log(ingredients);
		html += `
			<div>
				<b>${ingredients.foodItem}</b>
				<b>${ingredients.measureUnit}</b>
				<b>${ingredients.quantity}</b>
				<form class="delete-ingredient-form" method= "post" action= "/api/recipes/${data.id}/ingredients/${ingredients.ingredientId}">
				<button> Delete <button>
				</form>
			</div><br>
		`;
	}
	html += `<h3> Want to add more? </h3>
				<form id= "create-ingredient-form" method= "post" action= "/api/recipes/${data.id}/ingredients">
				<input name "foodItem" id="foodItem" placeholder= "Food item">
				<br>
				<input name="measureUnit" id= "measureUnit" placeholder= "Measure">
				<br>
				<input name= "quantity" id= "quantity" placeholder= "Quantity">
				<br>
				<button> Add ingredient </button>
							
				
			`;
	
	for (let instructions of data.instructions) {
		console.log(instructions)
		html += `
		<div>
		<b>${instructions.step}</b>
		<form class="delete-instruction-form" method= "post" action= "/api/recipes/${data.id}/instructions/${instructions.instructionId}">
				<button> Delete <button>
				</form>
			</div><br>
		`;
	}
	
	html += `
				<form id= "create-instruction-form" methop= "post" action= "/api/recipes/${data.id}/instructions">
				<input name "step" id= "step" placeholder= "Step"><br>
				<button>Add Step <button>
				</form>
			`;
	
	$('#recipe-detail').html(html);
}
	
function createListElement(recipe){
	$('<li></li>')
	.html(`
			<a href="#" data-recipe-id="${recipe.id}">
				${recipe.title}
				</a>
				<form class="delete-recipe-form" method="post" action="/api/recipes/${recipe.id}">
					<button>Delete</button>
					</form>
				
	`)
	.appendTo($('#recipe-list'));
}




$(document).on('submit', '.delete-recipe-form', function (e){
	e.preventDefault();
	
	$.ajax(this.action, { type: 'DELETE' })
		.done(() => {
			$(this)
				.closest('li')
				.remove();
		})
		.fail(error => console.error(error));
});

$(document).on('submit', '.delete-ingredient-form', function (e){
	e.preventDefault();
	console.log(this.action);
	$.ajax(this.action, { type: 'DELETE' })
		.done(() => {
			$(this)
				.closest('div')
				.remove();
		})
		.fail(error => console.error(error));
});

$(document).on('submit', '.delete-instruction-form', function (e){
	e.preventDefault();
	console.log(this.action);
	$.ajax(this.action, { type: 'DELETE' })
		.done(() => {
			$(this)
				.closest('div')
				.remove();
		})
		.fail(error => console.error(error));
});

$('#create-recipe-form').on('submit', function(e){
	e.preventDefault();
	
	let payload = {
			title: $('#title').val(),
			description: $('#description').val(),
			numberOfMinutes: $('#numberOfMinutes').val()
	};
			
	let ajaxOptions = {
		type: 'POST',
		data: JSON.stringify(payload),
		contentType: 'application/json'
	};
	
	$.ajax(this.action, ajaxOptions)
		.done(function (recipe) {
			createListElement(recipe);
		})		
		.fail(error => console.error(error));
});

$(document).on('submit', '#create-ingredient-form', function (e) {
	e.preventDefault();
	
	let payload = {
			foodItem: $('#foodItem').val(),
			measureUnit: $('#measureUnit').val(),
			quantity: $('#quantity').val()
	};
	
	let ajaxOptions = {
			type: 'POST',
			data: JSON.stringify(payload),
			contentType: 'application/json'
	};
	$.ajax(this.action, ajaxOptions)
		.done(function (data) {
			fillInDetails(data);
		})
		.fail(error => console.error(error));
	
});

$(document).on('submit', '#create-instruction-form', function (e) {
	e.preventDefault();
	
	let payload = {
			step: $('#step').val(),
			
	};
	
	let ajaxOptions = {
			type: 'POST',
			data: JSON.stringify(payload),
			contentType: 'application/json'
	};
	$.ajax(this.action, ajaxOptions)
		.done(function (data) {
			fillInDetails(data);
		})
		.fail(error => console.error(error));
	
});

$(document).on('click', 'a[data-recipe-id]', function(e){
	e.preventDefault();
	const recipeId = $(this).data('recipeId');  // camel case conversion happens
												// here!
	
	$.getJSON(baseurl + '/' + recipeId, function (data){

		data.foodItem = data.foodItem || '';   // item default value
		fillInDetails(data);
			
	});
	
});

$.getJSON(baseurl, function (data) {
	if(data.length){
		for (let recipe of data){
			createListElement(recipe);			
		}
	}else {
		
		$('<li></li>')
			.css('color', 'orange')
			.html('You have no data.')
			.appendTo($('#recipe-list'));
	}
	
});
