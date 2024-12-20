class RadioButtonSelectSVG {
    constructor(radioButton,svg) {
        this.radioButtons = document.querySelectorAll(radioButton);
        this.svg = document.querySelectorAll(svg);
        this.init();
    }

    init() {
        this.radioButtons.forEach(radio => {
            radio.addEventListener('change', () => this.changeSvg());
        });
    }

    changeSvg() {
        this.svg.forEach(span => {
            span.classList.add('hidden');
        });

        const selectedValue = document.querySelector('input[name="fourTransportation"]:checked').value;

        const svgToShow = document.getElementById(selectedValue);
        if (!svgToShow) return;
        svgToShow.classList.remove('hidden');
    }
}

document.addEventListener('DOMContentLoaded', () => {
    new RadioButtonSelectSVG('input[name="fourTransportation"]','span[id]');
});
