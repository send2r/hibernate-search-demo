function checkAll(it, field){
	if(field.legth == 0){
		field.checked = it.checked;
	}else {
		for (i = 0; i < field.length; i++){
			field[i].checked = it.checked ;
		}
	}

}
function goURL(url){
	document.location = url; 
}
function processString(it){
	it.searchString.value=Url.encode(it.searchString.value);
	return true;
}

function openForm(id){
	var form = document.getElementById(id);
	form.style.display = "";
}

function showForm(){
	var element = document.getElementById("uploadFile");
	element.style.display = "block";
}