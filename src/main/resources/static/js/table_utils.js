var selectedItem = -1;

function selectItem(el, className, id) {
    selectedItem = selectedItem === id ? -1 : id;
    if (selectedItem === -1){
        document.getElementById('delete-button').style.display = 'none';
        document.getElementById('edit-button').style.display = 'none';
    }else{
        document.getElementById('delete-button').style.display = 'block';
        document.getElementById('edit-button').style.display = 'block';
    }
    el.classList.toggle(className);
    var parentNode = el.parentElement.children;
    for (var i = 0; i < parentNode.length; i++) {
        if (parentNode[i] !== el)
            parentNode[i].classList.toggle(className, false);
    }
}

function deleteSelected(path) {
    window.location = path + "/delete/" + selectedItem + window.location.search;
}

function editSelected(path) {
    window.location = path + "/" + selectedItem + window.location.search;
}

function create(path){
    window.location = path + "/create" + window.location.search;
}