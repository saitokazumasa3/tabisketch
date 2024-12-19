document.addEventListener('DOMContentLoaded', function() {
    const walkFlagCheckbox = document.getElementById('walkFlag');
    const walkingTimeInput = document.getElementById('walkingTime');
    if (walkFlagCheckbox.checked) {
        walkingTimeInput.disabled = false;
    }
});
