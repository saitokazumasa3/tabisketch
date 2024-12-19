class PreviewImage {
    constructor(inputId, previewId, buttonId) {
        this.imageInput = document.getElementById(inputId);
        this.imagePreview = document.getElementById(previewId);
        this.previewButton = document.getElementById(buttonId);
        this.init();
    }

    init() {
        this.previewButton.addEventListener('click', () => this.showImage());
    }

    showImage() {
        const file = this.imageInput.files[0];
        if (!file) {
            this.imagePreview.classList.add('hidden');
            return;
        }

        const reader = new FileReader();
        reader.onload = (e) => {
            this.imagePreview.src = e.target.result;
            this.imagePreview.classList.remove('hidden');
        };
        reader.readAsDataURL(file);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    new PreviewImage('image', 'imagePreview', 'previewButton');
});
