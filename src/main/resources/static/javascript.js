const baseurl= 'http://localhost:8080/api/recipes';

function fillInDetails(data){
	let html = `
		<h1>${data.title}</h1> 
		<h2>${data.description}</h2>
		`;
	for (let ingredient of data.ingredients){
		html += `
			<div>
				<div>${ingredient.foodItem}</div>
				<div>${ingredient.measureUnit}</div>
				<div>${ingredient.quantity}</div>
				<form class="delete-ingredient-form" method="post" action="/api/recipes/${data.id}/ingredients/${ingredient.ingredientId}">
				<button>delete ingredient</button>
				</form>
			</div>
		`;
		}
	 
		html += `
		<form id="create-ingredient-form" method="post" action="/api/recipes/${data.id}/ingredients">
					<input required name="foodItem" id="foodItem" placeholder="Food Item">
					<br>
					<input name="measureUnit" id="measureUnit" placeholder="Measure Unit">
					<br>
					<input name="quantity" id="quantity" placeholder="Quantity">
					<br>
					<button>Add this Ingredient</button>
		</form>	
		`;
		
	for (let instruction of data.instructions){
		html += `
			<div>
				<div>${instruction.step}</div>
				<form class="delete-instruction-form" method="post" action="/api/recipes/${data.id}/instructions/${instruction.instructionId}">
				<button>delete instruction</button>
				</form>
			</div>
		`;
		}
		 
		html += `
		<form id="create-instruction-form" method="post" action="/api/recipes/${data.id}/instructions">
					<input required name="step" id="step" placeholder="Enter Step">
					<br>
					<button>Add this Instruction</button>
		</form>	
		`;	
	
	$('#recipe-detail').html(html);
}

function createListElement(recipe)	{
	$('<li></li>')
	.html(`
			<a href="#"  data-recipe-id="${recipe.id}">
				${recipe.title}
			</a>
			<form class="delete-recipe-form" method="post" action="/api/recipes/${recipe.id}">
				<button>Delete</button>
			</form>
			`)
	.appendTo($('#recipe-list'));
	
}

$(document).on('submit', '.delete-recipe-form', function(e){
	e.preventDefault();
	
	$.ajax(this.action, {type: 'DELETE' })
		.done(()=>{
			$(this)
				.closest('li')
				.remove();
		})
		.fail(error => console.error(error));
});

function createIngredientElement(ingredient)	{
	$('<li></li>')
	.html(`
			<a href="#"  data-ingredient-id="${ingredient.ingredientId}">
				${ingredient.foodItem}
			</a>
			<form class="delete-ingredient-form" method="post" action="/api/recipes/${recipe.id}/ingredients">
				<button>Delete</button>
			</form>
			`)
	.appendTo($('#recipe-list'));
	
}

$(document).on('submit', '.delete-ingredient-form', function(e){
	e.preventDefault();
	
	$.ajax(this.action, {type: 'DELETE' })
		.done(()=>{
			$(this)
				.closest('div')
				.remove();
		})
		.fail(error => console.error(error));
});

function createInstructionElement(instruction)	{
	$('<li></li>')
	.html(`
			<a href="#"  data-instruction-id="${instruction.instructionId}">
				${instruction.step}
			</a>
			<form class="delete-instruction-form" method="post" action="/api/recipes/${recipe.id}/instructions">
				<button>Delete</button>
			</form>
			`)
	.appendTo($('#recipe-list'));
	
}

$(document).on('submit', '.delete-instruction-form', function(e){
	e.preventDefault();
	
	$.ajax(this.action, {type: 'DELETE' })
		.done(()=>{
			$(this)
				.closest('div')
				.remove();
		})
		.fail(error => console.error(error));
});

$('#create-recipe-form').on('submit', function (e){
	e.preventDefault();

	let payload ={
		title: $('#title').val(),
		description: $('#description').val(),
		numberOfMinutes: $('#numberOfMinutes').val()
		
		
	};
	console.log(payload);
	
	let ajaxOption ={
			type: 'POST',
			data: JSON.stringify(payload),
			contentType: 'application/json'
	};
	
	$.ajax(this.action, ajaxOption)  //selects the url to post 
		.done(function (recipe) {  //success handler
			createListElement(recipe);
		})
		
		.fail(error => console.error(error));
	
});

$(document).on('submit', '#create-ingredient-form', function(e){
	e.preventDefault();
	
	let payload ={
			foodItem: $('#foodItem').val(),
			measureUnit: $('#measureUnit').val(),
			quantity: $('#quantity').val()
	};
//	console.log(payload);
	
	let ajaxOptions = {
			type: 'POST',
			data: JSON.stringify(payload),
			contentType: 'application/json'
	};
	
	$.ajax(this.action, ajaxOptions)
	.done(function (data){
		fillInDetails(data);
	})
	.fail(error => console.error(error))
});


$(document).on('submit', '#create-instruction-form', function(e){
	e.preventDefault();
	
	let payload ={
			step: $('#step').val()
	};
//	console.log(payload);
	
	let ajaxOptions = {
			type: 'POST',
			data: JSON.stringify(payload),
			contentType: 'application/json'
	};
	
	$.ajax(this.action, ajaxOptions)
	.done(function (data){
		fillInDetails(data);
	})
	.fail(error => console.error(error))
});


$(document).on('click', 'a[data-recipe-id]', function(e){
	e.preventDefault();
	const recipeId = $(this).data('recipeId');  //camel case conversion happens here!
	
	$.getJSON(baseurl + '/' + recipeId, function (data){
//		console.log('Data for', recipeId, 'is', data);
		data.foodItem = data.foodItem || '';   //idiom default value 
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
			.css('color', 'red')
			.html('You have no data.')
			.appendTo($('#recipe-list'));
	}
	
});
