class SortableManager {
    constructor(listId) {
        this.listElement = document.getElementById(listId);
        if (!this.listElement) return;

        this.sortable = new Sortable(this.listElement, {
            animation: 150,
            onEnd: () => this.updateIds(),
        });
    }

    updateIds() {
        const listItems = this.listElement.querySelectorAll('li');
        listItems.forEach((item, index) => {
            item.id = `${index + 1}`;
        });
    }
}

document.addEventListener('DOMContentLoaded', () => {
    new SortableManager('destination');
});
