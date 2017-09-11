const baseurl= 'http://localhost:8080/api/recipes';

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

$(document).on('click', 'a[data-recipe-id]', function(e){
	e.preventDefault();
	const recipeId = $(this).data('recipeId');  //camel case conversion happens here!
	
	$.getJSON(baseurl + '/' + recipeId, function (data){
//		console.log('Data for', recipeId, 'is', data);
		data.foodItem = data.foodItem || '';   //idiom default value 
		$('#recipe-detail')
			.html(`
					<h1>${data.title}</h1> 
					<h2>${data.description}</h2>	
			`)
		
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
