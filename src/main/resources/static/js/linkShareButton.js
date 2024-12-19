class CopyLinkHandler {
    constructor(copyButtonId, resultElementId) {
        this.copyButton = document.getElementById(copyButtonId);
        this.resultElement = document.getElementById(resultElementId);

        if (this.copyButton) {
            this.copyButton.addEventListener('click', () => this.copyToClipboard());
        }
    }
    // TODO:DBのリンク共有可能かどうかの状態を取得する。
    copyToClipboard() {
        const currentUrl = window.location.href;

        navigator.clipboard.writeText(currentUrl)
            .then(() => {
                this.showResultMessage();
            })
            .catch((err) => {
                console.error('リンクのコピーに失敗しました:', err);
            });
    }

    showResultMessage() {
        if (!this.resultElement) return;
        this.resultElement.classList.remove('opacity-0', 'invisible');
        this.resultElement.classList.add('opacity-100', 'visible');

        setTimeout(() => {
            this.resultElement.classList.add('opacity-0', 'invisible');
            this.resultElement.classList.remove('opacity-100', 'visible');
        }, 2000);
    }
}

class VisibilityToggle {
    constructor(toggleButton, copyLinkButton) {
        this.toggleButton = document.getElementById(toggleButton);
        this.copyLinkButton = document.getElementById(copyLinkButton);

        if (!this.toggleButton) return;
        this.toggleButton.addEventListener('change', () => this.toggleVisibility());
    }

    toggleVisibility() {
        if (this.toggleButton.checked) {
            this.showElement();
            return;
        }
        this.hideElement();
    }

    showElement() {
        if (!this.copyLinkButton) return;
        this.copyLinkButton.classList.remove('opacity-0', 'invisible');
        this.copyLinkButton.classList.add('opacity-100', 'visible');
    }

    hideElement() {
        if (!this.copyLinkButton) return;
        this.copyLinkButton.classList.add('opacity-0', 'invisible');
        this.copyLinkButton.classList.remove('opacity-100', 'visible');
    }
}

document.addEventListener('DOMContentLoaded', () => {
    new CopyLinkHandler('copyLinkButton', 'copyResultElement');
    new VisibilityToggle('toggleButton', 'copyLinkButton');
});
