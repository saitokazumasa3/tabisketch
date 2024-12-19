class PlaceNum {
    #value = 1;

    value() {
        return this.#value;
    }

    increment() {
        this.#value ++;
    }
}

class SessionStorageList {
    #startPlaceList = new Array(0);
    #endPlaceList = new Array(0);
    #placesList = new Array(0);

    constructor() {
        // Sessionがあった時に配列に入れる
        if (this.getStartData()) this.#startPlaceList = this.getStartData();
        if (this.getEndData()) this.#endPlaceList = this.getEndData();
        if (!this.getPlacesList()) return;

        // sessionから取得
        this.#placesList = this.getPlacesList();
        // sessionのnull|undefinedを除外
        this.#placesList = this.#placesList.filter(place => place !== null && place !== undefined);
        // 再保存して整合性を保つ
        sessionStorage.setItem('place', JSON.stringify(this.#placesList));
    }

    /**
     * 出発地点の情報をsessionに登録
     */
    setStartPlace() {
        this.#startPlaceList = [];
        this.#startPlaceList.push({
            placeId: document.getElementById('startPlaceId').value,
            lat: document.getElementById('startLat').value,
            lng: document.getElementById('startLng').value,
            name: document.getElementById('startPlace').value,
            startTime: document.getElementById('startTime').value
        });

        sessionStorage.setItem('startPlace', JSON.stringify(this.#startPlaceList));
    }

    /**
     * 終了地点の情報をsessionに登録
     */
    setEndPlace() {
        this.#endPlaceList = [];
        this.#endPlaceList.push({
            placeId: document.getElementById('endPlaceId').value,
            lat: document.getElementById('endLat').value,
            lng: document.getElementById('endLng').value,
            name: document.getElementById('endPlace').value
        });

        sessionStorage.setItem('endPlace', JSON.stringify(this.#endPlaceList));
    }

    /**
     * 目的地の情報をsessionに登録
     * @param formNum formの項番
     */
    setPlaces(formNum) {
        this.#placesList[formNum-1] = {
            placeId: document.getElementById(`placeId${formNum}`).value,
            lat: document.getElementById(`placeLat${formNum}`).value,
            lng: document.getElementById(`placeLng${formNum}`).value,
            name: document.getElementById(`place${formNum}`).value,
            budget: document.getElementById(`budget${formNum}`).value,
            stayTime: document.getElementById(`stayTime${formNum}`).value,
            desiredStartTime: document.getElementById(`desiredStartTime${formNum}`).value,
            desiredEndTime: document.getElementById(`desiredEndTime${formNum}`).value,
        };

        sessionStorage.setItem('place', JSON.stringify(this.#placesList));
    }

    /**
     * オススメ目的地をsessionStorage placeに登録
     * @param formNum formの項番
     * @param num オススメ目的地の識別番号
     */
    setRecommendPlace(formNum, num) {
        this.#placesList[formNum-1] = {
            placeId: document.getElementById(`recommendPlaceId${num}`).value,
            lat: document.getElementById(`recommendLat${num}`).value,
            lng: document.getElementById(`recommendLng${num}`).value,
            name: document.getElementById(`recommend${num}`).value,
            budget: document.getElementById(`recommendBudget${num}`).value,
            stayTime: document.getElementById(`recommendStayTime${num}`).value,
            desiredStartTime: document.getElementById(`recommendDesiredStartTime${num}`).value,
            desiredEndTime: document.getElementById(`recommendDesiredEndTime${num}`).value,
        };

        sessionStorage.setItem('place', JSON.stringify(this.#placesList));
    }

    /**
     * 出発地点をsessionから取得
     * @returns {*}
     */
    getStartData() {
        const startPlaceData = sessionStorage.getItem('startPlace');

        if (!startPlaceData) return;
        const startPlaceList = JSON.parse(startPlaceData);
        return startPlaceList[0];
    }

    /**
     * 終了地点をsessionから取得
     * @returns {*}
     */
    getEndData() {
        const endPlaceData = sessionStorage.getItem('endPlace');

        if (!endPlaceData) return;
        const endPlaceList = JSON.parse(endPlaceData);
        return endPlaceList[0];
    }

    /**
     * 目的地をsessionから取得
     * @param num session.placeの項番
     * @returns {*}
     */
    getPlacesData(num) {
        const placeData = sessionStorage.getItem('place');

        if (!placeData) return;
        const placeList = JSON.parse(placeData);
        return placeList[num];
    }

    /**
     * 目的地のsession配列を取得
     * @returns {any}
     */
    getPlacesList() {
        const placesList = sessionStorage.getItem('place');

        if (!placesList) return;
        return JSON.parse(placesList);
    }

    /**
     * 目的地sessionの一部削除
     * @param num 削除したい項番（formNum-1）
     */
    deletePlace(num) {
        this.#placesList[num] = null;
        sessionStorage.setItem('place', JSON.stringify(this.#placesList));
    }
}

class Fragment {
    #value;

    constructor() {
        this.#value = null;
    }

    /**
     * 追加fragmentの初期化
     * @returns {Promise<void>}
     */
    async initialize() {
        try {
            const response = await fetch(`/fragment/modal/places?num=${(placeNum.value()+1)}`);
            if (!response.ok) { throw new Error('フラグメントの取得に失敗しました'); }
            this.#value = await response.text();
        } catch (error) {
            throw new Error('initialize Error : ' + error);
        }
    }

    /**
     * HTMLにfragment追加
     */
    addFragment() {
        if (this.#value === null) throw new Error('このインスタンスは初期化されていません。initialize()を実行してください。');
        // id=destination の子要素に追加
        const container = document.getElementById('destination');
        const item = document.createElement('div');
        item.innerHTML = this.#value;
        container.appendChild(item);
    }

    /**
     * 追加するfragment取得
     * @returns {*}
     */
    value() {
        if (this.#value === null) throw new Error('このインスタンスは初期化されていません。initialize()を実行してください。');
        return this.#value;
    }
}

class ModalElement {
    #modals;
    #toggleButtons;
    #closeButtons;

    constructor() {
        this.#modals = {
            start: document.getElementById('startModal'),
            end: document.getElementById('endModal'),
            places: [document.getElementById(`placeModal${placeNum.value()}`)],
        };

        this.#toggleButtons = {
            start: document.getElementById('startToggle'),
            end: document.getElementById('endToggle'),
            places: [document.getElementById(`placeToggleBtn${placeNum.value()}`)],
        };

        this.#closeButtons = {
            start: document.getElementById('startClose'),
            end: document.getElementById('endClose'),
            places: [document.getElementById(`placeCloseBtn${placeNum.value()}`)],
        };
    }

    /**
     * 目的地のelementを配列に追加・autocomplete適用
     */
    addPlacesElement() {
        const modal = document.getElementById(`placeModal${placeNum.value()}`);
        const toggleButton = document.getElementById(`placeToggleBtn${placeNum.value()}`);
        const closeButton = document.getElementById(`placeCloseBtn${placeNum.value()}`);

        this.#modals.places.push(modal);
        this.#toggleButtons.places.push(toggleButton);
        this.#closeButtons.places.push(closeButton);

        const inputElement = document.getElementById(`place${placeNum.value()}`);
        const autoComplete = new AutoComplete(inputElement);
        autoCompleteList.add(autoComplete);
    }

    /**
     * ModalButtonイベント アタッチ
     * @param modalType (start,end,places)が入る
     * @Param num formId-1
     */
    addButtonEvent(modalType, num) {
        const modal = this.#getModal(modalType, num);
        const toggleBtn = this.#getToggleBtn(modalType, num);
        const closeBtn = this.#getCloseBtn(modalType);

        // イベントのアタッチ
        toggleBtn.addEventListener('click', () => modal.toggle() );
        closeBtn.addEventListener('click', () => {
            modal.hide();
            document.activeElement.blur(); // フォーカスを外す
        });
    }

    /**
     * modal取得
     * @param modalType {'places','start','end'} モーダルの種別
     * @param num formNum-1
     * @returns {Modal}
     */
    #getModal(modalType, num) {
        if (modalType === 'places') {
            return new Modal(this.#modals[modalType][num]);
        }
        return new Modal(this.#modals[modalType]);
    }

    /**
     * toggle取得
     * @param modalType {'places','start','end'} モーダルの種別
     * @param num formNum-1
     * @returns {*}
     */
    #getToggleBtn(modalType, num) {
        if (modalType === 'places') {
            return this.#toggleButtons.places[num];
        }
        return this.#toggleButtons[modalType];
    }

    /**
     * close取得
     * @param modalType {'places','start','end'} モーダルの種別
     * @returns {*}
     */
    #getCloseBtn(modalType) {
        if (modalType === 'places') {
            return this.#closeButtons.places[placeNum.value()-1];
        }
        return this.#closeButtons[modalType];
    }

    /**
     * modalを閉じる
     * @param modalType {'places','start','end'} モーダルの種別
     * @param num modalListのnumber(form項番-1)
     */
    closeModal(modalType, num) {
        const modal = this.#getModal(modalType, num);
        modal.hide();
    }

    /**
     * 出発地点の表示を変更
     */
    changeStartDisplay() {
        const spans = document.querySelectorAll('#startToggle span'); // spanタグ取得

        const data = sessionStorageList.getStartData();
        spans[0].textContent = data.startTime; // 開始時間を入れる
        spans[1].textContent = data.name; // spanの文字を場所名に
        spans[0].classList.remove('absolute');
    }

    /**
     * 終了地点の表示を変更
     */
    changeEndDisplay() {
        const span = document.querySelector('#endToggle span'); // spanタグ取得

        span.textContent = sessionStorageList.getEndData().name; // spanの文字を場所名に
    }

    /**
     * 目的地の表示を変更
     * @param formNum formの項番
     */
    changePlaceDisplay(formNum) {
        // buttonの子要素のspanタグ取得
        const spans = document.querySelectorAll(`#placeToggleBtn${formNum} > span`);
        const placeInput = document.getElementById(`place${formNum}`);

        placeInput.disabled = true; // 目的地部分をdisabledに
        placeInput.classList.add('bg-gray-100');

        const data = sessionStorageList.getPlacesData(formNum-1);

        // 場所名
        spans[1].textContent = data.name;

        // 希望時間
        if (!data.desiredStartTime) spans[0].textContent = '';
        else spans[0].textContent = data.desiredStartTime + '~' + data.desiredEndTime;
        spans[0].classList.remove('absolute');

        // 予算
        const budget = document.getElementById(`budgetDisplay${formNum}`);
        if (!data.budget) budget.textContent = '予算：----' + '円';
        else budget.textContent = '予算：' + data.budget + '円';

        // 滞在時間
        const stayTime = document.getElementById(`stayTimeDisplay${formNum}`);
        if (!data.stayTime) stayTime.textContent = '滞在時間：30分';
        else stayTime.textContent = '滞在時間：' + data.stayTime + '分';

        // 緑色の枠線をけす
        const toggleBtn = this.#getToggleBtn('places', formNum-1);
        toggleBtn.classList.remove('border-interactive');

        this.#displayDeleteButton(formNum);
    }

    /**
     * ✕ボタンの表示
     * @param formNum formの項番
     */
    #displayDeleteButton(formNum) {
        const deleteBtn = document.getElementById(`modalDeleteBtn${formNum}`);
        deleteBtn.classList.remove('hidden');

        deleteBtn.addEventListener('click', () => {
            const toggleBtn = this.#getToggleBtn('places', formNum-1);
            toggleBtn.classList.add('hidden'); // Modal表示のボタンを隠す
            deleteBtn.classList.add('hidden'); // ✕ボタンを隠す

            // sessionから消す
            sessionStorageList.deletePlace(formNum-1);
        });
    }
}

class ModalForm {
    #startFormElement;
    placeFormElements = [];
    #endFormElement;

    constructor() {
        this.#startFormElement = document.getElementById('startPlaceForm');
        for (let i = 1; i <= placeNum.value(); i++) {
            this.placeFormElements.push(document.getElementById(`placeForm${i}`));
        }
        this.#endFormElement = document.getElementById('endPlaceForm');
        this.initFormEvent();
    }

    initFormEvent() {
        if (!this.#startFormElement || !this.placeFormElements || !this.#endFormElement) return;
        this.#startFormElement.addEventListener('submit', (e) => this.#startFormSubmit(e) );
        this.#endFormElement.addEventListener('submit', (e) => this.#endFormSubmit(e) );
        this.placeFormElements.forEach((element) => element.addEventListener('submit', async(e) => await this.#placesFormSubmit(e)));
    }

    /**
     * 出発地点のsubmitイベント
     * @param e イベント
     */
    #startFormSubmit(e) {
        e.preventDefault();

        sessionStorageList.setStartPlace();

        const modalType = 'start';
        modal.closeModal(modalType, 0);
        modal.addButtonEvent(modalType, 0);

        modal.changeStartDisplay(); // 表示を変える
    }

    /**
     * 終了地点のsubmitイベント
     * @param e イベント
     */
    #endFormSubmit(e) {
        e.preventDefault();

        sessionStorageList.setEndPlace();

        const modalType = 'end';
        modal.closeModal(modalType, 0);
        modal.addButtonEvent(modalType, 0);

        modal.changeEndDisplay(); // 表示を変える
    }

    /**
     * 目的地のsubmitイベント
     * @param e イベント
     * @returns {Promise<void>}
     */
    async #placesFormSubmit(e) {
        e.preventDefault();

        const formId = e.target.id; // formのid取得
        const formNum = Number(formId.replace('placeForm', '')); // placeForm${num}の数字だけ取得

        sessionStorageList.setPlaces(formNum);

        // modal設定
        const modalType = 'places';
        modal.closeModal(modalType, formNum-1);
        modal.changePlaceDisplay(formNum);
        modal.addButtonEvent(modalType, formNum-1); // 送信したmodalのイベント再アタッチ

        if(formNum !== placeNum.value()) return; // 目的地再設定はreturn

        await this.newAddFragment();
    };

    /**
     * 追加フラグメントを挿入
     * @returns {Promise<void>}
     */
    async newAddFragment() {
        const newFragment = new Fragment();
        await newFragment.initialize();

        if (!newFragment.value()) return; // 取得できなかったとき

        newFragment.addFragment();
        placeNum.increment();
        modal.addPlacesElement();
        modal.addButtonEvent('places', placeNum.value()-1); // 新しいModalにイベント追加
        new ModalForm(); // modalFormイベントをアタッチ
    }
}

/**
 * sessionに値がある時の初期設定用Class
 */
class InitSessionModals {
    #startData;
    #endData;
    #placesData;

    constructor() {
        this.#startData = sessionStorageList.getStartData();
        this.#endData = sessionStorageList.getEndData();
        this.#placesData = sessionStorageList.getPlacesList();
    }

    async initialize() {
        // 開始地点がある場合
        if (this.#startData) {
            modal.changeStartDisplay();
            this.#setStartFormValue();
        }

        // 終了地点がある時
        if (this.#endData) {
            modal.changeEndDisplay();
            this.#setEndFormValue();
        }

        // 目的地がある時
        if (this.#placesData && this.#placesData.length > 0) {
            await this.#initializePlaces();
        }
    }

    /**
     * 出発地点inputにsessionの値をvalueで挿入
     */
    #setStartFormValue() {
        document.getElementById('startPlaceId').value = this.#startData.placeId;
        document.getElementById('startLat').value = this.#startData.lat;
        document.getElementById('startLng').value = this.#startData.lng;
        document.getElementById('startPlace').value = this.#startData.name;
        document.getElementById('startTime').value = this.#startData.startTime;
    }

    /**
     * 終了地点inputにsessionの値をvalueで挿入
     */
    #setEndFormValue() {
        document.getElementById('endPlaceId').value = this.#endData.placeId;
        document.getElementById('endLat').value = this.#endData.lat;
        document.getElementById('endLng').value = this.#endData.lng;
        document.getElementById('endPlace').value = this.#endData.name;
    }

    /**
     * 目的地inputにsessionの値をvalueで挿入
     * @param formNum formIDの項番
     * @param num session.placeの項番
     */
    #setPlaceFormValue(formNum ,num) {
        document.getElementById(`placeId${formNum}`).value = this.#placesData[num].placeId;
        document.getElementById(`placeLat${formNum}`).value = this.#placesData[num].lat;
        document.getElementById(`placeLng${formNum}`).value = this.#placesData[num].lng;
        document.getElementById(`place${formNum}`).value = this.#placesData[num].name;

        if (this.#placesData[num].budget)
            document.getElementById(`budget${formNum}`).value = this.#placesData[num].budget;

        if (this.#placesData[num].stayTime)
            document.getElementById(`stayTime${formNum}`).value = this.#placesData[num].stayTime;
        else
            document.getElementById(`stayTime${formNum}`).value = 30;

        if (this.#placesData[num].desiredStartTime)
            document.getElementById(`desiredStartTime${formNum}`).value = this.#placesData[num].desiredStartTime;

        if (this.#placesData[num].desiredEndTime)
            document.getElementById(`desiredEndTime${formNum}`).value = this.#placesData[num].desiredEndTime;
    }

    /**
     * 目的地の設定
     * inputにvalue
     * fragment追加
     * placeNum合わせる
     * @returns {Promise<void>}
     */
    async #initializePlaces() {
        // 無効なデータ（nullやundefined）を除外
        this.#placesData = this.#placesData.filter(place => place !== null && place !== undefined);
        for (let i = 0; i < this.#placesData.length; i++) {
            modal.changePlaceDisplay(i+1);
            this.#setPlaceFormValue(i+1, i);

            // 新規フラグメント呼び出し
            const newFragment = new Fragment();
            await newFragment.initialize();

            if (!newFragment.value()) return;

            newFragment.addFragment();
            if (i !== 0) modal.addButtonEvent('places', i);
            placeNum.increment();
            modal.addPlacesElement();
            if (i !== 0 && i === this.#placesData.length-1) modal.addButtonEvent('places', i+1);
        }
    }
}

const placeNum = new PlaceNum();
const sessionStorageList = new SessionStorageList();
const modal = new ModalElement();

async function initModal() {
    const initializeDisplay = new InitSessionModals();
    await initializeDisplay.initialize();
    new ModalForm();
}

initModal();
