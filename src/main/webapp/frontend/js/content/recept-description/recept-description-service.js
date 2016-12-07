export default function ReceptDescriptionService($http, $q, crudService) {
    'ngInject';

    Object.assign(this, {
        deleteRecept,
        getPdfFromRecept
    });

    function deleteRecept(receptId) {
        return crudService
            .doDelete(crudService.urls.DELETE_RECEPT, {
                receptId: receptId
            });
    }

    function getPdfFromRecept(receptId) {
        return $http.get(crudService.urls.GET_PDF_FILE, {
            params: {
                receptId: receptId
            },
            responseType: 'arraybuffer'
        });
    }
}