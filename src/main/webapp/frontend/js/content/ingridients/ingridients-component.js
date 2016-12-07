export default function Ingridients() {
    'ngInject';

    return {
        controller: IngridientsController,
        controllerAs: 'ingridientsCtrl',
        templateUrl: '/frontend/html/content/ingridients.html',
        bindings: {
            state: '='
        }
    };
}

export function IngridientsController(contentService, ingridientsService) {

    const vm = this;

    Object.assign(vm, {
        $onInit,
        deleteIngridient,
        saveIngridient
    });

    function deleteIngridient() {
        if (vm.ingridientId) {
            ingridientsService.deleteIngridient(vm.ingridientId)
                .then(setDataFromServer);
        }
    }

    function saveIngridient() {
        if (vm.ingridient.name) {
            ingridientsService.saveIngridient(vm.ingridient)
                .then(data => {
                    setDataFromServer();
                    resetIngridient();
                })
                .catch(reason => vm.error = reason);
        }
    }

    function $onInit() {
        resetIngridient();
        setDataFromServer();
    }

    function setDataFromServer() {
        contentService.getIngridients()
            .then(data => vm.ingridients = data);
    }

    function resetIngridient() {
        vm.ingridient = {
            name: null
        };
    }

}
