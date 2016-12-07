export default function MenuService(crudService) {
    'ngInject';

    Object.assign(this, {
        getDepartments,
        getTags
    });

    function getDepartments() {
        return crudService.doGet(crudService.urls.GET_DEPARTS);
    }

    function getTags() {
        return crudService.doGet(crudService.urls.GET_TAGS);
    }
}

