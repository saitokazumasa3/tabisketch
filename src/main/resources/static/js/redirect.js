function redirect(action) {
    const form = document.getElementById('planForm');
    form.action = action;
    form.submit();
}

function handleWalkFlagChange() {
    const walkFlag = document.getElementById('walkFlag');
    if (!walkFlag.checked) {
        redirect('/plan/edit');
    }
}
