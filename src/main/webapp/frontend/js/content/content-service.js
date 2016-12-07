export default function ContentService($http, $q, crudService) {
    'ngInject';

    Object.assign(this, {
        getRecept,
        getIngridients,
        saveReceptFile,
        getReceptFile,
        getUrlFromPngFile,
        getDetailFile,
        saveDetailFile,
        massiveGrouping
    });

    function saveReceptFile(file, receptId) {
        let fd = new FormData();
        fd.append('file', file);
        fd.append('receptId', receptId);
        return $http.post(crudService.urls.FILE_RECEPT_SAVE, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined},
            responseType: 'arraybuffer'
        });
    }

    function getRecept(receptId) {
        return crudService.doGet(crudService.urls.GET_RECEPT, {
            receptId: receptId
        });
    }

    function getIngridients() {
        return crudService.doGet(crudService.urls.GET_INGRIDIENTS);
    }

    function getReceptFile(receptId) {
        return $http.get(crudService.urls.FILE_RECEPT_GET, {
            params: {
                receptId: receptId
            },
            responseType: 'arraybuffer'
        });
    }

    function saveDetailFile(file, detailId) {
        var fd = new FormData();
        fd.append('file', file);
        fd.append('detailId', detailId);
        return $http.post(crudService.urls.FILE_DETAIL_SAVE, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined},
            responseType: 'arraybuffer'
        });
    }

    function getDetailFile(detailId) {
        return $http.get(crudService.urls.FILE_DETAIL_GET, {
            params: {
                detailId: detailId
            },
            responseType: 'arraybuffer'
        });
    }

    function getUrlFromPngFile(data) {
        if (data.data) {
            let blob = new Blob([data.data], {type: "image/png"});
            let urlCreator = window.URL || window.webkitURL;
            return urlCreator.createObjectURL(blob);
        }
        return null;
    }

    function massiveGrouping(n, inputArray, arr) {
        let inputArr = angular.copy(inputArray);
        arr = arr || [];
        if (inputArr.length != 0) {
            let emptyArr = [];
            for (let i = 0; i < n; i++) {
                if (inputArr[i]) {
                    emptyArr.push(inputArr[i]);
                }
            }
            inputArr.splice(0, emptyArr.length);
            arr.push(emptyArr);
            return massiveGrouping(n, inputArr, arr);
        } else {
            return arr;
        }
    }

}