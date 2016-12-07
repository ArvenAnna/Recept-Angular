export default function Main() {
    'ngInject';

    return {
        controller: MainController,
        controllerAs: 'mainCtrl',
        templateUrl: '/frontend/html/main.html',
        bindings: {
            theme: '='
        }
    };
}

export function MainController(receptModel, stateService) {

    const vm = this;

    Object.assign(vm, {
        $onInit,

        state: stateService.states.HOME,
        choosenDepartId: null,
        choosenTagId: null,
        query: null,
        sort: null,
        itemsPerPage: 5,
        receptId: null
    });

    function $onInit() {
        vm.model = receptModel;
    }
}
