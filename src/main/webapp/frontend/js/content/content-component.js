export default function Content() {
    'ngInject';

    return {
        controller: ContentController,
        controllerAs: 'contentCtrl',
        templateUrl: '/frontend/html/content/content.html',
        bindings: {
            choosenDepartId: '<',
            choosenTagId: '<',
            state: '=',
            query: '<',
            sort: '<',
            itemsPerPage: '<',
            receptId: '='
        }
    };
}

export function ContentController(stateService) {

    const vm = this;

    Object.assign(vm, {
        $onInit,
        isReceptListState,
        isReceptDescriptionState,
        isReceptEditState,
        isReceptCreateState,
        isIngridientEditState,
        isHomeState
    });

    function $onInit() {
    }

    function isReceptListState() {
        return vm.state === stateService.states.RECEPT_LIST;
    }

    function isReceptDescriptionState() {
        return vm.state === stateService.states.RECEPT_DESCRIPTION;
    }

    function isReceptEditState() {
        return vm.state === stateService.states.RECEPT_EDIT;
    }

    function isReceptCreateState() {
        return vm.state === stateService.states.RECEPT_CREATE;
    }

    function isIngridientEditState() {
        return vm.state === stateService.states.INGRIDIENT_EDIT;
    }

    function isHomeState() {
        return vm.state === stateService.states.HOME;
    }
}