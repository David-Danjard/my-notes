// functions
function deleteNote(noteId) {
    console.log("Delete note " + noteId);
    $.ajax({
        type: 'DELETE',
        url: getUrl('api/notes/'+noteId),
        timeout: 3000,
        headers: getCsrfHeader(),
        success: function(data) {
            window.location.reload(true);
        },
        error: function(err) {
            console.log("ERROR : "+JSON.stringify(err));
            alert('La requête n\'a pas abouti');
        }
    });
}

function updateNote(noteId) {
    const cardBodyEl = document.querySelector('#card-body-'+noteId);
    const cardTitle = cardBodyEl.querySelector(".card-title").innerText;
    const cardContent = cardBodyEl.querySelector(".card-content").innerText;

    const cardTitleInput = document.createElement('input');
    cardTitleInput.value = cardTitle;
    const cardContentInput = document.createElement('textarea');
    cardContentInput.innerText = cardContent;
    const cardValidateUpdateBtn = document.createElement('p');
    cardValidateUpdateBtn.classList.add('card-text');
    cardValidateUpdateBtn.classList.add('text-right');
    cardValidateUpdateBtn.innerHTML = '<i class="fas fa-check" title="valider la modification" onclick="validateUpdateNote('+noteId+')"></i>';

    cardBodyEl.innerHTML = '';
    cardBodyEl.appendChild(cardTitleInput);
    cardBodyEl.appendChild(cardValidateUpdateBtn);
    cardBodyEl.appendChild(cardContentInput);

    cardContentInput.style.height = cardContentInput.scrollHeight + 'px';
}

function validateUpdateNote(noteId) {
    const cardBodyEl = document.querySelector('#card-body-'+noteId);
    const cardTitle = cardBodyEl.querySelector("input").value;
    const cardContent = cardBodyEl.querySelector("textarea").value;

    $.ajax({
        type: 'PUT',
        url: getUrl('api/notes/update'),
        headers: getCsrfHeader(),
        data: {
            'id': noteId,
            'title': cardTitle,
            'content': cardContent
        },
        timeout: 3000,
        success: function(data) {
            window.location.reload(true);
        },
        error: function(err) {
            console.log("ERROR : "+JSON.stringify(err));
            alert('La requête n\'a pas abouti');
        }
    });
}

function sendNote(noteId) {
    const cardBodyEl = document.querySelector('#card-body-'+noteId);
    const cardMailForm = cardBodyEl.querySelector('.card-mail-form');
    const cardMenu = cardBodyEl.querySelector('.card-menu');

    cardMailForm.style.display = "block";
    cardMenu.style.display = "none";
}

function sendShareRequest(noteId) {
    const cardBodyEl = document.querySelector('#card-body-'+noteId);
    const cardMailFormSelect = cardBodyEl.querySelector('.card-mail-form > select');
    const userId = cardMailFormSelect.value;

    $.ajax({
        type: 'GET',
        url: getUrl('api/notes/share/'+noteId+'/'+userId),
        timeout: 3000,
        success: function(data) {
            window.location.reload(true);
        },
        error: function(err) {
            console.log("ERROR : "+JSON.stringify(err));
            alert('La requête n\'a pas abouti');
        }
    });
}

function aboardShareRequest(noteId) {
    const cardBodyEl = document.querySelector('#card-body-'+noteId);
    const cardMailForm = cardBodyEl.querySelector('.card-mail-form');
    const cardMenu = cardBodyEl.querySelector('.card-menu');

    cardMailForm.style.display = "none";
    cardMenu.style.display = "block";
}

function saveNote() {
    const noteTitleEl = document.querySelector('#add-note-title');
    const noteContentEl = document.querySelector('#add-note-content');

    $.ajax({
        type: 'POST',
        url: getUrl('api/notes/add'),
        headers: getCsrfHeader(),
        data: {
            'title': noteTitleEl.value,
            'content': noteContentEl.value
        },
        timeout: 3000,
        success: function(data) {
            window.location.reload(true);
        },
        error: function(err) {
            console.log("ERROR : "+JSON.stringify(err));
            alert('La requête n\'a pas abouti');
        }
    });
}

function getUrl(path) {
    const url = window.location.protocol + '//' + window.location.hostname + ':' + window.location.port + '/' + path;
    console.log("URL = "+url);
    return url;
}

function getCsrfHeader() {
    // constants
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");
    const headers = {};
    headers[header] = token;
    return headers;
}