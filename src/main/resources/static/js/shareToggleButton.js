class ToggleButton {
    constructor(toggleId, sendUrl) {
        this.toggleButton = document.getElementById(toggleId);
        this.sendUrl = sendUrl;

        if (!this.toggleButton) return;

        this.initialize();
    }

    initialize() {
        this.toggleButton.addEventListener('change', () => this.toggleChange());
    }

    async toggleChange() {
        try{
            const isPublic = this.toggleButton.checked;
            await fetch(`${this.sendUrl}?is_public=${isPublic}`);
        }catch (error){
            console.log(error);
        }
    }
}

document.addEventListener('DOMContentLoaded', () => {
    //TODO:sendUrlをcontroller側のis_publicを変更する時のUrlに変える
    new ToggleButton('toggleButton', '/onodera/public');
});
