class RecommendPlace {
    /**
     * おすすめ目的地submit時に発火
     */
    async submitEvent(e) {
        e.preventDefault();

        // submit送信したformを取得
        const formKey = e.target.id;
        const formNum = Number(formKey.replace('recommendForm', ''));

        // 「目的地を追加する」のform取得
        const modalForm = new ModalForm();
        const placeFormList = modalForm.placeFormElements;
        const placeFormKey = placeFormList[placeFormList.length - 1].id;
        const placeFormNum = Number(placeFormKey.replace('placeForm', ''));

        // sessionに値を追加 (modal.jsのSessionStorageListのplaceに入れる)
        sessionStorageList.setRecommendPlace(placeFormNum, formNum);

        // 今表示されている「目的地を追加する」のvalueをsessionから入れる
        this.#setPlaceFormValue(placeFormNum);

        // 今表示されている「目的地を追加する」の表示内容変更
        modal.changePlaceDisplay(placeFormNum);

        // 新規フラグメントの呼び出し
        await modalForm.newAddFragment();

        this.#hideModal(formNum); // 現在開かれてるmodalを閉じる
        this.#hideDisplay(formNum); // 追加したおすすめ目的地の表示を隠す
    }

    /**
     * modalの表示を隠す
     * @param formNum
     */
    #hideModal(formNum) {
        const modalElement = document.getElementById(`recommendModal${formNum}`);
        const modal = new Modal(modalElement);
        modal.hide(); // 表示されているmodalを閉じる
    }

    /**
     * toggleBtnの表示を隠す
     */
    #hideDisplay(formNum) {
        // modal表示切り替えのtoggleBtn取得
        const toggleBtn = document.getElementById(`recommendToggle${formNum}`);
        toggleBtn.style.display = 'none';
    }

    /**
     * sessionから値を取得してformのvalueに入れる
     * @param placeFormNum 「目的地」formの項番
     */
    #setPlaceFormValue(placeFormNum) {
        const data = sessionStorageList.getPlacesData(placeFormNum-1);

        document.getElementById(`placeId${placeFormNum}`).value = data.placeId;
        document.getElementById(`placeLat${placeFormNum}`).value = data.lat;
        document.getElementById(`placeLng${placeFormNum}`).value = data.lng;
        document.getElementById(`place${placeFormNum}`).value = data.name;

        if (data.budget)
            document.getElementById(`budget${placeFormNum}`).value = data.budget;

        if (data.stayTime)
            document.getElementById(`stayTime${placeFormNum}`).value = data.stayTime;
        else
            document.getElementById(`stayTime${placeFormNum}`).value = 30;

        if (data.desiredStartTime)
            document.getElementById(`desiredStartTime${placeFormNum}`).value = data.desiredStartTime;

        if (data.desiredEndTime)
            document.getElementById(`desiredEndTime${placeFormNum}`).value = data.desiredEndTime;
    }
}

const recommendPlace = new RecommendPlace();

// th呼び出し後 submitイベントをアタッチ
document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.recommend').forEach(element => {
        element.addEventListener('submit', async function(e) {
            await recommendPlace.submitEvent(e);
        });
    });
});
