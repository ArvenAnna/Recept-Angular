export default function ReceptListService($http, $q, crudService) {
    'ngInject';

    Object.assign(this, {
        getReceptList,
        getReceptListByTag
    });

    function getReceptList(departId) {
        return crudService
            .doGet(crudService.urls.GET_RECEPT_LIST_BY_DEPART, {
                departId: departId
            });
    }

    function getReceptListByTag(tagId) {
        crudService
            .doGet(crudService.urls.GET_RECEPT_LIST_BY_TAG, {
                tagId: tagId
            });
    }
}