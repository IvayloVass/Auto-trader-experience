const deleteBtn = document.getElementById('deleteBook');
const id = document.getElementById('commentId').value
deleteBtn.addEventListener('click', onDeleteComment);


function onDeleteComment(event) {
    event.preventDefault();
    let requestOptions = {
        method: 'POST',
        redirect: 'follow'
    };

    fetch("http://localhost:8080/comment/api/{id}/delete", requestOptions)
        .then(response => response.json())
        .then(result => console.log(result))
        .catch(error => console.log('error', error));
}