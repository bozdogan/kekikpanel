const admin_label = "Yönetici";
const user_label = "Kullanıcı";

function userComponent(username, isAdmin) {
    const el = document.createElement("div");

    const checkbox_label = document.createElement("label");
    checkbox_label.setAttribute("for", `${username}-isAdmin`);
    checkbox_label.className = "ps-1";
    checkbox_label.innerHTML = isAdmin ? admin_label : user_label;

    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    checkbox.id = `${username}-isAdmin`;
    checkbox.className = "ms-3";
    checkbox.checked = !!isAdmin;
    checkbox.addEventListener("click", async (e) => {
        e.preventDefault();
        const user = await api.getOneUser(username);
        console.log("modifyUser:: ", await api.modifyUser(user.username, {
            username: user.username,
            password: user.pwHash,
            isAdmin: !user.admin
        }));

        const isAdminNow = (await api.getOneUser(username)).admin;

        e.target.checked = isAdminNow;
        checkbox_label.innerHTML = isAdminNow ? admin_label : user_label;
        console.log(isAdminNow);
    });

    const deleteBt = document.createElement("button");
    deleteBt.id = `${username}-deleteBt`;
    deleteBt.className = "btn-sm btn-outline-danger ms-3";
    deleteBt.innerHTML = "Sil";
    deleteBt.addEventListener("click", async (e) => {
        e.preventDefault();
        await api.deleteUser(username);
        el.remove();
    });

    const usernameLb = document.createElement("div");
    usernameLb.innerHTML = username;
    usernameLb.className = "col-4";

    const rightSide = document.createElement("div");
    rightSide.className = "col-8 d-block text-end";
    rightSide.appendChild(checkbox);
    rightSide.appendChild(checkbox_label);
    rightSide.appendChild(deleteBt);

    const para = document.createElement("p");
    para.className = "row mb-0";
    para.appendChild(usernameLb);
    para.appendChild(rightSide);
    
    el.id = `user-${username}`;
    el.className = "p-2 my-3 bg-white border";
    el.appendChild(para);
    return el;
}

function noteComponent(noteID, noteBody, username, dateCreated) {
    const el = document.createElement("div");

    const bodyLb = document.createElement("p");
    bodyLb.innerHTML = noteBody;

    const usernameLb = document.createElement("span");
    usernameLb.className = "small"
    usernameLb.innerHTML = username;

    const dateLb = document.createElement("span");
    dateLb.innerHTML = ", " + dateCreated;

    const deleteBt = document.createElement("button");
    deleteBt.id = `note-${noteID}-deleteBt`;
    deleteBt.className = "d-block ms-auto btn-sm btn-outline-danger ms-3";
    deleteBt.innerHTML = "Sil";
    deleteBt.addEventListener("click", async (e) => {
        e.preventDefault();
        await api.deleteNote(noteID);
        el.remove();
    });

    el.id = `note-${noteID}`;
    el.className = "p-2 my-3 bg-white border";
    el.appendChild(bodyLb);
    el.appendChild(usernameLb);
    el.appendChild(dateLb);
    el.appendChild(deleteBt);
    return el;
}

async function loadUsers() {
    const users = await api.getUsers();
    
    usersPanel.innerHTML = "";
    users.forEach(it => {
        usersPanel.appendChild(userComponent(it.username, it.admin));
    });

    console.log(users);
}

async function loadNotes() {
    const notes = await api.getNotes();

    notesPanel.innerHTML = "";
    notes.forEach(it => {
        notesPanel.appendChild(noteComponent(
                it.id,
                it.body,
                it.owner.username,
                it.dateCreated
        ));
    });

    console.log(notes);
}


window.onload = async function(event) {
    await loadUsers();
    await loadNotes();
    userCreateForm.addEventListener("submit", async(e) => {
        e.preventDefault();

        const userData = {
            username: document.getElementById("newUserUsernameTx").value,
            password: document.getElementById("newUserPasswordTx").value,
            isAdmin: document.getElementById("newUserAdminCb").checked,
        };
        
        console.log(JSON.stringify(userData));
        const response = await api.createUser(userData);
        console.log(response);

        await loadUsers();
    })
};
