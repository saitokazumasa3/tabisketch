class PlaceNum {
    #value = 0;

    value() {
        return this.#value;
    }

    increment() {
        this.#value ++;
    }
}

class Fragment {
    #toggle;
    #form;
    #startUpdateForm;
    #placesUpdateForm;

    constructor() {
        this.#toggle = null;
        this.#form = null;
        this.#startUpdateForm = null;
        this.#placesUpdateForm = null;
    }

    /**
     * 追加fragmentの初期化
     * @returns {Promise<void>}
     */
    async initialize() {
        // toggle取得 /fragment/modal/placesToggle
        try {
            const response = await fetch(`/fragment/modal/placesToggle?num=${(placeNum.value()+1)}`);
            if (!response.ok) { throw new Error('フラグメントの取得に失敗しました'); }
            this.#toggle = await response.text();
        } catch (error) {
            throw new Error('initialize Error : ' + error);
        }
        // form取得 /fragment/modal/placesForm
        try {
            const response = await fetch(`/fragment/modal/placesForm?num=${(placeNum.value()+1)}`);
            if (!response.ok) { throw new Error('フラグメントの取得に失敗しました'); }
            this.#form = await response.text();
        } catch (error) {
            throw new Error('initialize Error : ' + error);
        }
        // 出発地点更新form取得 /fragment/modal/startUpdateForm
        try {
            const response = await fetch('/fragment/modal/startUpdateForm');
            if (!response.ok) { throw new Error('フラグメントの取得に失敗しました'); }
            this.#startUpdateForm = await response.text();
        } catch (error) {
            throw new Error('initialize Error : ' + error);
        }
        // 目的地更新form取得 /fragment/modal/placesUpdateForm
        try {
            const response = await fetch('/fragment/modal/placesUpdateForm');
            if (!response.ok) { throw new Error('フラグメントの取得に失敗しました'); }
            this.#placesUpdateForm = await response.text();
        } catch (error) {
            throw new Error('initialize Error : ' + error);
        }
    }

    /**
     * HTMLにfragment追加 toggleとform
     */
    addFragment() {
        if (!this.#toggle || !this.#form) throw new Error('このインスタンスは初期化されていません。initialize()を実行してください。');

        // id=destination の子要素にToggleを追加
        const destination = document.getElementById('destination');
        const newToggle = document.createElement('div');
        newToggle.innerHTML = this.#toggle;
        destination.appendChild(newToggle);

        // id=formDiv の子要素に Form を追加
        const formDiv = document.getElementById('formDiv');
        const newForm = document.createElement('div');
        newForm.innerHTML = this.#form;
        formDiv.appendChild(newForm);
    }

    /**
     * HTMLにstartUpdateFormフラグメント追加
     */
    addStartUpdateForm() {
        if (!this.#startUpdateForm) return;

        // id=formDivの子要素に startUpdateForm を追加
        const formDiv = document.getElementById('formDiv');
        const newForm = document.createElement('div');
        newForm.innerHTML = this.#startUpdateForm;
        formDiv.appendChild(newForm);
    }

    /**
     * HTMLにplacesUpdateFormフラグメント追加
     */
    addPlacesUpdateForm() {
        if (!this.#placesUpdateForm) return;

        // id=formDivの子要素に placesUpdateForm を追加
        const formDiv = document.getElementById('formDiv');
        const newForm = document.createElement('div');
        newForm.innerHTML = this.#placesUpdateForm;
        formDiv.appendChild(newForm);
    }

    /**
     * 追加する Toggle fragment取得
     * @returns {*}
     */
    toggle() {
        if (this.#toggle === null) throw new Error('このインスタンスは初期化されていません。initialize()を実行してください。');
        return this.#toggle;
    }

    /**
     * 追加する Form fragment取得
     * @returns {*}
     */
    form() {
        if (this.#form === null) throw new Error('このインスタンスは初期化されていません。initialize()を実行してください。');
        return this.#form;
    }
}

class ModalElement {
    /**
     * モーダル要素を格納するオブジェクト
     */
    #modals;
    /**
     * トグルボタンを格納するオブジェクト
     */
    #toggleButtons;
    /**
     * クローズボタンを格納するオブジェクト
     */
    #closeButtons;

    constructor() {
        this.#modals = {
            start: document.getElementById('startModal'),
            end: document.getElementById('endModal'),
            places: [document.getElementById(`placeModal${placeNum.value()}`)],
            updateStart: null,
            updatePlaces: [],
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
            updateStart: null,
            updatePlaces: [],
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
        const modal = this.getModal(modalType, num);
        const toggleBtn = this.getToggleBtn(modalType, num);
        const closeBtn = this.getCloseBtn(modalType, num);

        // イベントのアタッチ
        toggleBtn.addEventListener('click', () => modal.toggle() );
        closeBtn.addEventListener('click', () => {
            modal.hide();
            document.activeElement.blur(); // フォーカスを外す
        });
    }

    /**
     * modal取得
     * @param modalType {'places','start','end','updateStart','updatePlaces'} モーダルの種別
     * @param num formNum
     * @returns {Modal}
     */
    getModal(modalType, num=null) {
        if (modalType === 'places') {
            return new Modal(this.#modals[modalType][num]);
        }
        return new Modal(this.#modals[modalType]);
    }

    /**
     * toggle取得
     * @param modalType {'places','start','end'} モーダルの種別
     * @param num formNum
     * @returns {*}
     */
    getToggleBtn(modalType, num=null) {
        if (modalType === 'places') {
            return this.#toggleButtons.places[num];
        }
        return this.#toggleButtons[modalType];
    }

    /**
     * close取得
     * @param modalType {'places','start','end'} モーダルの種別
     * @param num formNum
     * @returns {*}
     */
    getCloseBtn(modalType, num=null) {
        if (modalType === 'places') {
            return this.#closeButtons.places[num];
        }
        return this.#closeButtons[modalType];
    }

    /**
     * ✕ボタンの表示
     * @param formNum formの項番
     */
    #displayDeleteButton(formNum) {
        const deleteBtn = document.getElementById(`modalDeleteBtn${formNum}`);
        deleteBtn.classList.remove('hidden');

        deleteBtn.addEventListener('click', () => {
            const toggleBtn = this.getToggleBtn('places', formNum);
            toggleBtn.classList.add('hidden'); // Modal表示のボタンを隠す
            deleteBtn.classList.add('hidden'); // ✕ボタンを隠す
        });
    }

    /**
     * 出発地点更新のelementを配列に追加
     */
    setStartUpdateModal() {
        this.#modals.startUpdateModal = document.getElementById('startUpdateModal');
        this.#closeButtons.startUpdateModal = document.getElementById('startUpdateClose');
    }

    /**
     * 目的地更新のelementを配列に追加
     */
    setPlacesUpdateModal() {
        this.#modals.updatePlaces.push(document.getElementById('placesUpdateModal'));
        this.#closeButtons.updatePlaces.push(document.getElementById('placesUpdateClose'));
    }

    /**
     * modalを閉じる
     * @param modalType {'places','start','end'} モーダルの種別
     * @param num modalListのnumber(form項番-1)
     */
    closeModal(modalType, num=null) {
        const modal = this.getModal(modalType, num);
        modal.hide();
    }

    /**
     * 出発地点の表示を変更
     */
    changeStartDisplay() {
        const timeSpan = document.getElementById('startTimeSpan'); // 時間spanタグ取得
        const placeSpan = document.getElementById('startPlaceSpan'); // 場所名spanタグ

        const startTime = document.getElementById('startTime');
        timeSpan.textContent = startTime.value; // 開始時間を入れる
        const startPlace = document.getElementById('startPlace');
        placeSpan.textContent = startPlace.value; // spanの文字を場所名に

        timeSpan.classList.remove('absolute');
    }

    /**
     * 終了地点の表示を変更
     */
    changeEndDisplay() {
        const placeSpan = document.getElementById('endPlaceSpan'); // spanタグ取得

        const endPlace = document.getElementById('endPlace');
        placeSpan.textContent = endPlace.value; // spanの文字を場所名に
    }

    /**
     * 目的地の表示を変更
     * @param formNum formの項番
     */
    changePlaceDisplay(formNum) {
        // buttonの子要素のspanタグ取得
        const timeSpan = document.getElementById(`placeTimeSpan${formNum}`);
        const placeSpan = document.getElementById(`placeNameSpan${formNum}`);
        const budgetSpan = document.getElementById(`budgetSpan${formNum}`);
        const stayTimeSpan = document.getElementById(`stayTimeSpan${formNum}`);

        // inputの要素取得
        const placeInput = document.getElementById(`place${formNum}`);
        const desiredStartTimeInput = document.getElementById(`desiredStartTime${formNum}`);
        const desiredEndTimeInput = document.getElementById(`desiredEndTime${formNum}`);
        const budgetInput = document.getElementById(`budget${formNum}`);
        const stayTimeInput = document.getElementById(`stayTime${formNum}`);

        /* ---- 表示を変更 ---- */
        placeInput.disabled = true; // 目的地部分をdisabledに
        placeInput.classList.add('bg-gray-100');

        // 場所名
        placeSpan.textContent = placeInput.value;

        // 希望時間
        if (!desiredStartTimeInput.value) timeSpan.textContent = '';
        else timeSpan.textContent = desiredStartTimeInput.value + '~' + desiredEndTimeInput.value;
        timeSpan.classList.remove('absolute');

        // 予算
        if (!budgetInput.value) budgetSpan.textContent = '予算：----' + '円';
        else budgetSpan.textContent = '予算：' + budgetInput.value + '円';

        // 滞在時間
        if (!stayTimeInput.value) stayTimeSpan.textContent = '滞在時間：30分';
        else stayTimeSpan.textContent = '滞在時間：' + stayTimeInput.value + '分';

        // 緑色の枠線をけす
        const toggleBtn = this.getToggleBtn('places', formNum);
        toggleBtn.classList.remove('border-interactive');

        this.#displayDeleteButton(formNum);
    }

    /**
     * 開くModalをUpdateFormに切り替える
     * createのFormを削除
     * @param modalType {'start','end','places'}
     * @param num
     */
    changeToggleTarget(modalType, num=null) {
        const toggleBtn = this.getToggleBtn(modalType, num);

        // '○○UpdateModal' にターゲットを変える
        const newTarget = num ? `${modalType}updateModal${num}` : `${modalType}updateModal`;

        // data-modal-target data-modal-toggleを変更
        toggleBtn.setAttribute('data-modal-target', newTarget);
        toggleBtn.setAttribute('data-modal-toggle', newTarget);

        // createのFormを削除
        const createForm = this.getModal(modalType, num);
        if (!createForm) return;
        createForm.remove();
    }
}

class ModalForm {
    #startFormElement;
    placeFormElement = [];
    #endFormElement;
    #startUpdateFormElement;
    #placesUpdateFormElement;

    constructor() {
        this.#startFormElement = document.getElementById('startPlaceForm');
        for (let i = 0; i <= placeNum.value(); i++) {
            this.placeFormElement.push(document.getElementById(`placeForm${i}`));
        }
        this.#endFormElement = document.getElementById('endPlaceForm');
        this.initFormEvent();
    }

    /**
     * #startFormElement placeFormElement #endFormElementにsubmitイベント割り当て
     */
    initFormEvent() {
        if (this.#startFormElement) this.#startFormElement.addEventListener('submit', (e) => this.#startFormSubmit(e) );
        if (this.#endFormElement) this.#endFormElement.addEventListener('submit', (e) => this.#endFormSubmit(e) );
        if (this.placeFormElement) this.placeFormElement.forEach((element) =>
            element.addEventListener('submit', async(e) => await this.#placesFormSubmit(e)));
    }

    /**
     * 出発地点のsubmitイベント
     * @param e イベント
     */
    async #startFormSubmit(e) {
        e.preventDefault();

        // 値の検証（nullがあるか）
        if (!this.#startFormCheck()) {
            // エラーメッセージ表示
            document.getElementById('startError').textContent = '出発地点・予定時間を正しく入力してください。';
            return;
        }
        document.getElementById('startError').textContent = '';

        // api/create-planに送信
        const formData = new FormData(e.target);
        await this.postCreatePlaceAPI(formData, 'start');
    }

    /**
     * 出発地点のrequiredチェック
     * @returns {boolean} すべて値が入ってたらtrue
     */
    #startFormCheck() {
        const placeName = document.getElementById('startPlace').value;
        const placeId = document.getElementById('startPlaceId').value;
        const lat = document.getElementById('startLat').value;
        const lng = document.getElementById('startLng').value;
        const time = document.getElementById('startTime').value;

        return !!(placeName && placeId && lat && lng && time);
    }

    /**
     * 出発地点の /api/create-placeが成功したとき
     */
    #startPlaceCreateSuccess() {
        // modal関連の動作
        modal.closeModal('start');
        modal.changeStartDisplay();

        // startUpdateFormを呼び出す
        const fragment = new Fragment();
        fragment.addStartUpdateForm(); // HTMLに追加
        modal.setStartUpdateModal(); // Elementを追加

        // updateFormのstartUpdatePlaceを更新
        const updatePlace = document.getElementById('startUpdatePlace');
        const startPlace = document.getElementById('startPlace');
        updatePlace.value = startPlace.value;

        // startToggleの data-modal-target data-modal-toggleを変更
        modal.changeToggleTarget('start');

        // formのsubmitイベントをアタッチ
        this.#startUpdateFormElement = modal.getModal('updateStart');
        this.#startUpdateFormElement.addEventListener('submit', (e) => this.#startUpdateFormSubmit(e));
    }

    /**
     * 終了地点の /api/create-placeが成功したとき
     */
    #endPlaceCreateSuccess() {
        // modal関連の動作
        modal.closeModal('end');
        modal.changeEndDisplay();
    }

    /**
     * 目的地の /api/create-placeが成功したとき
     */
    async #placesCreateSuccess(formNum) {
        // modal関連の動作
        modal.closeModal('places');
        modal.changePlaceDisplay();

        if(formNum !== placeNum.value()) return; // 目的地再設定はreturn
        await this.newAddFragment();

        // placesUpdateFormを呼び出す
        const fragment = new Fragment();
        fragment.addPlacesUpdateForm(); // HTMLに追加
        modal.setPlacesUpdateModal(); // Elementを追加

        // 場所名を取得して更新
        const updatePlace = document.getElementById(`updatePlace${formNum}`);
        const place = document.getElementById(`place${formNum}`);
        updatePlace.value = place.value;

        // placesToggleの data-modal-target data-modal-toggleを変更
        modal.changeToggleTarget('places', formNum);

        // formのsubmitイベントをアタッチ
        this.#placesUpdateFormElement = modal.getModal('updatePlaces');
        this.#placesUpdateFormElement.addEventListener('submit', (e) => this.#placeUpdateFormSubmit(e));
    }

    /**
     * 出発地点の更新submitイベント
     * @param e
     */
    async #startUpdateFormSubmit(e) {
        e.preventDefault();

        // 値の検証（startUpdateTimeがあるか）
        if (!document.getElementById('startUpdateTime')) {
            document.getElementById('startUpdateError').textContent = '予定時間を正しく入力してください。';
            return;
        }
        document.getElementById('startUpdateError').textContent = '';

        const formData = new FormData(e.target);
        // api/update-planに送信
        await this.postUpdatePlaceAPI(formData, 'start');
    }

    /**
     * 目的地の更新submitイベント
     * @param e
     */
    async #placeUpdateFormSubmit(e) {
        e.preventDefault();

        const formData = new FormData(e.target);
        // api/update-planに送信
        await this.postUpdatePlaceAPI(formData, 'places');
    }

    /**
     * 終了地点のsubmitイベント
     * @param e イベント
     */
    #endFormSubmit(e) {
        e.preventDefault();

        // 値の検証（nullがあるか）
        if (!this.#endFormCheck()) {
            document.getElementById('endError').textContent = '終了地点を正しく入力してください。';
            return;
        }
        document.getElementById('endError').textContent = '';

        const formData = new FormData(e.target);

        // api/create-planに送信
        this.postCreatePlaceAPI(formData, 'end');
    }

    /**
     * 終了地点のrequiredチェック
     * @returns {boolean} すべて値が入ってたらtrue
     */
    #endFormCheck() {
        const placeName = document.getElementById('endPlace').value;
        const placeId = document.getElementById('endPlaceId').value;
        const lat = document.getElementById('endLat').value;
        const lng = document.getElementById('endLng').value;

        return !!(placeName && placeId && lat && lng);
    }

    /**
     * 目的地のsubmitイベント
     * @param e イベント
     * @returns {Promise<void>}
     */
    async #placesFormSubmit(e) {
        e.preventDefault();

        const formId = e.target.id; // formのid取得
        const formNum = Number(formId.replace('placeForm', '')); // placesSubmit{num}の数字だけ取得

        // 値の検証（nullがあるか）
        if (!this.#placeFormCheck(formNum)) {
            document.getElementById(`placeError${formNum}`).textContent = '目的地を正しく入力してください。';
            return;
        }
        document.getElementById(`placeError${formNum}`).textContent = '';

        const formData = new FormData(e.target);
        this.setEndTime(formNum, formData);

        // Post処理 /api/create-places
        await this.postCreatePlaceAPI(formData, 'places', formNum);
    };

    /**
     * 目的地のrequiredチェック
     * @returns {boolean} すべて値が入ってたらtrue
     */
    #placeFormCheck(num) {
        const placeName = document.getElementById(`place${num}`).value;
        const placeId = document.getElementById(`placeId${num}`).value;
        const lat = document.getElementById(`placeLat${num}`).value;
        const lng = document.getElementById(`placeLng${num}`).value;

        return !!(placeName && placeId && lat && lng);
    }

    /**
     * 追加フラグメントを挿入
     * @returns {Promise<void>}
     */
    async newAddFragment() {
        const newFragment = new Fragment();
        await newFragment.initialize();

        if (!newFragment.toggle() && !newFragment.form()) return; // 取得できなかったとき
        newFragment.addFragment();
        placeNum.increment();
        modal.addPlacesElement();
        modal.addButtonEvent('places', placeNum.value()); // 新しいModalにイベント追加
        new ModalForm(); // modalFormイベントをアタッチ
    }

    /**
     * endTimeを(startTime+stayTime)に
     */
    setEndTime(formNum, formData) {
        const startTime = formData.get('startTime');
        const stayTime = formData.get(`stayTime${formNum}`);
        // startTimeをパースしてDate型に変換
        const [startHours, startMinutes] = startTime.split(':').map(Number);
        const startDate = new Date();
        startDate.setHours(startHours, startMinutes, 0, 0); // 時間, 分, 秒, ミリ秒
        // stayTime(分)を加算
        const endDate = new Date(startDate.getTime() + stayTime * 60000); // 60000ms = 1分
        // endDateをHH:mm形式にフォーマット
        const endHours = String(endDate.getHours()).padStart(2, '0'); // ゼロ埋め
        const endMinutes = String(endDate.getMinutes()).padStart(2, '0'); // ゼロ埋め
        const endTime = `${endHours}:${endMinutes}`;
        // FormDataにendTimeをセット
        formData.set('endTime', endTime);
    }

    /**
     *
     * @param formData 送信するformのデータ
     * @param modalType {'start','end','places'} 送信するformのタイプ
     * @param formNum 送信するformの項番 placeFormのみ
     */
    async postCreatePlaceAPI(formData, modalType, formNum=null) {
        try {
            // 非同期でPOSTリクエストを送信
            const response = await fetch('/api/create-place', {
                method: 'POST',
                body: formData,
            });

            // 通信が成功しないとき
            if (!response.ok) {
                throw new Error(`送信エラー: ${response.status}`);
            }

            // /api/create-placeでFailedが返される
            const data = await response.json();
            if (data.status === 'Failed') {
                throw new Error('エラーが発生しました。');
            }

            // 成功時の処理
            if (modalType === 'start') {
                this.#startPlaceCreateSuccess();
            } else if (modalType === 'end') {
                this.#endPlaceCreateSuccess();
            } else {
                await this.#placesCreateSuccess(formNum); // awaitで実行
            }
        } catch (error) {
            document.getElementById('Error').textContent = '送信中にエラーが発生しました。もう一度お試しください。';
        }
    }

    /**
     * /api/update-placeに送信する
     * @param formData 送信formData
     */
    postUpdatePlaceAPI(formData) {
        try {
            // 非同期でPOSTリクエストを送信
            fetch('/api/update-place', {
                method: 'POST',
                body: formData,
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`送信エラー: ${response.status}`);
                    }
                    return response.json(); // 必要に応じてレスポンスを処理
                })
                .then(data => {
                    if (data.status === 'Failed')
                        throw new Error('エラーが発生しました。');
                    // 成功時
                });
        } catch (error) {
            document.getElementById('Error').textContent = '送信中にエラーが発生しました。もう一度お試しください。';
        }
    }
}

const placeNum = new PlaceNum();
const modal = new ModalElement();

document.addEventListener('DOMContentLoaded', () => {
    new ModalForm();
});
