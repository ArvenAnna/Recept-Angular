export default function IngridientsService(crudService) {
    'ngInject';

    Object.assign(this, {
        saveIngridient,
        deleteIngridient
    });

    function saveIngridient(ing) {
        return crudService
            .doPost(crudService.urls.POST_INGRIDIENT, ing);
    }

    function deleteIngridient(ingId) {
        return crudService
            .doDelete(crudService.urls.DELETE_INGRIDIENT, {
                ingId: ingId
            });
    }
}
